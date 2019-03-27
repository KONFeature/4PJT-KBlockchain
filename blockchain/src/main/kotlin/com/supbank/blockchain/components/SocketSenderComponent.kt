package com.supbank.blockchain.components

import com.google.gson.Gson
import com.supbank.blockchain.models.Node
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.repos.NodeRepository
import com.supbank.blockchain.utils.p2p.JoinPayload
import com.supbank.blockchain.utils.p2p.NodesPayload
import com.supbank.blockchain.utils.p2p.P2pPayload
import io.reactivex.schedulers.Schedulers
import io.rsocket.kotlin.DefaultPayload
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.client.WebsocketClientTransport
import io.rsocket.kotlin.util.AbstractRSocket
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * List of socket connected to the node
 */
@Component
class SocketSenderComponent(private var nodeRepository: NodeRepository,
                            private val log: Logger) {

    // Map of all the socket clients
    private var clients: HashMap<NodePojo, RSocket> = HashMap()

    // Current node for fetching the address
    private var fetchedNode: ArrayList<NodePojo> = ArrayList()

    @Value("\${blockchain.known.node.host}")
    lateinit var knownHost: String

    @Value("\${blockchain.known.node.port}")
    var knownPort: Int = 0

    @Value("\${blockchain.accessible.host}")
    lateinit var accessibleHost: String

    @Value("\${blockchain.accessible.port}")
    var accessiblePort: Int = 0

    /**
     * Init the base client connexion to a known host
     */
    @PostConstruct
    private fun init() {
        // Clean all the known node before when launching the server
        nodeRepository.deleteAll()

        // Send join request to the known node
        RSocketFactory
                .connect()
                .acceptor { { rSocket -> handle(rSocket) } }
                .transport {
                    WebsocketClientTransport
                            .create(knownHost, knownPort)
                }
                .start()
                .observeOn(Schedulers.io())
                .subscribe({ rSocket ->
                    log.info("Sending join request on known node {}:{}", knownHost, knownPort)
                    join(rSocket)
                }, { error ->
                    log.warn("Error when sending join request to the known node {}:{}, {}", knownHost, knownPort, error)
                })

//        addClient(NodePojo(knownHost, knownPort))
    }

    /**
     * Function used to send the server ip to the node
     */
    private fun join(socket: RSocket) {
        socket.requestStream(JoinPayload(NodePojo(accessibleHost, accessiblePort)).get())
                .doOnNext {payload ->
                    // Receive new get for the join request
                    log.debug("New get received from the join request {}:{}", payload.metadataUtf8, payload.dataUtf8)
                    if(P2pPayload.isNodes(payload)) {
                        // Parse the list of node
                        val nodes = Gson().fromJson<ArrayList<NodePojo>>(payload.dataUtf8, NodesPayload.type)
                        log.info("Received list of nodes of the network {}", nodes)
                        nodes.forEach { node -> addClient(node) }
                    }
                }.doOnComplete {
                    log.info("End of the join request")
                }.doOnError {error ->
                    log.error("Error when joining the known node on the network {}", error)
                }.subscribeOn(Schedulers.io()).subscribe()
    }

    /**
     * Function used to add a client to the list
     */
    private fun addClient(node: NodePojo) {
        if (clients.containsKey(node)) {
            log.warn("Client already known, aborting the adding request")
            return
        }

//        if(Inet4Address.getLocalHost().hostAddress.equals(node.host, true) ||
//                Inet6Address.getLocalHost().hostAddress.equals(node.host, true) ||
//                Inet4Address.getLoopbackAddress().hostAddress.equals(node.host, true) ||
//                Inet6Address.getLoopbackAddress().hostAddress.equals(node.host, true)) {
//            log.warn("Trying to add localhost or loopback address, aborting")
//            return
//        }

        RSocketFactory
                .connect()
                .acceptor { { rSocket -> handle(rSocket) } }
                .transport {
                    WebsocketClientTransport
                            .create(node.host, node.port)
                }
                .start()
                .doOnSuccess { rSocket ->
                    log.info("Adding client socket for node {}", node)
                    clients[node] = rSocket
                    nodeRepository.save(Node(node.host, node.port))
                }.doOnError { error ->
                    log.warn("Error when initialising the client on node {} : {}", node, error)
                }.subscribeOn(Schedulers.io()).subscribe()
    }

    /**
     * Create the sender socket
     * rSocket = the client socket
     */
    private fun handle(rSocket: RSocket) =
            object : AbstractRSocket() {

            }

    /**
     * Function used to broadcast a message to all socket
     */
    fun broadcast(text: String, data: String) {
        clients.forEach { entry ->
            log.info("Sending broadcast on socket {}", entry.key)
            entry.value.fireAndForget(DefaultPayload.text(text, data))
                    .observeOn(Schedulers.io())
                    .subscribe {
                        log.info("Response received from socket {} : {}", entry.key)
                    }
        }
    }
}
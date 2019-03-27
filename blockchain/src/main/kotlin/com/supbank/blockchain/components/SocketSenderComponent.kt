package com.supbank.blockchain.components

import com.google.gson.Gson
import com.supbank.blockchain.models.Node
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.repos.NodeRepository
import com.supbank.blockchain.utils.p2p.JoinPayload
import com.supbank.blockchain.utils.p2p.NodesPayload
import com.supbank.blockchain.utils.p2p.P2pPayload
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import io.rsocket.kotlin.Payload
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.transport.netty.client.WebsocketClientTransport
import io.rsocket.kotlin.util.AbstractRSocket
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.Inet4Address
import java.net.Inet6Address
import javax.annotation.PostConstruct

/**
 * List of socket connected to the node
 */
@Component
class SocketSenderComponent(private val log: Logger) {

    // Map of all the socket clients
    private var clients: HashMap<NodePojo, RSocket> = HashMap()

    @Value("\${blockchain.known.node.host}")
    lateinit var knownHost: String

    @Value("\${blockchain.known.node.port}")
    var knownPort: Int = 0

    @Value("\${p2p.accessible.host}")
    lateinit var accessibleHost: String

    @Value("\${p2p.port}")
    var accessiblePort: Int = 0

    /**
     * Init the base client connexion to a known host
     */
    @PostConstruct
    private fun init() {
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
                .doOnNext { payload ->
                    // Receive new get for the join request*
                    if (P2pPayload.isNodes(payload)) {
                        // Parse the list of node
                        val nodes = Gson().fromJson<ArrayList<NodePojo>>(payload.dataUtf8, NodesPayload.type)
                        log.info("Received list of nodes of the network {}", nodes)
                        nodes.forEach { node -> addClient(node) }
                    }
                }.doOnComplete {
                    log.info("End of the join request")
                }.doOnError { error ->
                    log.error("Error when joining the known node on the network {}", error)
                }.subscribeOn(Schedulers.io()).subscribe()
    }

    /**
     * Function used to add a client to the list
     */
    fun addClient(node: NodePojo) {
        if (clients.containsKey(node)) {
            log.warn("Client already known, aborting the adding request")
            return
        }

        if(Inet4Address.getLocalHost().hostAddress.equals(node.host, true) ||
                Inet6Address.getLocalHost().hostAddress.equals(node.host, true) ||
                Inet4Address.getLoopbackAddress().hostAddress.equals(node.host, true) ||
                Inet6Address.getLoopbackAddress().hostAddress.equals(node.host, true)) {
            log.warn("Trying to add localhost or loopback address, aborting")
            return
        }

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
                override fun close(): Completable {
                    // Find the nodes to remove and remove them
                    val nodes = clients.filter { entry -> entry.value == rSocket }.keys
                    nodes.forEach { node ->
                        clients.remove(node)
                        log.info("Node {} leave the network", node)
                    }
                    return super.close()
                }
            }

    /**
     * Function used to broadcastFf a message to all socket
     */
    fun broadcastFf(payload: Payload) {
        clients.forEach { entry ->
            log.debug("Sending the \"{}\" broadcast to node {}", payload.metadataUtf8, entry.key)
            entry.value.fireAndForget(payload)
                    .doOnComplete {
                        log.debug("Successfully send the \"{}\" broadcast to the node {}", payload.metadataUtf8, entry.key)
                    }.doOnError {error ->
                        log.warn("Error when sending the \"{}\" broadcast to the node {} : {}", payload.metadataUtf8, entry.key, error)
                    }.subscribeOn(Schedulers.io()).subscribe()
        }
    }

    /**
     * Function use to list all the node of this current server
     */
    fun nodes() = clients.keys.toList()
}
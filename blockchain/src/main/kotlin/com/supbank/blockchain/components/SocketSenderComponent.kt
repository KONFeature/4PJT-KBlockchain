package com.supbank.blockchain.components

import com.supbank.blockchain.models.Node
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.repos.NodeRepository
import com.supbank.blockchain.utils.Request
import io.reactivex.schedulers.Schedulers
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

    /**
     * Init the base client connexion to a known host
     */
    @PostConstruct
    private fun init() {
        // Clean all the known node before when launching the server
        nodeRepository.deleteAll()
        addClient(NodePojo(knownHost, knownPort))
    }

    /**
     * Function used to add a client to the list
     */
    private fun addClient(node: NodePojo) {
        if (clients.containsKey(node)) {
            log.warn("Client already known, aborting the adding request")
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
                .observeOn(Schedulers.io())
                .subscribe({ rSocket ->
                    log.info("Adding client socket on node {}", node)
                    clients[node] = rSocket
                    nodeRepository.save(Node(node.host, node.port))
                }, { error ->
                    log.warn("Error when initialising the client on node {} : {}", node, error)
                })
    }

    /**
     * Try to fetch all the network node on the address
     */
    fun fetchAddress() {
        clients.entries.forEach { entry ->
            log.info("Fetching addresses from node {}", entry.key)

            // If we doesn't have fetch address on this node yet we try it and leave after
            if (!fetchedNode.contains(entry.key)) {
                requestNodeKnownAddresses(entry.key, entry.value)
                return
            }
        }
    }

    /**
     * Function used to request a node all the address he known
     */
    private fun requestNodeKnownAddresses(node: NodePojo, socket: RSocket) {
        // Construct list of known nodes to prevent the server to send nodes that we already have
        var knownNodes = ArrayList<NodePojo>()
        nodeRepository.findAll().forEach { dbNode ->
            NodePojo.fromNode(dbNode)?.let { knownNodes.add(it) }
        }


        socket.requestStream(Request.LIST_NODES.payload)
                .observeOn(Schedulers.io())
                .subscribe({ payload ->
                    // Next
                    val parsedNode = NodePojo.fromPayload(payload) // Parse the node pojo
                    parsedNode?.let { newNode ->
                        log.info("Successfully received new node address {}", newNode)
                        addClient(newNode) // Add the node p    ojo
                    } ?: run {
                        log.warn("Unable to parse node from {} : {}->{}", node, payload.dataUtf8, payload.metadataUtf8)
                    }
                }, { error ->
                    // Error
                    log.warn("Error when fetching addresses from node {} : {}", node, error)
                    fetchedNode.add(node)
                    fetchAddress()
                }, {
                    // Complete
                    log.info("Fetched all the known node of node {}", node)
                    fetchedNode.add(node)
                    fetchAddress()
                })
    }

    /**
     * Create the sender socket
     * rSocket = the client socket
     */
    private fun handle(rSocket: RSocket) =
            object : AbstractRSocket() {

            }
}
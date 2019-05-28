package com.supbank.blockchain.components

import com.google.gson.Gson
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.utils.p2p.sync.JoinPayload
import com.supbank.blockchain.utils.p2p.sync.NodesPayload
import com.supbank.blockchain.utils.p2p.P2pPayload
import com.supbank.blockchain.utils.p2p.sync.StatusPayload
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
class SocketSenderComponent(private val log: Logger,
                            private val syncComponent: SyncComponent) {

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

    // Var used to know if we are currently syncing the blockchain or not
    private var syncInProgress = false

    /**
     * Function use to list all the node of this current server
     */
    fun nodes() = clients.keys.toList()

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
                    clients[NodePojo(knownHost, knownPort)] = rSocket
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
                    if(!syncInProgress) sync(socket)
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

        if (Inet4Address.getLocalHost().hostAddress.equals(node.host, true) ||
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
     * Function used to ask sync to other node
     */
    fun sync(socket: RSocket) {
        // Get the max available client
        var socketTmp: RSocket? = null
        for (client in clients) {
            if (socketTmp == null || client.value.availability() > socketTmp.availability()) {
                socketTmp = client.value
            }
        }

        // Fetch ur current status
        val status = syncComponent.getStatus()

        // Send the sync request to the client
        log.info("Send the status of the blockchain to the known nodes, see if we need a sync or not")
        socket.let {
            it.requestStream(StatusPayload(status).get())
                    .doOnNext {payload ->
                        syncComponent.receivedSyncResponse(payload)
                    }.doOnComplete {
                        log.info("End of the synchronization with other node")
                    }.doOnError {
                        log.error("Error occured during synchronization {}", it.message)
                    }.subscribeOn(Schedulers.io())
                    .subscribe()
        } ?: run {
            log.error("Error when launching the sync request, unable to find a socket available")
        }
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
                    }.doOnError { error ->
                        log.warn("Error when sending the \"{}\" broadcast to the node {} : {}", payload.metadataUtf8, entry.key, error)
                    }.subscribeOn(Schedulers.io()).subscribe()
        }
    }
}

/**
 * Payload description :
 *  -> metadata = data type transfered (only string for title, or for wallet / transaction / block custom metadata object created with gson)
 *  -> data = data transfered by payload ( created with gson)
 *
 * Exchange :
 *  -> join :
 *      -> req payload
 *          -> metadata = "join"
 *          -> data = object composed of : local node + ? (other data needed to join the blockchain network)
 *      -> resp payloads (flowable)
 *          -> current nodes payload
 *              -> metadata = "nodes"
 *              -> data = ArrayList of node present on blockchain sorted by current availability
 *          -> blockchain payload
 *              -> metadata = "blockchain_status"
 *              -> data = Last block in the blockchain (the client will handle the blockchain sync when he will get a connection to all the node)
 *          -> transaction payload
 *              -> metadata = "transaction_status"
 *              -> data = Last transaction in the pool (the client will handle the transaction sync after the blockchain sync)
 *          -> wallet payload
 *              -> metadata = "wallet_status"
 *              -> data = Wallet count the client will get all the wallet before the blockchain sync)
 *
 *  -> new_node (broadcasted to all node when received a join request or when a node finished to connect to all the other node ??)
 *      -> req payload
 *          -> metadata = "node_joined"
 *          -> data = the node that joined the network
 *      -> resp (completable)
 *
 *  -> wallet sync
 *      -> req payload
 *          -> metadata = "wallet_sync"
 *          -> data = last wallet in db hash
 *      -> resp payload (single)
 *          -> metadata = "wallets"
 *          -> data = ArrayList of wallet in the network
 *
 *  -> blockchain sync
 *      -> req payload
 *          -> metadata = "blockchain_sync"
 *          -> data = last block hash
 *      -> resp payloads (flowable, same payload repeated until the last block wa send)
 *          -> metadata = "blocks"
 *          -> data = List of five block starting to the oldest to the newer
 *
 *  -> transaction sync = same as blockchain sync but for transaction
 *
 * Merge wallet, blockchain & transaction sync in the same request ?
 * Add a service to check on other node only the status of all of this ?
 * Checking each block when sync, if bad then resync on another node ? suffisant to prevent bad block intrusion ?
 *
 *  -> add_wallet (broadcast)
 *      -> req payload
 *          -> metadata = "add_wallet"
 *          -> data = object describing the newly created wallet
 *      -> resp (completable)
 *
 *  -> publish_transaction (broadcast)
 *      -> req payload
 *          -> metadata = "publish_transaction"
 *          -> data = object describing the new transaction
 *      -> resp (completable)
 *
 *  -> start_mining_block (broadcast)
 *      -> req payload
 *          -> metadata = "start_mining_block"
 *          -> data = Array of transaction hash that will be mined (with start timestamp)
 *      -> resp (completable)
 *
 *  -> block_mined (broadcast)
 *      -> req payload
 *          -> metadata = "block_mined"
 *          -> data = the newly created block and the transaction id to remove from the mining pool
 *      -> resp (completable)
 *
 *  Crypting the blockchain related stuff with priv key wallet and asserting veracity with him pub key ?
 *      -> publish_transaction & block_mined request metadata to crypt ?
 *      -> custom error if bad signature ?

 */

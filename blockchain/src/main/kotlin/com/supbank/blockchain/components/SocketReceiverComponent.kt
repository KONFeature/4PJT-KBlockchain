package com.supbank.blockchain.components

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.utils.P2pException
import com.supbank.blockchain.utils.p2p.P2pPayload
import com.supbank.blockchain.utils.p2p.sync.NewNodePayload
import com.supbank.blockchain.utils.p2p.sync.NodesPayload
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.rsocket.kotlin.Payload
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketFactory
import io.rsocket.kotlin.Setup
import io.rsocket.kotlin.transport.netty.server.NettyContextCloseable
import io.rsocket.kotlin.transport.netty.server.WebsocketServerTransport
import io.rsocket.kotlin.util.AbstractRSocket
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Server socket, receive all the request comming from the node
 */
@Component
class SocketReceiverComponent(private val socketSender: SocketSenderComponent,
                              private val walletComponent: WalletComponent,
                              private val miningComponent: MiningComponent,
                              private val syncComponent: SyncComponent,
                              private val log: Logger) {

    // Server that receive the request
    private var server: NettyContextCloseable? = null

    @Value("\${p2p.port}")
    var serverPort: Int = 0

    @Value("\${server.port}")
    var webserverPort: Int = 0

    /**
     * Init the server
     */
    @PostConstruct
    private fun init() {
        RSocketFactory
                .receive()
                .acceptor { { setup, rSocket -> handle(setup, rSocket) } }
                .transport(WebsocketServerTransport.create(serverPort))
                .start()
                .observeOn(Schedulers.io())
                .subscribe({ context ->
                    log.info("Server running on address {}", context.address())
                    log.info("Vous pouvez accedez a la webapp via 'localhost:$webserverPort'")
                    server = context
                }, { error ->
                    log.warn("Error when launching the socket server {}", error)
                })
    }

    /**
     * Handle incoming message
     * param : setup = the client setup, rSocket = the client socket
     */
    private fun handle(setup: Setup, rSocket: RSocket): Single<RSocket> = Single.just(object : AbstractRSocket() {

        override fun fireAndForget(payload: Payload): Completable {
            log.debug("Server received f&f, get {}->{}", payload.dataUtf8, payload.metadataUtf8)

            return when {
                P2pPayload.isNewNode(payload) -> {
                    // Received new node
                    try {
                        val node = Gson().fromJson(payload.dataUtf8, NodePojo::class.java)
                        socketSender.addClient(node)
                    } catch(e: JsonSyntaxException) {
                        log.error("Unable to parse to payload to a node object {}, {}", payload.dataUtf8, e.message)
                    }
                    Completable.complete()
                }
                P2pPayload.isAddWallet(payload) -> walletComponent.receivedPayload(payload)
                P2pPayload.isUpdateWallet(payload) -> walletComponent.receivedPayload(payload)
                P2pPayload.isPublishTransaction(payload) -> walletComponent.receivedTransaction(payload)
                P2pPayload.isBlockMined(payload) -> miningComponent.receivedBlockMined(payload)
                else -> Completable.error(P2pException.unknownOperationException(payload))
            }
        }

        override fun requestResponse(payload: Payload): Single<Payload> {
            log.debug("Server received rr, get {}->{}", payload.dataUtf8, payload.metadataUtf8)
            return Single.error(P2pException.unknownOperationException(payload))
        }

        override fun requestStream(payload: Payload): Flowable<Payload> {
            log.debug("Server received rs get {}->{}", payload.dataUtf8, payload.metadataUtf8)

            return when {
                P2pPayload.isJoin(payload) -> // Received a join request
                    joinResponse(payload)
                P2pPayload.isStatus(payload) -> // Received a status of an other node in the network
                    syncComponent.receivedStatus(payload)
                else -> // Unknown operation request
                    Flowable.error<Payload>(P2pException.unknownOperationException(payload))
            }
        }
    })

    /**
     * Handle join request
     */
    private fun joinResponse(payload: Payload) : Flowable<Payload> {
        val requesterNode = Gson().fromJson(payload.dataUtf8, NodePojo::class.java)

        // Saving new node
        socketSender.addClient(requesterNode)

        // Send the new node to other node
        socketSender.broadcastFf(NewNodePayload(requesterNode).get())

        // Return known node to new node
        return Flowable.create({ emitter ->
            // Find all the node in DB, and send them (size limit to prevent network overload)
            val nodes = socketSender.nodes().iterator()
            val toSend = ArrayList<NodePojo>()
            while (nodes.hasNext()) {
                if(toSend.size >= NodesPayload.arraySize) {
                    emitter.onNext(NodesPayload(toSend).get())
                    toSend.clear()
                }
                toSend.add(nodes.next())
            }

            // If we have nodes not send we s end them now
            if(toSend.isNotEmpty())
                emitter.onNext(NodesPayload(toSend).get())

            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }
}

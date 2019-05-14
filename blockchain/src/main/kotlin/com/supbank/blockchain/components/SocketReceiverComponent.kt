package com.supbank.blockchain.components

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.WalletRepository
import com.supbank.blockchain.utils.P2pException
import com.supbank.blockchain.utils.p2p.NewNodePayload
import com.supbank.blockchain.utils.p2p.NodesPayload
import com.supbank.blockchain.utils.p2p.P2pPayload
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import io.rsocket.kotlin.*
import io.rsocket.kotlin.transport.netty.server.NettyContextCloseable
import io.rsocket.kotlin.transport.netty.server.WebsocketServerTransport
import io.rsocket.kotlin.util.AbstractRSocket
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException
import javax.annotation.PostConstruct

/**
 * Server socket, receive all the request comming from the node
 */
@Component
class SocketReceiverComponent(private val socketSender: SocketSenderComponent,
                              private val walletRepository: WalletRepository,
                              private val blockchainRepository: BlockchainRepository,
                              private val log: Logger) {

    // Server that receive the request
    private var server: NettyContextCloseable? = null

    @Value("\${p2p.port}")
    var serverPort: Int = 0

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
                P2pPayload.isAddWallet(payload) -> {
                    // Received a new wallet
                    try {
                        val wallet = Gson().fromJson(payload.dataUtf8, Wallet::class.java)
                        if(!walletRepository.existsById(wallet.id)) {
                            walletRepository.save(wallet)
                            Completable.complete()
                        } else {
                            Completable.error(P2pException.walletKnownException(payload))
                        }
                    } catch(e: JsonSyntaxException) {
                        log.error("Unable to parse to payload to a wallet object {}, {}", payload.dataUtf8, e.message)
                        Completable.error(P2pException.walletKnownException(payload))
                    }
                }
                P2pPayload.isPublishTransaction(payload) -> {
                    try {
                        val block: Block? = Gson().fromJson(payload.dataUtf8, Block::class.java)
                        // TODO : Check last hash corresponding to last block in blockchain, else abort add and resync
                    } catch(e: JsonSyntaxException) {
                        log.error("Unable to parse to payload to a block object {}, {}", payload.dataUtf8, e.message)
                    }
                    Completable.error(P2pException.unknownOperationException(payload))
                }
                P2pPayload.isBlockMined(payload) -> Completable.error(P2pException.unknownOperationException(payload))
                else -> Completable.error(P2pException.unknownOperationException(payload))
            }
        }

        override fun requestResponse(payload: Payload): Single<Payload> {
            log.debug("Server received rr, get {}->{}", payload.dataUtf8, payload.metadataUtf8)
            return Single.error(P2pException.unknownOperationException(payload))
        }

        override fun requestStream(payload: Payload): Flowable<Payload> {
            log.debug("Server received rs get {}->{}", payload.dataUtf8, payload.metadataUtf8)

            return if(P2pPayload.isJoin(payload)) {
                // Received a join request
                joinResponse(payload)
            } else {
                // Unknown operation request
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

            // TODO : Return blockchain, transactions and wallets status
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }
}

package com.supbank.blockchain.components

import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.repos.NodeRepository
import com.supbank.blockchain.utils.Request
import io.reactivex.*
import io.reactivex.schedulers.Schedulers
import io.rsocket.kotlin.*
import io.rsocket.kotlin.transport.netty.server.NettyContextCloseable
import io.rsocket.kotlin.transport.netty.server.WebsocketServerTransport
import io.rsocket.kotlin.util.AbstractRSocket
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.lang.NullPointerException
import javax.annotation.PostConstruct

/**
 * Server socket, receive all the request comming from the node
 */
@Component
class SocketReceiverComponent(private var nodeRepository: NodeRepository,
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
            log.debug("Server received f&f, payload {}->{}", payload.dataUtf8, payload.metadataUtf8)
            return Completable.complete()
        }

        override fun requestResponse(payload: Payload): Single<Payload> {
            log.debug("Server received rr, payload {}->{}", payload.dataUtf8, payload.metadataUtf8)
            return Single.just(DefaultPayload.text("server handler response"))
        }

        override fun requestStream(payload: Payload): Flowable<Payload> {
            log.debug("Server received rs payload {}->{}", payload.dataUtf8, payload.metadataUtf8)

            when(payload.dataUtf8) {
                // The client ask the server to list all the known nodes
                Request.LIST_NODES.payload.dataUtf8 -> {
                    return Flowable.create({ emitter ->
                        // Return local node
                        server?.let {
                            emitter.onNext(DefaultPayload.text(NodePojo.TITLE, NodePojo(it.address().hostString, it.address().port).json()))
                        }

                        // Return node in db
                        nodeRepository.findAll().forEach { node ->
                            NodePojo.fromNode(node)?.let {pojo ->
                                emitter.onNext(DefaultPayload.text(NodePojo.TITLE, pojo.json()))
                            }?:run {
                                emitter.onError(NullPointerException("Unable to parse node to pojo"))
                            }
                        }
                        emitter.onComplete()
                    }, BackpressureStrategy.BUFFER)
                }
                else -> {
                    log.warn("Unknown payload request {}->{}", payload.dataUtf8, payload.metadataUtf8)
                    return Flowable.just(DefaultPayload.text("Server doesn't recognize request"))
                }
            }
        }

    })
}
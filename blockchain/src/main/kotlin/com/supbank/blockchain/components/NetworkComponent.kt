package com.supbank.blockchain.components

import com.supbank.blockchain.components.sockets.ClientSocket
import com.supbank.blockchain.components.sockets.ServerSocket
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.rsocket.kotlin.*
import io.rsocket.kotlin.transport.netty.client.WebsocketClientTransport
import io.rsocket.kotlin.transport.netty.server.NettyContextCloseable
import io.rsocket.kotlin.transport.netty.server.WebsocketServerTransport
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class NetworkComponent(private val log: Logger) {

    // Server and server clients
    var server: NettyContextCloseable? = null
    var clients: ArrayList<RSocket> = ArrayList()

    // Client socket
    var client: RSocket? = null

    @Value("\${blockchain.known.node.host}")
    lateinit var knownHost: String

    @Value("\${blockchain.known.node.port}")
    var knownPort: Int = 0

    @Value("\${p2p.port}")
    var serverPort: Int = 0

    @PostConstruct
    private fun init() {
        log.info("Init of the socket for the p2p connexion")

        initServer()
    }

    /**
     * Init the server
     */
    private fun initServer() {
        val serverFlux: Single<NettyContextCloseable> = RSocketFactory
                .receive()
                .acceptor { { setup, rSocket -> handleServer(setup, rSocket) } }
                .transport(WebsocketServerTransport.create(serverPort))
                .start()

        serverFlux
                .observeOn(Schedulers.io())
                .subscribe { context ->
                    log.info("Server running on address {}", context.address())
                    server = context

                    // Launch client init when server ok
                    initClient()
                }
    }

    /**
     * Init the client
     */
    private fun initClient() {
        // Param not used on the acceptor -> the server sending the request
        val clientFlux: Single<RSocket> = RSocketFactory
                .connect()
                .acceptor { { ClientSocket(log) } }
                .transport {
                    WebsocketClientTransport
                            .create(knownHost, knownPort)
                }
                .start()

        clientFlux
                .observeOn(Schedulers.io())
                .subscribe { rSocket ->
                    log.info("Client running with availability {}", rSocket.availability())
                    client = rSocket
                }
    }

    /**
     * Handle server message
     * param : setup = the client setup, rSocket = the client socket
     */
    private fun handleServer(setup: Setup, rSocket: RSocket): Single<RSocket> {
        if (!clients.contains(rSocket)) {
            log.info("Adding socket to server clients with availability {}", rSocket.availability())
            clients.add(rSocket)
        }

        return Single.just(ServerSocket(log))
    }

    /**
     * Public method used to send data from the client to the server
     */
    fun send(text: String) {
        val responseFlux = client?.requestStream(DefaultPayload.text(text))
        responseFlux?.let { resp ->
            resp.observeOn(Schedulers.io())
                    .subscribe({ payload ->
                        log.info("Receive from request \"{}\" payload : {}", text, payload.dataUtf8)
                    }, { error ->
                        log.info("Error from request \"{}\" : {}", text, error)
                    })
        }
    }

    /**
     * Public method used to send data from the server to the client
     */
    fun broadcast(text: String) {
        clients.forEach { socket ->
            val responseFlux = socket.requestStream(DefaultPayload.text(text))
            responseFlux.let { resp ->
                resp.observeOn(Schedulers.io())
                        .subscribe({ payload ->
                            log.info("Receive from broadcast \"{}\" payload : {}", text, payload.dataUtf8)
                        }, { error ->
                            log.info("Error from broadcast \"{}\" : {}", text, error)
                        })
            }
        }
    }
}
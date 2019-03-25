package com.supbank.blockchain.components.sockets

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.rsocket.kotlin.DefaultPayload
import io.rsocket.kotlin.Payload
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.util.AbstractRSocket
import org.reactivestreams.Publisher
import org.slf4j.Logger

class ServerSocket(val log: Logger): AbstractRSocket() {

    override fun fireAndForget(payload: Payload): Completable {
        log.info("Server received f&f, payload {}", payload.dataUtf8)
        return Completable.complete()
    }

    override fun metadataPush(payload: Payload): Completable {
        log.info("Server received mp, payload {}", payload.dataUtf8)
        return Completable.complete()
    }

    override fun requestChannel(payloads: Publisher<Payload>): Flowable<Payload> {
        TODO("not implemented")
    }

    override fun requestResponse(payload: Payload): Single<Payload> {
        log.info("Server received rr, payload {}", payload.dataUtf8)
        return Single.just(DefaultPayload.text("server handler response"))
    }

    override fun requestStream(payload: Payload): Flowable<Payload> {
        log.info("Server received payload {}", payload.dataUtf8)
        return Flowable.just(DefaultPayload.text("server handler response"))
    }
}
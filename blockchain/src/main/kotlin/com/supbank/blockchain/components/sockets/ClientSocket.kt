package com.supbank.blockchain.components.sockets

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.rsocket.kotlin.DefaultPayload
import io.rsocket.kotlin.Payload
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.util.AbstractRSocket
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.slf4j.Logger

class ClientSocket(val log: Logger): AbstractRSocket() {
    override fun fireAndForget(payload: Payload): Completable {
        log.info("Client received f&f, payload {}", payload.dataUtf8)
        return Completable.complete()
    }

    override fun metadataPush(payload: Payload): Completable {
        log.info("Client received mp, payload {}", payload.dataUtf8)
        return Completable.complete()
    }

    override fun requestChannel(payloads: Publisher<Payload>): Flowable<Payload> {
        TODO("not implemented")
    }

    override fun requestResponse(payload: Payload): Single<Payload> {
        log.info("Client received rr, payload {}", payload.dataUtf8)
        return Single.just(DefaultPayload.text("client handler response"))
    }

    override fun requestStream(payload: Payload): Flowable<Payload> {
        log.info("Client received rs, payload {}", payload.dataUtf8)
        return Flowable.just(DefaultPayload.text("client handler response"))
    }
}
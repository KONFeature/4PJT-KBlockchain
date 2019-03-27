package com.supbank.blockchain.utils.p2p

import io.rsocket.kotlin.DefaultPayload
import io.rsocket.kotlin.Payload

abstract class P2pPayload(var title: String) {

    companion object {
        fun isJoin(payload: Payload) = payload.metadataUtf8 == JoinPayload.title
        fun isNewNode(payload: Payload) = payload.metadataUtf8 == NewNodePayload.title
        fun isNodes(payload: Payload) = payload.metadataUtf8 == NodesPayload.title
    }

    abstract fun getData(): String

    fun get() = DefaultPayload(getData(), title)
}
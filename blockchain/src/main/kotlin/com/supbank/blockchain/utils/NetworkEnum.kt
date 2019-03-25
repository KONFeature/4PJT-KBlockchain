package com.supbank.blockchain.utils

import io.rsocket.kotlin.DefaultPayload

enum class Request(val payload: io.rsocket.kotlin.Payload) {
    LIST_NODES(DefaultPayload.text("list_nodes"))
}
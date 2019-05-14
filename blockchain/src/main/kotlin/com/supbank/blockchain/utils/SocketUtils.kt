package com.supbank.blockchain.utils

import io.rsocket.kotlin.DefaultPayload
import io.rsocket.kotlin.Payload

/**
 * Class helping us to create request get
 * TODO : Realy usefull ?
 */
class SocketUtils {
}

/**
 * Class defining all the custom exception linked to p2p error
 */
class P2pException(val msg: String) : Exception(msg) {
    companion object {
        fun unknownOperationException(payload: Payload) = P2pException("Received an unknown operation : \"" + payload.metadataUtf8 + "\"")

        fun walletKnownException(payload: Payload) = P2pException("Received a known wallet to add : \"" + payload.metadataUtf8 + "\"")
    }


}

package com.supbank.blockchain.utils

import io.rsocket.kotlin.DefaultPayload
import io.rsocket.kotlin.Payload

/**
 * Class defining all the custom exception linked to p2p error
 */
class P2pException(private val msg: String) : Exception(msg) {
    companion object {
        fun unknownOperationException(payload: Payload) = P2pException("Received an unknown operation : \"${payload.metadataUtf8}\"")
        fun unableToParseException(payload: Payload, msg: String?) = P2pException("Unable to parse the received data : \"${payload.metadataUtf8}\" : $msg")

        fun walletKnownException(payload: Payload) = P2pException("Received a known wallet to add : \"${payload.metadataUtf8}\"")
        fun transactionKnownException(payload: Payload) = P2pException("Received a known transaction to add : \"${payload.metadataUtf8}\"")
        fun exception(payload: Payload, msg: String?) = P2pException("Exception occured : \"${payload.metadataUtf8}\" : $msg")
    }


}

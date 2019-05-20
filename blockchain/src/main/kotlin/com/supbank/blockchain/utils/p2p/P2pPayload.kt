package com.supbank.blockchain.utils.p2p

import com.supbank.blockchain.utils.p2p.sync.*
import com.supbank.blockchain.utils.p2p.wallet.AddWalletPayload
import com.supbank.blockchain.utils.p2p.wallet.PublishTransactionPayload
import io.rsocket.kotlin.DefaultPayload
import io.rsocket.kotlin.Payload

abstract class P2pPayload(var title: String) {

    companion object {
        fun isJoin(payload: Payload) = payload.metadataUtf8 == JoinPayload.title
        fun isNewNode(payload: Payload) = payload.metadataUtf8 == NewNodePayload.title
        fun isNodes(payload: Payload) = payload.metadataUtf8 == NodesPayload.title
        fun isAddWallet(payload: Payload) = payload.metadataUtf8 == AddWalletPayload.title
        fun isPublishTransaction(payload: Payload) = payload.metadataUtf8 == PublishTransactionPayload.title
        fun isBlockMined(payload: Payload) = payload.metadataUtf8 == BlockMinedPayload.title
        fun isStatus(payload: Payload) = payload.metadataUtf8 == StatusPayload.title
    }

    abstract fun getData(): String

    fun get() = DefaultPayload(getData(), title)
}

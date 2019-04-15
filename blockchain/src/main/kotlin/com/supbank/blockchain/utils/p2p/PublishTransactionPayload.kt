package com.supbank.blockchain.utils.p2p

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.supbank.blockchain.models.Wallet

class PublishTransactionPayload(private val transaction: Transient) : P2pPayload(title) {
    companion object {
        const val title = "publish_transaction"
    }
    override fun getData() = Gson().toJson(transaction)
}

package com.supbank.blockchain.utils.p2p.wallet

import com.google.gson.Gson
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.utils.p2p.P2pPayload

class PublishTransactionPayload(private val transaction: Transaction) : P2pPayload(title) {
    companion object {
        const val title = "publish_transaction"
    }
    override fun getData(): String = Gson().toJson(transaction)
}

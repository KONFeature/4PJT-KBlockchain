package com.supbank.blockchain.utils.p2p

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.supbank.blockchain.models.Wallet

class AddWalletPayload(private val wallet: Wallet) : P2pPayload(title) {
    companion object {
        const val title = "add_wallet"
    }
    override fun getData() = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(wallet)
}

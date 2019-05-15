package com.supbank.blockchain.utils.p2p

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.utils.GsonUtils

class AddWalletPayload(private val wallet: Wallet) : P2pPayload(title) {
    companion object {
        const val title = "add_wallet"
    }
    override fun getData() = GsonUtils.getWalletCustom().toJson(wallet)
}

package com.supbank.blockchain.utils.p2p.wallet

import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.utils.GsonUtils
import com.supbank.blockchain.utils.p2p.P2pPayload

class AddWalletPayload(private val wallet: Wallet) : P2pPayload(title) {
    companion object {
        const val title = "add_wallet"
    }
    override fun getData() = GsonUtils.getWalletCustom().toJson(wallet)
}

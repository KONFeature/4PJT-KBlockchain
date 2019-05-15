package com.supbank.blockchain.utils.p2p.sync

import com.google.gson.Gson
import com.supbank.blockchain.pojo.BlockchainStatusPojo
import com.supbank.blockchain.utils.p2p.P2pPayload

class StatusPayload(private val blockchainStatusPojo: BlockchainStatusPojo) : P2pPayload(title) {
    companion object {
        const val title = "status"
    }

    // Data = blockchain status
    override fun getData() = Gson().toJson(blockchainStatusPojo)
}

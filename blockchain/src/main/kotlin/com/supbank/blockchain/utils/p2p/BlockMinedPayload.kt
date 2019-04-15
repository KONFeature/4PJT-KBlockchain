package com.supbank.blockchain.utils.p2p

import com.google.gson.Gson
import com.supbank.blockchain.models.Block

class BlockMinedPayload(private val block: Block) : P2pPayload(title) {
    companion object {
        const val title = "block_mined"
    }
    override fun getData() = Gson().toJson(block)
}

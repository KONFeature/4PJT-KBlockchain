package com.supbank.blockchain.utils.p2p.sync

import com.google.gson.Gson
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.utils.p2p.P2pPayload

/**
 * Create a join payload
 */
class JoinPayload(private val localNode: NodePojo) : P2pPayload(title) {
    companion object {
        const val title = "join"
    }

    // Data = current node pojo
    override fun getData(): String = Gson().toJson(localNode)
}

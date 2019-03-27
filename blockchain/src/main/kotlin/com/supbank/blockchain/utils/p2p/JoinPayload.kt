package com.supbank.blockchain.utils.p2p

import com.google.gson.Gson
import com.supbank.blockchain.pojo.NodePojo
import io.rsocket.kotlin.Payload

/**
 * Create a join payload
 */
class JoinPayload(private val localNode: NodePojo) : P2pPayload(title) {
    companion object {
        const val title = "join"
    }

    // Data = current node pojo
    override fun getData() = Gson().toJson(localNode)
}
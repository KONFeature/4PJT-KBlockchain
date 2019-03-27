package com.supbank.blockchain.utils.p2p

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.supbank.blockchain.pojo.NodePojo
import io.rsocket.kotlin.Payload

/**
 * Create list of known node payload
 */
class NodesPayload(private val nodes: List<NodePojo>) : P2pPayload(title) {
    companion object {
        const val title = "join"
        const val arraySize = 5
        val type = object : TypeToken<ArrayList<NodePojo>>() {}.type
    }

    // Data = current node pojo
    override fun getData() = Gson().toJson(nodes)
}
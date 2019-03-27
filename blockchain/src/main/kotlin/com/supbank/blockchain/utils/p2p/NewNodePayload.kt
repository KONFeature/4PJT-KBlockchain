package com.supbank.blockchain.utils.p2p

import com.google.gson.Gson
import com.supbank.blockchain.pojo.NodePojo

class NewNodePayload(private val newNode: NodePojo) : P2pPayload(title) {
    companion object {
        const val title = "new_node"
    }

    // Data = new node that join the network
    override fun getData() = Gson().toJson(newNode)
}
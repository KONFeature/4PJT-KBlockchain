package com.supbank.blockchain.utils.p2p.sync

import com.google.gson.Gson
import com.supbank.blockchain.pojo.NodePojo
import com.supbank.blockchain.utils.p2p.P2pPayload

class NewNodePayload(private val newNode: NodePojo) : P2pPayload(title) {
    companion object {
        const val title = "new_node"
    }

    // Data = new node that join the network
    override fun getData(): String = Gson().toJson(newNode)
}

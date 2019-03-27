package com.supbank.blockchain.pojo

import com.google.gson.Gson
import com.supbank.blockchain.models.Node
import io.rsocket.kotlin.Payload

data class NodePojo(val host: String,
                    val port: Int) {

    companion object {
        /**
         * Convert a node to a node pojo
         */
        fun fromNode(node : Node) : NodePojo = NodePojo(node.host, node.port)
    }
}
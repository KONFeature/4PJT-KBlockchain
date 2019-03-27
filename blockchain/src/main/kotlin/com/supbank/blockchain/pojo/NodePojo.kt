package com.supbank.blockchain.pojo

import com.supbank.blockchain.models.Node

data class NodePojo(val host: String,
                    val port: Int) {

    companion object {
        /**
         * Convert a node to a node pojo
         */
        fun fromNode(node : Node) : NodePojo = NodePojo(node.host, node.port)
    }
}
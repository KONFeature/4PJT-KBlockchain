package com.supbank.blockchain.pojo

import com.google.gson.Gson
import com.supbank.blockchain.models.Node
import io.rsocket.kotlin.Payload

data class NodePojo(val host: String,
                    val port: Int) {

    companion object {

        // Title for payload
        const val TITLE = "node_pojo"

        /**
         * Convert a payload to a node pojo
         */
        fun fromPayload(payload: Payload) : NodePojo? {
            if(payload.dataUtf8 != TITLE)
                return null

            return Gson().fromJson(payload.metadataUtf8, NodePojo::class.java)
        }

        /**
         * Convert a node to a node pojo
         */
        fun fromNode(node : Node) : NodePojo? {
            if(node.host.isEmpty())
                return null

            return NodePojo(node.host, node.port)
        }
    }

    fun json() = Gson().toJson(this)
}
package com.supbank.blockchain.utils

enum class RequestHeader(val data: String) {
    DECLARE_ITSELFT("declare_itselft"),
    LIST_NODES("list_nodes"),
    FETCH_BLOCKCHAIN("fetch_blockchain"),
    PUBLISH_TRANSACTION("publish_transaction"),
    PUBLISH_BLOCK("publish_block")
}
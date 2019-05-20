package com.supbank.blockchain.pojo

data class BlockchainStatusPojo(
        val walletLastId: Long,
        val transactionLastId: Long,
        val blockLastId: Long,
        val lastBlockHash: String?
)

package com.supbank.blockchain.pojo

data class BlockchainStatusPojo(
        val walletCount: Long,
        val transactionCount: Long,
        val blockCount: Long,
        val lastBlockHash: String?
)

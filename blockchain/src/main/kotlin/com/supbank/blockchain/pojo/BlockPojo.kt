package com.supbank.blockchain.pojo

import com.supbank.blockchain.models.Block

data class BlockPojo(val prevHash: String,
                     val hash: String,
                     val timestamp: Long,
                     val id: Long,
                     val transactionsCount: Int,
                     val nonce: Long) {
    companion object {
        fun fromBlock(block: Block) : BlockPojo =
                BlockPojo(block.prevHash, block.hash, block.timestamp, block.id, block.transactions.count(), block.nonce)
    }


}

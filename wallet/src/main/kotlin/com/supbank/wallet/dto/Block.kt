package com.supbank.wallet.dto

data class Block(val id: Long,
                 val prevHash: String,
                 val hash: String,
                 val nonce: Long,
                 val transactions: Collection<Transaction>)

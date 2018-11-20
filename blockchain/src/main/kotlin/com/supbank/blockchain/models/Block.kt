package com.supbank.blockchain.models

data class Block(val prevHash: ByteArray,
                 val hash: ByteArray,
                 val sender: ByteArray,
                 var receiver: ByteArray,
                 var amount: Int,
                 var message: String)
package com.supbank.blockchain.pojo.request

data class CreateTransactionRequest(val message: String,
                                    val amount: Int,
                                    val receiver: Long)

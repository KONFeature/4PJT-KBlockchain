package com.supbank.wallet.dto

data class CreateTransactionRequest(val message: String,
                                    val amount: Int,
                                    val receiver: Long)

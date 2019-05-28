package com.supbank.wallet.dto

data class Transaction(val id: Long,
                       val senderId: Long,
                       val receiverId: Long,
                       val amount: Int,
                       val message: String,
                       val mined: Boolean)

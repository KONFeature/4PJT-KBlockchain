package com.supbank.wallet.dto

data class Wallet(val id: Long,
                  val name: String,
                  val amount: Long,
                  val mail: String?,
                  val token: String?)

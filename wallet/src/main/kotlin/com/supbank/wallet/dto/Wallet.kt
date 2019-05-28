package com.supbank.wallet.dto

import java.security.PublicKey
import java.util.*

data class Wallet(val id: Long,
                  val name: String,
                  val amount: Long,
                  val mail: String?,
                  val token: String?)

package com.supbank.blockchain.pojo.request

import java.util.*

data class CreateWalletRequest(val name: String,
                               val mail: Optional<String>,
                               val token: Optional<String>)

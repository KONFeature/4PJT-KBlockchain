package com.supbank.blockchain.pojo

import java.util.*

data class CreateWalletRequest(val name: String,
                               val mail: Optional<String>,
                               val token: Optional<String>)

package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.SocketSenderComponent
import com.supbank.blockchain.components.WalletComponent
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*

/**
 * Handle creation / transaction
 */
@RestController
@RequestMapping("/wallet")
class WalletController(private val sender: SocketSenderComponent,
                       private val walletComponent: WalletComponent,
                       private val log: Logger) {

    @GetMapping("/create")
    fun createWallet(@RequestParam("name") name: String) : String {
        // Create new wallet
        val wallet = walletComponent.create(name)
        return wallet?.let { "Wallet created : $it" }?:kotlin.run { "Error during wallet creation" }
    }

    @GetMapping("/load")
    fun loadWallet() : String {
        // Try to load a wallet
        val wallet = walletComponent.load()
        return wallet?.let { "Wallet loaded : $it" }?:kotlin.run { "Error during wallet loading" }
    }

    @GetMapping("/publish")
    fun publishTransaction() : String {
        // Check if wallet is logged in
        // If yes create a transaction
        return "To implement"
    }
}

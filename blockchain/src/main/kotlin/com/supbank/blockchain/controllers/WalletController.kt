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
    fun create(@RequestParam("name") name: String) : String {
        // Create new wallet
        val wallet = walletComponent.create(name)
        return wallet?.let { "Wallet created : $it" }?:kotlin.run { "Error during wallet creation" }
    }

    @GetMapping("/load")
    fun load() : String {
        // Try to load a wallet
        val wallet = walletComponent.load()
        return wallet?.let { "Wallet loaded : $it" }?:kotlin.run { "Error during wallet loading" }
    }

    @GetMapping("/status")
    fun status() : String {
        val wallet = walletComponent.wallet
        return wallet?.let { "Current wallet : $it" }?:kotlin.run { "No wallet loaded" }
    }

    @GetMapping("/publish")
    fun publishTransaction(msg: String, amount: Long, receiverId: Long) : String {
        // Check if wallet is logged in
        // If yes create a transaction
        return "To implement"
    }
}

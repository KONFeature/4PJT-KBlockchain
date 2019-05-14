package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.SocketSenderComponent
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Handle creation / transaction
 */
@RestController
@RequestMapping("/wallet")
class WalletController(private val sender: SocketSenderComponent,
                       private val log: Logger) {

    @PostMapping("/create")
    fun createWallet() : String {
        // Check if we can load a wallet
        // Else create a new one
        return "To implement"
    }

    @PostMapping("/load")
    fun loadWallet() : String {
        // Try to load a wallet
        return "To implement"
    }

    @PostMapping("/publish")
    fun addTransaction() : String {
        // Check if wallet is logged in
        // If yes create a transaction
        return "To implement"
    }
}

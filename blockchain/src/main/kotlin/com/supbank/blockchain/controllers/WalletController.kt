package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.SocketSenderComponent
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Handle creation / transaction
 */
@RestController
@RequestMapping("/wallet")
class WalletController(private val sender: SocketSenderComponent,
                       private val log: Logger) {

    @GetMapping("/publish")
    fun addTransaction() : String {
        return "To implement"
    }

}
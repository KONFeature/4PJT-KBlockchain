package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.NetworkComponent
import com.supbank.blockchain.models.Client
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * P2p
 */
@RestController
@RequestMapping("/network")
class NetworkController(private val network: NetworkComponent,
                        private val log: Logger) {

    var clients = ArrayList<Client>()

    /**
     * Join the blockchain network
     */
    @GetMapping("/join")
    fun joinNetwork() : String {
        network.send("connect")
        return "To implement"
    }

    /**
     * Boardcast the blockchain network
     */
    @GetMapping("/broadcast")
    fun broadcastNetwork() : String {
        network.broadcast("broadcast")
        return "To implement"
    }

    /**
     * List all the known nodes by this host
     */
    @PostMapping("/nodes")
    fun listNodes() = clients

}
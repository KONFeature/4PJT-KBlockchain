package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.SocketSenderComponent
import com.supbank.blockchain.models.Node
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
class NetworkController(private val sender: SocketSenderComponent,
                        private val log: Logger) {

    /**
     * List all the known nodes by this host
     */
    @PostMapping("/nodes")
    fun listNodes() = sender.nodes()

}

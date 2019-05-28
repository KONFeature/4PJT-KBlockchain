package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.SocketSenderComponent
import com.supbank.blockchain.models.Node
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*

/**
 * Controller for all the network infos
 */
@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/network")
class NetworkController(private val sender: SocketSenderComponent,
                        private val log: Logger) {

    /**
     * List all the known nodes by this host
     */
    @GetMapping("/nodes")
    fun listNodes() = sender.nodes()

}

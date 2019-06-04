package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.SocketSenderComponent
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller for all the network infos
 */
@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/network")
class NetworkController(private val sender: SocketSenderComponent) {

    /**
     * List all the known nodes by this host
     */
    @GetMapping("/nodes")
    fun listNodes() = sender.nodes()

}

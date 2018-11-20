package com.supbank.blockchain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.socket.client.WebSocketClient

@SpringBootApplication
class BlockchainApplication

fun main(args: Array<String>) {
	runApplication<BlockchainApplication>(*args)
}

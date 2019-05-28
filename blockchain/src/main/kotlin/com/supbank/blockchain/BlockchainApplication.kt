package com.supbank.blockchain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.web.reactive.socket.client.WebSocketClient

@PropertySources(*[
	PropertySource("classpath:application.properties")
])
@SpringBootApplication
class BlockchainApplication

fun main(args: Array<String>) {
	runApplication<BlockchainApplication>(*args)
}

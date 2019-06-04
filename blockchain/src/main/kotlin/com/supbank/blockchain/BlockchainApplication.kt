package com.supbank.blockchain

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@PropertySources(PropertySource("file:blockchain.properties"), PropertySource("classpath:application.properties"))
@SpringBootApplication
class BlockchainApplication

fun main(args: Array<String>) {
	runApplication<BlockchainApplication>(*args)
}

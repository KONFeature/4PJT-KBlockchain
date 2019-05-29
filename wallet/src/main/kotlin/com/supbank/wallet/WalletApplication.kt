package com.supbank.wallet

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.math.BigInteger
import java.security.MessageDigest

@SpringBootApplication
class WalletApplication

fun main(args: Array<String>) {
	runApplication<WalletApplication>(*args)
}

/**
 * Little function helping us to print String
 */
fun String.print() =
	System.out.println(this)

package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.MiningComponent
import com.supbank.blockchain.components.SocketSenderComponent
import com.supbank.blockchain.components.WalletComponent
import com.supbank.blockchain.models.Transaction
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.slf4j.Logger
import org.springframework.web.bind.annotation.*

/**
 * Handle creation / transaction
 */
@RestController
@RequestMapping("/wallet")
class WalletController(private val walletComponent: WalletComponent,
                       private val miningComponent: MiningComponent,
                       private val log: Logger) {

    @GetMapping("/create")
    fun create(@RequestParam("name") name: String) : String {
        // Create new wallet
        val wallet = walletComponent.create(name, null, null)
        return wallet?.let { "Wallet created : $it" }?:kotlin.run { "Error during wallet creation" }
    }

    @PostMapping("/create")
    fun createPost(@RequestParam("name") name: String,
                   @RequestParam("mail") mail: String,
                   @RequestParam("token") token: String) : String {
        val wallet = walletComponent.create(name, mail, token)
        return wallet?.let { "Wallet created : $it" }?:kotlin.run { "Error during wallet creation" }
    }

    @GetMapping("/load")
    fun load() : String {
        // Try to load a wallet
        val wallet = walletComponent.load()
        return wallet?.let { "Wallet loaded : $it" }?:kotlin.run { "Error during wallet loading" }
    }

    @GetMapping("/status")
    fun status() : String {
        val wallet = walletComponent.wallet
        return wallet?.let { "Current wallet : $it" }?:kotlin.run { "No wallet loaded" }
    }

    @GetMapping("/publish")
    fun publishTransaction(@RequestParam("message") msg: String,
                           @RequestParam("amount") amount: Int,
                           @RequestParam("receiver") receiverId: Long) : String {
        val transactionStatus = walletComponent.newTransaction(msg, amount, receiverId)
        return if(transactionStatus)
            "Transaction successfully created and submitted on the network"
        else
            "Error during the creation of the transaction, please check the blockchain log"
    }

    @GetMapping("/miner")
    fun miner(@RequestParam("status") status: Boolean) : Boolean {
        return if(status) {
            miningComponent.startMining()
        } else {
            miningComponent.stopMining()
        }
    }

    @GetMapping("/transactions")
    fun transaction() : Flowable<Transaction> {
        return Flowable.create({emitter ->
            walletComponent.getWalletTransactions().forEach { emitter.onNext(it) }
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

}

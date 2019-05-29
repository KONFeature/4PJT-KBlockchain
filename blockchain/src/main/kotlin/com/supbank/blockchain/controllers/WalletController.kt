package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.MiningComponent
import com.supbank.blockchain.components.WalletComponent
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.pojo.CreateWalletRequest
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.slf4j.Logger
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * Handle creation / transaction
 */
@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/wallet")
class WalletController(private val walletComponent: WalletComponent,
                       private val miningComponent: MiningComponent,
                       private val log: Logger) {

    @GetMapping("/create")
    fun create(@RequestParam("name") name: String) : Wallet? {
        // Create new wallet
        return walletComponent.create(name, null, null)
    }

    @PostMapping("/create", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPost(@RequestBody request: CreateWalletRequest) : Wallet? {
        log.error("$request")
        return walletComponent.create(request.name, request.mail, request.token)
    }

    @GetMapping("/load")
    fun load() : Wallet? {
        // Try to load a wallet
        return walletComponent.load()
    }

    @GetMapping("/status")
    fun status() : Wallet? {
        return walletComponent.wallet
    }

    @GetMapping("/publish")
    fun publishTransaction(@RequestParam("message") msg: String,
                           @RequestParam("amount") amount: Int,
                           @RequestParam("receiver") receiverId: Long) : Transaction? {
        return walletComponent.newTransaction(msg, amount, receiverId)
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

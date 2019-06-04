package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.MiningComponent
import com.supbank.blockchain.components.WalletComponent
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.pojo.request.CreateTransactionRequest
import com.supbank.blockchain.pojo.request.CreateWalletRequest
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * Handle creation / transaction
 */
@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/wallet")
class WalletController(private val walletComponent: WalletComponent,
                       private val miningComponent: MiningComponent) {

    @GetMapping("/create")
    fun create(@RequestParam("name") name: String) : Wallet? {
        // Create new wallet
        return walletComponent.create(name, null, null)
    }

    @PostMapping("/create", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPost(@RequestBody(required = false) request: CreateWalletRequest) : Wallet? {
        return walletComponent.create(request.name,
                if(request.mail.isPresent) request.mail.get() else null,
                if(request.token.isPresent) request.token.get() else null)
    }

    @GetMapping("/load")
    fun load(@RequestParam("identifier") identifier: String) : Wallet? {
        // Try to load a wallet
        return walletComponent.load(identifier)
    }

    @GetMapping("/status")
    fun status() : Wallet? {
        return walletComponent.wallet
    }

    @PostMapping("/publish")
    fun publishTransaction(@RequestBody request: CreateTransactionRequest) : Transaction? {
        return walletComponent.newTransaction(request.message, request.amount, request.receiver)
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

    @GetMapping("/decrypt")
    fun decrypt(@RequestParam("id") transactionId: Long) : String {
        return walletComponent.decryptTransactionMessage(transactionId)
    }

}

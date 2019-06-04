package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.MiningComponent
import com.supbank.blockchain.components.SearchComponent
import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.pojo.BlockPojo
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.TransactionRepository
import com.supbank.blockchain.repos.WalletRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.springframework.web.bind.annotation.*

/**
 * Handle blockcahin sync / fetch
 */
@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/blockchain")
class BlockchainController(private val miningComponent: MiningComponent,
                           private val blockchainRepository: BlockchainRepository,
                           private val transactionRepository: TransactionRepository,
                           private val walletRepository: WalletRepository,
                           private val searchComponent: SearchComponent) {

    @GetMapping("/miner/{action}")
    fun mining(@PathVariable action: String): Boolean {
        return when(action) {
            "start" -> miningComponent.startMining()
            "stop" -> miningComponent.stopMining()
            else -> false
        }
    }

    @PostMapping("/blocks")
    fun getBlocks(): Flowable<BlockPojo> {
        return Flowable.create({emitter ->
            blockchainRepository.findAll().forEach { emitter.onNext(BlockPojo.fromBlock(it)) }
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    @PostMapping("/block")
    fun getBlock(@RequestParam("id") id: Long): Block? {
        val blockOpt = blockchainRepository.findById(id)
        return if(blockOpt.isPresent)
            blockOpt.get()
        else
            null
    }

    @PostMapping("/transactions")
    fun getTransactions() : Flowable<Transaction> {
        return Flowable.create({emitter ->
            transactionRepository.findAll().forEach { emitter.onNext(it) }
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    @PostMapping("/pool")
    fun getTransactionsPool() : Flowable<Transaction> {
        return Flowable.create({emitter ->
            transactionRepository.findAll()
                    .filter { !it.mined }
                    .forEach { emitter.onNext(it) }
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    @PostMapping("/wallets")
    fun getWallets() : Flowable<Wallet> {
        return Flowable.create({emitter ->
            walletRepository.findAll()
                    .forEach { emitter.onNext(it) }
            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    @GetMapping("/search")
    fun search(@RequestParam("criteria") criteria: String) : Flowable<Transaction> {
        return searchComponent.search(criteria)
    }
}

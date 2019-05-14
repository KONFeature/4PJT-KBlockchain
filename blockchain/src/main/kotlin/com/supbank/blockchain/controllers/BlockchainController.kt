package com.supbank.blockchain.controllers

import com.supbank.blockchain.components.MiningComponent
import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.repos.TransactionRepository
import io.reactivex.Flowable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Handle blockcahin sync / fetch
 */
@RestController
@RequestMapping("/blockchain")
class BlockchainController(private val miningComponent: MiningComponent,
                           private val transactionRepository: TransactionRepository) {

    @GetMapping("/all")
    fun getAll(): Flowable<Block> {
        val transactions1 = ArrayList<Transaction>()
        transactions1.add(Transaction(1, 42, 13,  "test1"))
        transactions1.add(Transaction(2, 13, 42,  "test2"))

        val transactions2 = ArrayList<Transaction>()
        transactions2.add(Transaction(3, 42, 13, "test3"))
        transactions2.add(Transaction(4, 13, 42, "test4"))

        return Flowable.just(
                Block(
                        "1313",
                        "420",
                        transactions1,
                        13,
                        Date().time),
                Block(
                        "420",
                        "1313",
                        transactions2,
                        42,
                        Date().time))
    }

    @GetMapping("/miner/{action}")
    fun mining(@PathVariable action: String): Boolean {
        return when(action) {
            "start" -> miningComponent.startMining()
            "stop" -> miningComponent.stopMining()
            else -> false
        }
    }

    @GetMapping("/test")
    fun addTestValue() : String {
        val transactions = ArrayList<Transaction>()
        transactions.add(Transaction(1, 42, 13,  "test1"))
        transactions.add(Transaction(2, 13, 42,  "test2"))
        transactions.add(Transaction(3, 13, 42,  "test3"))
        transactions.add(Transaction(4, 13, 42,  "test4"))
        transactions.add(Transaction(5, 13, 42,  "test5"))
        transactionRepository.saveAll(transactions)
        return "test"
    }

    // TODO : Blockchain sync
    // TODO : Blockchain validation
}

package com.supbank.blockchain.controllers

import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import io.reactivex.Flowable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

/**
 * Handle blockcahin sync / fetch
 */
@RestController
@RequestMapping("/blockchain")
class BlockchainController {

    @GetMapping("/all")
    fun getAll(): Flowable<Block> {
        val transactions1 = ArrayList<Transaction>()
        transactions1.add(Transaction(13, 42, 13, "test1"))
        transactions1.add(Transaction(42, 13, 42, "test2"))

        val transactions2 = ArrayList<Transaction>()
        transactions2.add(Transaction(13, 42, 13, "test3"))
        transactions2.add(Transaction(42, 13, 42, "test4"))

        return Flowable.just(
                Block(
                        ByteArray(1313),
                        ByteArray(420),
                        ByteArray(1313),
                        transactions1,
                        13,
                        Date().time),
                Block(
                        ByteArray(420),
                        ByteArray(1313),
                        ByteArray(420),
                        transactions2,
                        42,
                        Date().time))
    }
}
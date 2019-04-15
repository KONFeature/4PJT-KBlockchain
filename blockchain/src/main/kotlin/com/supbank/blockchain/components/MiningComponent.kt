package com.supbank.blockchain.components

import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.TransactionRepository
import org.slf4j.Logger
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MiningComponent(private val transactionRepository: TransactionRepository,
                      private val blockchainRepository: BlockchainRepository,
                      private val log: Logger) {

    companion object {
        const val DIFFICULTY = 5
        const val TRANSACTION_IN_BLOCK = 4
        const val WAIT_FOR_TRANSACTION: Long = 1
    }

    private var running: Boolean = false

    // Launch miner
    fun startMining(): Boolean {
        if (running) return false
        running = true

        mine()
        return running
    }

    // Stop miner
    fun stopMining(): Boolean {
        running = false
        return running
    }

    /**
     * function used to mine a block
     */
    fun mine() {
        if(!running) return

        // Check if we have enough transaction to create a block
        if (transactionRepository.findAllByMinedFalse().count() < TRANSACTION_IN_BLOCK) {
            log.info("Not enough transaction to mine a block")
            TimeUnit.MINUTES.sleep(WAIT_FOR_TRANSACTION)
            mine()
            return
        }

        // Fetch transaction to mine
        val transactions = ArrayList<Transaction>()
        transactionRepository.findAllByMinedFalse().forEach { transaction ->
            if(transactions.size < TRANSACTION_IN_BLOCK) {
                transactions.add(transaction)
            } else {
                return@forEach
            }
        }

        // Find last block
        val prevBlockHash = blockchainRepository.findAll().lastOrNull()?.hash?:"0"
        log.info("Last block hash $prevBlockHash")

        // Create ur block
        var block = Block(prevHash = prevBlockHash, transactions = transactions)
        log.info("Start mining a block with transactions $transactions")
        while(!block.hash.startsWith("0".repeat(DIFFICULTY)) && running) {
           block = block.copy(nonce = block.nonce + 1)
        }
        log.info("Block mined $block")

        // Save block and send it to other node
        blockchainRepository.save(block)
        transactions.forEach {transaction ->
            transaction.mined = true
            transactionRepository.save(transaction)
        }

        // TODO : Reward miner and send block to other node via p2p

        // Call to a new block mining
        mine()
    }
}
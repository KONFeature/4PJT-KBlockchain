package com.supbank.blockchain.services

import com.supbank.blockchain.components.NetworkComponent
import com.supbank.blockchain.configs.Constant.Companion.DIFFICULTY
import com.supbank.blockchain.configs.Constant.Companion.MAX_TRANSACTIONS_PER_BLOCK
import com.supbank.blockchain.configs.Constant.Companion.REFRESH_RATE_EMPTY_TRANSACTION
import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.repos.BlockRepository
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicBoolean
import java.util.stream.Collectors


@Service
class MiningService @Autowired
constructor(private val transactionService: TransactionService,
            private val nodeService: NetworkComponent,
            private val blockService: BlockRepository,
            private val log: Logger) : Runnable {

    private val runMiner = AtomicBoolean(false)


    @Value("\${mining.address.hash.reward}")
    private val miningAddressReward: String? = null

    /**
     * Start the miner
     */
    fun startMiner() {
        if (runMiner.compareAndSet(false, true)) {
            val thread = Thread(this)
            thread.start()
        }
    }

    /**
     * Stop the miner after next iteration
     */
    fun stopMiner() {
        log.info("Stopping miner")
        runMiner.set(false)
    }

    /**
     * Loop for new blocks until someone signals to stop
     */
    override fun run() {
        while (runMiner.get()) {
            val block = mineBlock()
            if (block != null) {
                // Found block! Append and publish
                rewardAddress()
                log.info("Mined block with " + block!!.transactions.size + " transactions and nonce " + block!!.tries)
                //TODO blockService.append(block)
                //TODO nodeService.broadcastPut("block", block)
            }
        }
        log.info("Miner stopped")
    }

    /**
     * Function used to mine a block
     */
    private fun mineBlock(): Block? {
        var tries: Long = 0

        // get previous hash and transactions
        val previousBlockHash: ByteArray = if (blockService.findAll().last() != null) blockService.findAll().last().hash!! else null
        val transactions = transactionService.getTransactionPool()
                .stream().limit(MAX_TRANSACTIONS_PER_BLOCK).collect(Collectors.toList<Transaction>())

        // sleep if no more transactions left
        if (transactions.isEmpty()) {
            log.info("No transactions available, pausing")
            try {
                Thread.sleep(REFRESH_RATE_EMPTY_TRANSACTION)
            } catch (e: InterruptedException) {
                log.error("Thread interrupted", e)
            }

            return null
        }

        // try new block until difficulty is sufficient
        while (runMiner.get()) {
            val block = Block(previousBlockHash, transactions, tries)
            if (block.getLeadingZerosCount() >= DIFFICULTY) {
                return block
            }
            tries++
        }
        return null
    }

    /**
     * Send the mining reward to the properties address
     */
    private fun rewardAddress() {
        if (miningAddressReward == null || miningAddressReward.isEmpty()) {
            log.info("No mining address reward specified")
            return
        }
    }

}

package com.supbank.blockchain.components

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.TransactionRepository
import com.supbank.blockchain.utils.P2pException
import com.supbank.blockchain.utils.p2p.BlockMinedPayload
import io.reactivex.Completable
import io.rsocket.kotlin.Payload
import org.slf4j.Logger
import org.springframework.core.task.TaskExecutor
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MiningComponent(private val transactionRepository: TransactionRepository,
                      private val blockchainRepository: BlockchainRepository,
                      private val socketSender: SocketSenderComponent,
                      private val walletComponent: WalletComponent,
                      private val taskExecutor: TaskExecutor,
                      private val log: Logger) {

    companion object {
        const val DIFFICULTY = 5
        const val TRANSACTION_IN_BLOCK = 4
        const val WAIT_FOR_TRANSACTION: Long = 1
    }

    private var running: Boolean = false

    // Launch miner
    fun startMining(): Boolean {
        if (running || walletComponent.wallet == null) return false
        running = true

        taskExecutor.execute {
            mine()
        }
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

        // Send the mined block to other node
        socketSender.broadcastFf(BlockMinedPayload(block).get())

        // Reward miner
        walletComponent.rewardMiningWallet()

        // Call to a new block mining
        mine()
    }

    /**
     * Received a mined block from p2p network
     */
    fun receivedBlockMined(payload: Payload) : Completable {
        return try {
            val rawBlock: Block? = Gson().fromJson(payload.dataUtf8, Block::class.java)
            rawBlock?.let {block ->
                val localTransaction = ArrayList<Transaction>()

                // Get only the id of the transaction id
                val transactionsId = block.transactions.map { transaction -> transaction.id }

                // foreach id, find the corresponding local transaction
                transactionsId.forEach {transactionIdReceived ->
                    val transactionOpt = transactionRepository.findById(transactionIdReceived)
                    if(transactionOpt.isPresent) {
                        localTransaction.add(transactionOpt.get())
                        // Update local transaction
                        transactionOpt.get().mined = true
                        transactionRepository.save(transactionOpt.get())
                    } else {
                        log.warn("Unable to refresh the transaction present in the block received")
                    }
                }

                // Map the local transactions to the block
                if(block.transactions is ArrayList<Transaction>) {
                    block.transactions.clear()
                    block.transactions.addAll(localTransaction)
                } else {
                    log.warn("Unable to map the transactions to the received block")
                }

                // save the block
                log.info("Saving a new mined block")
                blockchainRepository.save(block)
            }?:kotlin.run {
                log.warn("Unable to parse the received block")
            }
            Completable.complete()
        } catch(e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a block object {}, {}", payload.dataUtf8, e.message)
            Completable.error(P2pException.exception(payload, e.message))
        }
    }
}

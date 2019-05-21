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
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MiningComponent(private val transactionRepository: TransactionRepository,
                      private val blockchainRepository: BlockchainRepository,
                      private val socketSender: SocketSenderComponent,
                      private val walletComponent: WalletComponent,
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

        // Send the mined block to other node
        socketSender.broadcastFf(BlockMinedPayload(block).get())

        // TODO : Reward miner
//        walletComponent.wallet?.let {
//            it.amount = 5
//        }

        // Call to a new block mining
        mine()
    }

    /**
     * Received a mined block from p2p network
     */
    fun receivedBlockMined(payload: Payload) : Completable {
        try {
            val block: Block? = Gson().fromJson(payload.dataUtf8, Block::class.java)
            // TODO : Check last hash corresponding to last block in blockchain, else abort add and resync
        } catch(e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a block object {}, {}", payload.dataUtf8, e.message)
        }
        return Completable.error(P2pException.unknownOperationException(payload))
    }
}

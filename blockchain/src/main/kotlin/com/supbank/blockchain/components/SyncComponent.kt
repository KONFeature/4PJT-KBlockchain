package com.supbank.blockchain.components

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.pojo.BlockchainStatusPojo
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.TransactionRepository
import com.supbank.blockchain.repos.WalletRepository
import com.supbank.blockchain.utils.GsonUtils
import com.supbank.blockchain.utils.P2pException
import com.supbank.blockchain.utils.SyncField
import com.supbank.blockchain.utils.p2p.sync.SyncPayload
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.rsocket.kotlin.Payload
import org.slf4j.Logger
import org.springframework.stereotype.Component

@Component
class SyncComponent(private val walletRepository: WalletRepository,
                    private val transactionRepository: TransactionRepository,
                    private val blockchainRepository: BlockchainRepository,
                    private val log: Logger) {

    // Block that we havn't added to the blockchain yet
    private val blockPool = ArrayList<Block>()

    /**
     * Function used to get the current status of the blockchain
     */
    fun getStatus(): BlockchainStatusPojo {
        return BlockchainStatusPojo(walletRepository.getTopByOrderByIdDesc()?.id ?: 0L,
                transactionRepository.getTopByOrderByIdDesc()?.id ?: 0L,
                blockchainRepository.getTopByOrderByIdDesc()?.id ?: 0L,
                blockchainRepository.getTopByOrderByIdDesc()?.hash ?: "")
    }

    /**
     * Function called when we received the status of the blockchain from other nodes on the p2p network
     * Return all the data missing from the requester node
     */
    fun receivedStatus(payload: Payload): Flowable<Payload> {
        return try {
            log.info("Received a blockchain status, check if we need to send data or not")
            val syncStatus = Gson().fromJson(payload.dataUtf8, BlockchainStatusPojo::class.java)
            val fieldsToSync = LinkedHashMap<SyncField, Long>()

            // Check wallet status
            val walletId = walletRepository.getTopByOrderByIdDesc()?.id ?: 0L
            if (walletId > syncStatus.walletLastId) {
                log.info("Wallet behind : received id ${syncStatus.walletLastId} vs $walletId")
                fieldsToSync[SyncField.WALLET] = syncStatus.walletLastId
            } else {
                log.error("$walletId vs ${syncStatus.walletLastId}")
            }

            // Check transaction status
            val transactionId = transactionRepository.getTopByOrderByIdDesc()?.id ?: 0L
            if (transactionId > syncStatus.transactionLastId) {
                log.info("Transaction behind : received id ${syncStatus.transactionLastId} vs $transactionId")
                fieldsToSync[SyncField.TRANSACTION] = syncStatus.transactionLastId
            }

            // Check blockchain status
            val blockId = blockchainRepository.getTopByOrderByIdDesc()?.id ?: 0L
            if (blockId > syncStatus.blockLastId) {
                log.info("Blockchain behind : received id ${syncStatus.blockLastId} vs $blockId")
                fieldsToSync[SyncField.BLOCKCHAIN] = syncStatus.blockLastId
            }

            if (fieldsToSync.isNotEmpty()) {
                // Generate the flowable to sync
                log.info("The node need to synchronize some field : {}", fieldsToSync)
                generateSyncPackage(fieldsToSync)
            } else {
                // Already up to date
                log.info("No sync needed, already up to date")
                Flowable.empty<Payload>()
            }
        } catch (e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a status object {}, {}", payload.dataUtf8, e.message)
            Flowable.error(P2pException.unableToParseException(payload, e.message))
        }
    }

    /**
     * Called when received a sync status
     */
    fun generateSyncPackage(syncMap: Map<SyncField, Long>): Flowable<Payload> {
        // Create the flowable
        return Flowable.create({ emitter ->
            // check wich data to send
            syncMap.forEach { (syncField: SyncField, id: Long) ->
                when (syncField) {
                    SyncField.WALLET -> {
                        createSyncRespMap(syncField, walletRepository.findAll().filter { it.id > id })
                                .forEach {
                                    emitter.onNext(it.get())
                                }
                    }
                    SyncField.TRANSACTION -> {
                        createSyncRespMap(syncField, transactionRepository.findAll().filter { it.id > id })
                                .forEach {
                                    emitter.onNext(it.get())
                                }
                    }
                    SyncField.BLOCKCHAIN -> {
                        createSyncRespMap(syncField, blockchainRepository.findAll().filter { it.id > id })
                                .forEach {
                                    emitter.onNext(it.get())
                                }
                    }
                }
            }

            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    /**
     * Create the list of response sync payload to send to the client
     */
    private fun createSyncRespMap(syncField: SyncField, data: List<Any>): List<SyncPayload> {
        val toSend = ArrayList<SyncPayload>()
        val dataSubList = ArrayList<Any>()
        data.forEach {
            // Create the data package to send when we reach the good size
            if (dataSubList.size >= SyncPayload.arraySize) {
                val map = HashMap<SyncField, List<Any>>()
                map[syncField] = dataSubList
                toSend.add(SyncPayload(map))
                dataSubList.clear()
            }

            dataSubList.add(it)
        }

        if (dataSubList.isNotEmpty()) {
            val map = HashMap<SyncField, List<Any>>()
            map[syncField] = dataSubList
            toSend.add(SyncPayload(map))
        }

        return toSend
    }

    /**
     * Called when received a sync response (save the element received)
     */
    fun receivedSyncResponse(payload: Payload) {
        try {
            val syncResponse: Map<SyncField, JsonArray> = Gson().fromJson(payload.dataUtf8, SyncPayload.type)
            log.debug("Sync received data {}", syncResponse)
            syncResponse.forEach { response ->
                when (response.key) {
                    SyncField.WALLET -> {
                        log.info("Syncing some wallets, please wait ...")
                        response.value.forEach { respVal ->
                            when (respVal) {
                                is JsonElement -> walletRepository.save(GsonUtils.getWalletCustom().fromJson(respVal, Wallet::class.java))
                                else -> log.error("Unable to parse the received wallet $respVal of type ${respVal::class.java}")
                            }
                        }
                    }
                    SyncField.TRANSACTION -> {
                        log.info("Syncing some transactions, please wait ...")
                        response.value.forEach { respVal ->
                            when (respVal) {
                                is JsonElement -> transactionRepository.save(Gson().fromJson(respVal, Transaction::class.java))
                                else -> log.error("Unable to parse the received transaction $respVal of type ${respVal::class.java}")
                            }
                        }
                    }
                    SyncField.BLOCKCHAIN -> {
                        log.info("Syncing some blocks, please wait ...")
                        response.value.forEach { respVal ->
                            when (respVal) {
                                is JsonElement -> receivedBlock(Gson().fromJson(respVal, Block::class.java))
                                else -> log.error("Unable to parse the received block $respVal of type ${respVal::class.java}")
                            }
                        }
                    }
                }
            }
        } catch (e: JsonSyntaxException) {
            log.error("Error occured during sync, unable to parse response {}", e.message)
        }
    }

    /**
     * Function called when we receive a new block, this function check the integrity of the blockchain
     */
    fun receivedBlock(block: Block) {
        val lastBlock = blockchainRepository.getTopByOrderByIdDesc()

        // Check that the block we received correspond to the last block
        if(block.prevHash == lastBlock?.hash?:"0") {
            blockchainRepository.save(rebuildBlockTransaction(block))

            // Explore the pool and add all the block
            var blockInPool = blockPool.firstOrNull { it.prevHash == block.hash }
            while(blockInPool != null) {
                log.info("Founded a block in pool that match the current block hash, adding it to blockchain")
                blockchainRepository.save(blockInPool)
                blockPool.remove(blockInPool)
                val hash = blockInPool.hash
                blockInPool = blockPool.firstOrNull { it.prevHash == hash }
            }
        } else {
            log.info("Received block previous hash doesn't correspond current last block hash, adding it into pool")
            blockPool.add(rebuildBlockTransaction(block))
        }
    }

    /**
     * Rebuild the transactions map of a block
     */
    private fun rebuildBlockTransaction(block: Block) : Block {
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

        return block
    }
}

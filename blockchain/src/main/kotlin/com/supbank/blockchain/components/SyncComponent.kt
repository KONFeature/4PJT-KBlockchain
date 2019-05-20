package com.supbank.blockchain.components

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.pojo.BlockchainStatusPojo
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.TransactionRepository
import com.supbank.blockchain.repos.WalletRepository
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
            val fieldsToSync = HashMap<SyncField, Long>()

            // Check wallet status
            val walletId = walletRepository.getTopByOrderByIdDesc()?.id ?: 0L
            if (walletId > syncStatus.walletLastId) {
                fieldsToSync[SyncField.WALLET] = syncStatus.walletLastId
            }

            // Check transaction pool status
            val transactionId = transactionRepository.getTopByOrderByIdDesc()?.id ?: 0L
            if (transactionId > syncStatus.transactionLastId) {
                fieldsToSync[SyncField.TRANSACTION] = syncStatus.transactionLastId
            }

            // Check blockchain status
            val blockId = blockchainRepository.getTopByOrderByIdDesc()?.id ?: 0L
            if (blockId > syncStatus.blockLastId) {
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
            Flowable.error(P2pException.unableToPaseException(payload, e.message))
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
                    SyncField.BLOCKCHAIN -> {
                        createSyncRespMap(syncField, blockchainRepository.findAll().filter { it.id > id })
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
                    SyncField.WALLET -> {
                        createSyncRespMap(syncField, walletRepository.findAll().filter { it.id > id })
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
                map.put(syncField, dataSubList)
                toSend.add(SyncPayload(map))
                dataSubList.clear()
            }

            dataSubList.add(it)
        }

        if (dataSubList.isNotEmpty()) {
            val map = HashMap<SyncField, List<Any>>()
            map.put(syncField, dataSubList)
            toSend.add(SyncPayload(map))
        }

        return toSend
    }

    /**
     * Called when received a sync response (save the element received)
     */
    fun receivedSyncResponse(payload: Payload) {
        try {
            val syncResponse: Map<SyncField, List<Any>> = Gson().fromJson(payload.dataUtf8, SyncPayload.type)
            log.info("Sync in progress please wait ...")
            log.debug("Sync received data {}", syncResponse)
            syncResponse.forEach { response ->
                when (response.key) {
                    SyncField.BLOCKCHAIN -> {
                        // TODO : Check blockchain integrity here ?
                        response.value.forEach { respVal ->
                            if (respVal is Block) {
                                blockchainRepository.save(respVal)
                            }
                        }
                    }
                    SyncField.TRANSACTION -> {
                        // TODO : Upate mined transaction here ?
                        response.value.forEach { respVal ->
                            if (respVal is Transaction) {
                                transactionRepository.save(respVal)
                            }
                        }
                    }
                    SyncField.WALLET -> {
                        response.value.forEach { respVal ->
                            if (respVal is Wallet) {
                                walletRepository.save(respVal)
                            }
                        }
                    }
                }
            }
        } catch (e: JsonSyntaxException) {
            log.error("Error occured during sync, unable to parse response {}", e.message)
        }
    }
}

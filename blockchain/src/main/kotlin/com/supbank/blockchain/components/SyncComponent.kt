package com.supbank.blockchain.components

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.pojo.BlockchainStatusPojo
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.TransactionRepository
import com.supbank.blockchain.repos.WalletRepository
import com.supbank.blockchain.utils.P2pException
import com.supbank.blockchain.utils.SyncField
import com.supbank.blockchain.utils.p2p.P2pPayload
import io.reactivex.Completable
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
    fun getStatus() : BlockchainStatusPojo {
        val lastHash = blockchainRepository.findFirstOrderByTimestamp()?.hash
        return BlockchainStatusPojo(walletRepository.count(),transactionRepository.count(), blockchainRepository.count(), lastHash)
    }

    /**
     * Function called when we received the status of the blockchain from other nodes on the p2p network
     */
    fun receivedStatus(payload: Payload) : Completable {
        return try {
            val syncStatus = Gson().fromJson(payload.dataUtf8, BlockchainStatusPojo::class.java)
            val fieldsToSync = ArrayList<SyncField>()

            // Check wallet status
            if(syncStatus.walletCount > walletRepository.count()) {
                fieldsToSync.add(SyncField.WALLET)
            }

            // Check transaction pool status
            if(syncStatus.transactionCount > transactionRepository.count()) {
                fieldsToSync.add(SyncField.TRANSACTION)
            }

            // Check blockchain status
            if(syncStatus.blockCount > blockchainRepository.count() || syncStatus.lastBlockHash != blockchainRepository.findFirstOrderByTimestamp()?.hash) {
                fieldsToSync.add(SyncField.BLOCKCHAIN)
            }

            if(!fieldsToSync.isEmpty()) {
                // TODO : Ask a node for the field to sync
            }

            Completable.complete()
        } catch(e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a sync object {}, {}", payload.dataUtf8, e.message)
            Completable.error(P2pException.unableToPaseException(payload, e.message))
        }
    }

    /**
     * Called when received a sync status
     */
    fun receivedSyncRequest(payload: Payload) : Completable {
        // TODO : DO IT ! JUST DO IT !
        return Completable.complete()
    }
}

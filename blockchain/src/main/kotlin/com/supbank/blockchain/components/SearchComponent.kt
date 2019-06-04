package com.supbank.blockchain.components

import com.supbank.blockchain.models.Block
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.repos.BlockchainRepository
import com.supbank.blockchain.repos.TransactionRepository
import com.supbank.blockchain.repos.WalletRepository
import com.supbank.blockchain.utils.CryptoUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.slf4j.Logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class SearchComponent(private val transactionRepository: TransactionRepository,
                      private val blockchainRepository: BlockchainRepository,
                      private val walletRepository: WalletRepository,
                      private val log: Logger) {

    /**
     * Function used to search for wallet / block / transaction for the blockchain
     */
    fun search(criteria: String) : Flowable<Transaction> {
        log.info("performing search for the criteria '$criteria'")

        val criteriaLong = criteria.toLongOrNull()

        val blocksTransaction = ArrayList<Block>()
        val walletsTransaction = ArrayList<Wallet>()

        // Find all the component to search transaction in
        criteriaLong?.let { possibleId ->
            // If ur criteria is long, we search for wallet or block
            blockchainRepository.findByIdOrNull(possibleId)?.let { blocksTransaction.add(it) }
            walletRepository.findByIdOrNull(possibleId)?.let { walletsTransaction.add(it) }
        }?: run {
            // Search for pubkey on wallet
            try {
                walletRepository.findByPubKeyLike(CryptoUtil.getPubKeyFromEncodedString(criteria))?.let { walletsTransaction.add(it) }
            } catch (e: Exception) {
                log.debug("Unable to parse the criteria to public key")
            }

            // Search for name / mail on wallet
            walletRepository.findAllByNameLikeOrMailLike(criteria, criteria).let { walletsTransaction.addAll(it) }

            // Search for hash in blockchain
            blockchainRepository.findAllByHashLike(criteria).let { blocksTransaction.addAll(it) }
        }

        return Flowable.create({emitter ->
            // Retreive the transaction and send them
            blocksTransaction.forEach {block ->
                block.transactions.forEach { emitter.onNext(it) }
            }

            // Retreive all the transactions associated with the wallet founded
            walletsTransaction.forEach { wallet ->
                transactionRepository.findAllByReceiverId(wallet.id).forEach { emitter.onNext(it) }
                transactionRepository.findAllBySenderId(wallet.id).forEach { emitter.onNext(it) }
            }

            emitter.onComplete()
        }, BackpressureStrategy.BUFFER)
    }
}

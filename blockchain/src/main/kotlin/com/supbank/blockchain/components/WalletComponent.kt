package com.supbank.blockchain.components

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.repos.TransactionRepository
import com.supbank.blockchain.repos.WalletRepository
import com.supbank.blockchain.utils.CryptoUtil
import com.supbank.blockchain.utils.GsonUtils
import com.supbank.blockchain.utils.P2pException
import com.supbank.blockchain.utils.p2p.wallet.AddWalletPayload
import com.supbank.blockchain.utils.p2p.wallet.PublishTransactionPayload
import com.supbank.blockchain.utils.p2p.wallet.UpdateWalletPayload
import io.reactivex.Completable
import io.rsocket.kotlin.Payload
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class WalletComponent (private val socketSender: SocketSenderComponent,
                       private val walletRepository: WalletRepository,
                       private val transactionRepository: TransactionRepository,
                       private val keyStore: KeyStoreComponent,
                       private val log: Logger) {

    companion object {
        const val MINIG_REWARD = 10
    }

    @Value("\${wallet.key.path}")
    lateinit var keyPath: String

    var wallet: Wallet? = null

    /**
     * Function used to create a wallet
     */
    fun create(name: String, mail: String?, token: String?) : Wallet? {
        // Destroy current wallet
        wallet = null

        // If we have a mail, check that we don't use it for another wallet
        if(mail != null && walletRepository.findAllByMail(mail).isNotEmpty()) {
            log.error("Another wallet already use this address mail, aborting")
            return null
        }

        // Generate a keypair
        val keypair = keyStore.createKeyPair(name, mail)
        wallet = Wallet(name, keypair.public, keypair.private)

        // Add the mail and the token if they exist
        wallet?.mail = mail
        wallet?.token = token

        // Send the wallet to the other node
        wallet?.let {
            walletRepository.save(it)
            socketSender.broadcastFf(AddWalletPayload(it).get())
        }

        log.info("Successfully created a new wallet $wallet")
        return wallet
    }

    /**
     * Function used to load a wallet
     */
    fun load(identifier: String) : Wallet? {
        // Destroy current wallet
        wallet = null

        // Try to retreive the keypair from the keystore
        keyStore.retrieveKeyPair(identifier)?.let {keyPair ->
            log.info("Successfully retreived the keypair with the id $identifier")

            // Find the wallet corresponding to the public key in db
            wallet = walletRepository.getByPubKeyEquals(keyPair.public)
            wallet?.let {
                // Load private key
                it.privKey = keyPair.private
            }?:run {
                // Error, no wallet found
                log.error("Unable to find the wallet corresponding to ur public key, aborting")
                wallet = null
            }
        }?:run {
            log.error("Unable to load keypair for ur wallet, aborting")
        }

        log.info("Retrieved wallet $wallet")
        return wallet
    }

    /**
     * Function used to create a new transaction
     */
    fun newTransaction(msg: String, amount: Int, receiverId : Long) : Transaction? {
        // Check sending wallet
        if(wallet == null) {
            log.warn("No wallet loaded, unable to create a transaction aborting")
            return null
        }

        // Check receiver wallet
        val receiverOpt = walletRepository.findById(receiverId)
        if(!receiverOpt.isPresent) {
            log.warn("No receiver founded with the id {}, aborting the creation of the transaction", receiverId)
            return null
        }
        val receiver = receiverOpt.get()

        // Check that the receiver is not the sender too
        if(receiverId == wallet?.id) {
            log.warn("Sender cannnot be receiver too, aborting")
            return null
        }

        // Check balance
        if(wallet!!.amount < amount) {
            log.warn("Not enough coins on ur wallet to create the transaction")
            return null
        }

        // Create the transaction
        val transaction = Transaction.create(receiver, wallet!!, msg, amount)

        // Save the transaction in db
        transactionRepository.save(transaction)

        // Send the transaction to other node
        socketSender.broadcastFf(PublishTransactionPayload(transaction).get())

        // Refresh wallet amount
        wallet?.let {
            it.amount -= amount
            walletRepository.save(it)
        }
        receiver.amount += amount
        walletRepository.save(receiver)

        log.info("Newly created transaction : $transaction")
        return transaction
    }

    /**
     * Function used to add a wallet when received it from p2p network
     */
    fun receivedPayload(payload: Payload) : Completable {
        return try {
            val wallet = GsonUtils.getWalletCustom().fromJson(payload.dataUtf8, Wallet::class.java)
            if(!walletRepository.existsById(wallet.id)) {
                log.info("Adding a new wallet to the database")
            } else {
                log.info("Wallet known, refreshing it")
            }
            walletRepository.save(wallet)
            Completable.complete()
        } catch(e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a wallet object {}, {}", payload.dataUtf8, e.message)
            Completable.error(P2pException.unableToParseException(payload, e.message))
        }
    }

    /**
     * Function used to add a transaction received from the p2p network
     */
    fun receivedTransaction(payload: Payload) : Completable {
        return try {
            // Save the transaction
            val transaction = Gson().fromJson(payload.dataUtf8, Transaction::class.java)
            if(!transactionRepository.existsById(transaction.id)) {
                log.info("Received a new transaction $transaction")
                transactionRepository.save(transaction)

                // Update the wallet
                walletRepository.findByIdOrNull(transaction.senderId)?.let {
                    it.amount -= transaction.amount
                    walletRepository.save(it)
                }
                walletRepository.findByIdOrNull(transaction.receiverId)?.let {
                    it.amount += transaction.amount
                    walletRepository.save(it)
                }

                Completable.complete()
            } else {
                Completable.error(P2pException.transactionKnownException(payload))
            }
        } catch(e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a wallet object {}, {}", payload.dataUtf8, e.message)
            Completable.error(P2pException.unableToParseException(payload, e.message))
        }
    }

    /**
     * Function used to reward the miner who has mined a block
     */
    fun rewardMiningWallet() {
        wallet?.let {
            it.amount += MINIG_REWARD
            walletRepository.save(it)
            socketSender.broadcastFf(UpdateWalletPayload(it).get())
        }
    }

    /**
     * Function used to fetch all the transactions associated with a wallet
     */
    fun getWalletTransactions() : List<Transaction> {
        return wallet?.let {
            val res = ArrayList<Transaction>()
            res.addAll(transactionRepository.findAllBySenderId(it.id))
            res.addAll(transactionRepository.findAllByReceiverId(it.id))
            res
        }?:run {
            log.warn("No wallet loaded, returning an empty transaction list")
            ArrayList<Transaction>()
        }
    }

    /**
     * Function used to decrypt a transaction message
     */
    fun decryptTransactionMessage(transactionId: Long) : String {
        log.info("Decrypting the transaction $transactionId message")
        val transaction = transactionRepository.findByIdOrNull(transactionId)
        if(transaction != null && wallet != null && transaction.receiverId == wallet!!.id) {
            return CryptoUtil.decrypt(transaction.message, wallet!!.privKey)
        }
        return "Le wallet charger ne correspond a la transaction demande"
    }
}

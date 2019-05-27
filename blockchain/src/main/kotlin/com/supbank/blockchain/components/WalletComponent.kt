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
import org.springframework.stereotype.Component

@Component
class WalletComponent (private val socketSender: SocketSenderComponent,
                       private val walletRepository: WalletRepository,
                       private val transactionRepository: TransactionRepository,
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
    public fun create(name: String, mail: String?, token: String?) : Wallet? {
        // Destroy current wallet
        wallet = null

        // Generate a keypair
        CryptoUtil.createKeyPair(keyPath)?.let {keyPair ->
            // Create the wallet from the keypair
            log.info("Successfully created a keypair at the location {}", keyPath)
            wallet = Wallet(name, keyPair.public, keyPair.private)
            mail?.let { wallet?.mail = it }
            token?.let { wallet?.token = it }

            // Send the wallet to the other node
            wallet?.let {
                walletRepository.save(it)
                socketSender.broadcastFf(AddWalletPayload(it).get())
            }
        }?:run {
            log.error("Unable to generate keypair for the new wallet, aborting")
        }

        return wallet
    }

    /**
     * Function used to load a wallet
     */
    public fun load() : Wallet? {
        // Destroy current wallet
        wallet = null
        // store it static

        // Try to find local keypair
        CryptoUtil.loadKeyPair(keyPath)?.let { keyPair ->
            log.info("Successfully loaded the keypair from {}", keyPath)

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

        return wallet
    }

    /**
     * Function used to create a new transaction
     */
    fun newTransaction(msg: String, amount: Int, receiverId : Long) : Boolean {
        // Check sending wallet
        if(wallet == null) {
            log.warn("No wallet loaded, unable to create a transaction aborting")
            return false
        }

        // Check receiver wallet
        val receiverOpt = walletRepository.findById(receiverId)
        if(!receiverOpt.isPresent) {
            log.warn("No receiver founded with the id {}, aborting the creation of thetransaction", receiverId)
            return false
        }
        val receiver = receiverOpt.get()

        // Check balance
        if(wallet!!.amount < amount) {
            log.warn("Not enough coins on ur wallet to create the transaction")
            return false
        }

        // Create the transaction
        val transaction = Transaction.create(receiver, wallet!!, msg, amount)

        // Save the transaction in db
        transactionRepository.save(transaction)

        // Send the transaction to other node
        socketSender.broadcastFf(PublishTransactionPayload(transaction).get())

        log.info("Newly created transaction : $transaction")
        return true
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
            val transaction = Gson().fromJson(payload.dataUtf8, Transaction::class.java)
            if(!transactionRepository.existsById(transaction.id)) {
                log.info("Received a new transaction $transaction")
                transactionRepository.save(transaction)
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
}

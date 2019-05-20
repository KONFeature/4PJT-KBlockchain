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

    @Value("\${wallet.key.path}")
    lateinit var keyPath: String

    var wallet: Wallet? = null

    /**
     * Function used to create a wallet
     */
    public fun create(name: String) : Wallet? {
        // Destroy current wallet
        wallet = null

        // Generate a keypair
        CryptoUtil.createKeyPair(keyPath)?.let {keyPair ->
            // Create the wallet from the keypair
            log.info("Successfully created a keypair at the location {}", keyPath)
            wallet = Wallet(name, keyPair.public, keyPair.private)

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
    public fun newTransaction(msg: String, amount: Int, receiverId : Long) : Boolean {
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
    public fun receivedPayload(payload: Payload) : Completable {
        return try {
            log.info("Adding a new wallet")
            val wallet = GsonUtils.getWalletCustom().fromJson(payload.dataUtf8, Wallet::class.java)
            if(!walletRepository.existsById(wallet.id)) {
                walletRepository.save(wallet)
                Completable.complete()
            } else {
                Completable.error(P2pException.walletKnownException(payload))
            }
        } catch(e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a wallet object {}, {}", payload.dataUtf8, e.message)
            Completable.error(P2pException.unableToPaseException(payload, e.message))
        }
    }

    /**
     * Function used to add a transaction received from the p2p network
     */
    public fun receivedTransaction(payload: Payload) : Completable {
        return try {
            val transaction = Gson().fromJson(payload.dataUtf8, Transaction::class.java)
            if(!transactionRepository.existsById(transaction.id)) {
                transactionRepository.save(transaction)
                Completable.complete()
            } else {
                Completable.error(P2pException.transactionKnownException(payload))
            }
        } catch(e: JsonSyntaxException) {
            log.error("Unable to parse to payload to a wallet object {}, {}", payload.dataUtf8, e.message)
            Completable.error(P2pException.unableToPaseException(payload, e.message))
        }
    }

}

package com.supbank.blockchain.components

import com.supbank.blockchain.models.Wallet
import com.supbank.blockchain.repos.WalletRepository
import com.supbank.blockchain.utils.CryptoUtil
import com.supbank.blockchain.utils.p2p.AddWalletPayload
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value

class WalletComponent (private val socketSender: SocketSenderComponent,
                       private val walletRepository: WalletRepository,
                       private val log: Logger) {

    @Value("\${wallet.public.key.path}")
    lateinit var publicKeyPath: String

    @Value("\${wallet.private.key.path}")
    lateinit var privateKeyPath: String

    var wallet: Wallet? = null

    /**
     * Function used to create a wallet
     */
    public fun create(name: String) : Wallet? {
        // Destroy current wallet
        wallet = null

        // Generate a keypair
        CryptoUtil.createKeyPair(publicKeyPath, privateKeyPath)?.let {keyPair ->
            // Create the wallet from the keypair
            log.info("Successfully created a keypair at the location {} and {}", publicKeyPath, privateKeyPath)
            wallet = Wallet(name, keyPair.public, keyPair.private)

            // Send the wallet to the other node
            wallet?.let { socketSender.broadcastFf(AddWalletPayload(it).get()) }
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
        CryptoUtil.loadKeyPair(publicKeyPath, privateKeyPath)?.let { keyPair ->
            log.info("Successfully loaded the keypair from {} and {}", publicKeyPath, privateKeyPath)

            // Find the wallet corresponding to the public key in db
            wallet = walletRepository.getByPubKey(keyPair.public)
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
    public fun newTransaction() : Boolean {
        // TODO : Just DO IT !
        return false
    }

}

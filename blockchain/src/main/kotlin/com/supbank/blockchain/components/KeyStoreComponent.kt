package com.supbank.blockchain.components

import com.supbank.blockchain.utils.CryptoUtil
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.KeyPair
import java.security.KeyStore
import javax.annotation.PostConstruct

@Component
class KeyStoreComponent(private val log: Logger) {

    lateinit var keyStore: KeyStore

    @Value("\${wallet.key.path}")
    lateinit var keystoreFolder: String

    @Value("\${keystore.name}")
    lateinit var keystoreFilename: String

    @Value("\${keystore.pass}")
    lateinit var keystorePassword: String

    /**
     * Function used to load (or create) the keystore of the blockchain app
     */
    @PostConstruct
    fun load() {
        // Load default keystore instance
        val keystore = try {
            KeyStore.getInstance(KeyStore.getDefaultType())
        } catch (e: Exception) {
            log.error("Exception occured during the blockchain keystore loading ${e.message}, aborting")
            throw e
        }

        // Check if the keystore already exist or not
        val ksFile = File(keystoreFolder, keystoreFilename)
        if (!ksFile.exists()) {
            // Create the folder if necessary
            if (!ksFile.parentFile.exists()) ksFile.parentFile.mkdirs()

            // Create the keystore
            try {
                val fos = FileOutputStream(ksFile)
                keystore.load(null, keystorePassword.toCharArray())
                keystore.store(fos, keystorePassword.toCharArray())
                fos.close()
            } catch (e: Exception) {
                log.error("Exception occured during the blockchain keystore creation ${e.message}, aborting")
                throw e
            }
        } else {
            // Load the keystore
            try {
                val fis = FileInputStream(ksFile)
                keystore.load(fis, keystorePassword.toCharArray())
                fis.close()
            } catch (e: Exception) {
                log.error("Exception occured during the blockchain keystore creation ${e.message}, aborting")
                throw e
            }
        }

        log.info("Successfully init the keystore")
        this.keyStore = keystore
    }

    /**
     * Function used to save the key store
     */
    private fun save() {
        val ksFile = File(keystoreFolder, keystoreFilename)

        // Create the keystore
        try {
            val fos = FileOutputStream(ksFile)
            this.keyStore.store(fos, keystorePassword.toCharArray())
            fos.close()
        } catch (e: Exception) {
            log.error("Exception occured during the blockchain keystore creation ${e.message}, aborting")
            throw e
        }
    }

    /**
     * Function used to create a keypair for a wallet
     */
    fun createKeyPair(name: String, mail: String?): KeyPair {
        // Create the keypair
        val keypairIdentifier = mail ?: name
        val certGen = CryptoUtil.createKeyPair()

        // Get the certificate from the public key
        val certificate = certGen.getSelfCertificate(CryptoUtil.CERT_NAME, CryptoUtil.CERT_VALIDITY)

        // Store it in the keystore
        this.keyStore.setKeyEntry(keypairIdentifier, certGen.privateKey, keystorePassword.toCharArray(), arrayOf(certificate))

        // Update the keystore
        save()

        return KeyPair(certGen.publicKeyAnyway, certGen.privateKey)
    }

    /**
     * Function used to retreive a keypair from the keystores
     */
    fun retrieveKeyPair(identifier: String): KeyPair? {
        // Check that ur keystore contains the identifier
        if (!this.keyStore.containsAlias(identifier)) {
            log.error("Unable to find the keypair for the identifer $identifier")
            return null
        }

        // Find the priv key entry from the keystore
        val rawPrivKey = this.keyStore.getEntry(identifier, KeyStore.PasswordProtection(keystorePassword.toCharArray()))
        if (rawPrivKey !is KeyStore.PrivateKeyEntry) {
            log.error("The private key founded for the alias $identifier isn't a private key")
            return null
        }

        // Assert the public key correspond to the private key with a challenge
        val pubCert = rawPrivKey.certificate
        if (!CryptoUtil.checkKeyPair(rawPrivKey.privateKey, pubCert)) {
            log.error("The key founded for the alias $identifier doesnt' match, aborting")
            return null
        }

        log.info("Successfully retrieve the keypair for the identifier $identifier")
        return KeyPair(pubCert.publicKey, rawPrivKey.privateKey)
    }
}

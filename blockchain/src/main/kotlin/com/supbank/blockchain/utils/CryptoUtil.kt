package com.supbank.blockchain.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.security.*
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.Cipher

object CryptoUtil {

    // Const
    const val CRYPTO_ALGORITHM = "RSA"
    const val CRYPTO_BITS = 2048
    const val CRYPTO_TRANSFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

    // Factory and cipher
    private val factory = KeyFactory.getInstance(CRYPTO_ALGORITHM)
    private val generator = KeyPairGenerator.getInstance(CRYPTO_ALGORITHM)
    private val cipher = Cipher.getInstance(CRYPTO_TRANSFORM)

    // Logger
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Function used to create a key pair
     */
    fun createKeyPair(pubPath: String, privPath: String) : KeyPair? {
        log.debug("Generating keypair to path {} {}", pubPath, privPath)

        // Generate the keypair
        generator.initialize(CRYPTO_BITS)
        val keypair = generator.genKeyPair()

        // Store the keypair to the specified path
        try {
            val pubFile = File(pubPath)
            val privFile = File(privPath)

            // Check the folder tree
            if(!pubFile.parentFile.exists())
                pubFile.parentFile.mkdirs()
            if(!privFile.parentFile.exists())
                privFile.parentFile.mkdirs()

            log.debug("Public key file path {}, private key file path {}", pubFile.absolutePath, privFile.absolutePath)

            pubFile.writeBytes(keypair.public.encoded)
            privFile.writeBytes(keypair.private.encoded)

            log.debug("Successfully generated keypair")

            return keypair
        } catch(e: IOException) {
            // Error
            log.warn("Error during keypair generation {}", e.message)
            return null
        }
    }

    /**
     * Function used to lod local keypair
     */
    fun loadKeyPair(pubPath: String, privPath: String) : KeyPair? {
        log.debug("Loading keypair from path {} {}", pubPath, privPath)

        // Find the file and load them
        try {
            val pubFile = File(pubPath)
            val privFile = File(privPath)

            log.debug("Public key file path {}, private key file path {}", pubFile.absolutePath, privFile.absolutePath)

            val pubSpec = X509EncodedKeySpec(pubFile.readBytes())
            val pubKey = factory.generatePublic(pubSpec)
            val privSpec = PKCS8EncodedKeySpec(privFile.readBytes())
            val privKey = factory.generatePrivate(privSpec)

            log.debug("Successfully retreived keypair")

            return KeyPair(pubKey, privKey)
        } catch(e: IOException) {
            // Error
            log.warn("Error during keypair generation {}", e.message)
            return null
        }
    }

    /**
     * Get the public key corresponding to an encoded string
     */
    fun getPubKeyFromEncodedString(pubString: String) : PublicKey {
        val pubSpec = X509EncodedKeySpec(Base64.getDecoder().decode(pubString))
        return factory.generatePublic(pubSpec)
    }

    /**
     * Function used to encrypt a String using a public key
     */
    fun encrypt(msg: String, key: PublicKey) : String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return Base64.getEncoder().encodeToString(cipher.doFinal(msg.toByteArray(StandardCharsets.UTF_8)))
    }

    /**
     * Function used to decrypt a String using a private key
     */
    fun decrypt(msg: String, key: PrivateKey) : String {
        cipher.init(Cipher.DECRYPT_MODE, key)
        return String(cipher.doFinal(Base64.getDecoder().decode(msg)), StandardCharsets.UTF_8)
    }


}

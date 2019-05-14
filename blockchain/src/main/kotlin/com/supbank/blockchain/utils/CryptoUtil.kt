package com.supbank.blockchain.utils

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
    const val CRYPTO_BITS = 4096
    const val CRYPTO_TRANSFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

    // Factory and cipher
    private val factory = KeyFactory.getInstance(CRYPTO_ALGORITHM)
    private val generator = KeyPairGenerator.getInstance(CRYPTO_ALGORITHM)
    private val cipher = Cipher.getInstance(CRYPTO_TRANSFORM)

    /**
     * Function used to create a key pair
     */
    fun createKeyPair(pubPath: String, privPath: String) : KeyPair? {
        // Generate the keypair
        generator.initialize(CRYPTO_BITS)
        val keypair = generator.genKeyPair()

        // Store the keypair to the specified path
        try {
            val pubFile = File(pubPath)
            val privFile = File(privPath)

            pubFile.writeBytes(keypair.public.encoded)
            privFile.writeBytes(keypair.private.encoded)

            return keypair
        } catch(e: IOException) {
            // Error
            return null
        }
    }

    /**
     * Function used to lod local keypair
     */
    fun loadKeyPair(pubPath: String, privPath: String) : KeyPair? {
        // Find the file and load them
        try {
            val pubFile = File(pubPath)
            val privFile = File(privPath)

            val pubSpec = X509EncodedKeySpec(pubFile.readBytes())
            val pubKey = factory.generatePublic(pubSpec)
            val privSpec = PKCS8EncodedKeySpec(privFile.readBytes())
            val privKey = factory.generatePrivate(privSpec)

            return KeyPair(pubKey, privKey)
        } catch(e: IOException) {
            // Error
            return null
        }
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

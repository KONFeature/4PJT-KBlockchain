package com.supbank.blockchain.utils

import org.slf4j.Logger

import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec


object SignatureUtils {

    /**
     * The keyFactory defines which algorithms are used to generate the private/public keys.
     */
    private var keyFactory: KeyFactory? = null
    private val log: Logger? = null

    private val signatureObj: Signature
        @Throws(NoSuchAlgorithmException::class)
        get() = Signature.getInstance("SHA256withRSA")

    init {
        try {
            keyFactory = KeyFactory.getInstance("RSA")
        } catch (e: NoSuchAlgorithmException) {
            log?.error("Failed initializing keyFactory {}", e)
        }

    }

    /**
     * Generate a random key pair.
     * @return KeyPair containg private and public key
     */
    @Throws(NoSuchAlgorithmException::class)
    fun generateKeyPair(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance("RSA")
        keyGen.initialize(2048, SecureRandom())
        return keyGen.generateKeyPair()
    }

    /**
     * Verify if the given signature is valid regarding the data and publicKey.
     * @param data raw data which was signed
     * @param signature to proof the validity of the sender
     * @param publicKey key to verify the data was signed by owner of corresponding private key
     * @return true if the signature verification succeeds.
     */
    @Throws(InvalidKeySpecException::class, NoSuchAlgorithmException::class, InvalidKeyException::class, SignatureException::class)
    fun verify(data: ByteArray, signature: ByteArray, publicKey: ByteArray): Boolean {
        // construct a public key from raw bytes
        val keySpec = X509EncodedKeySpec(publicKey)
        val publicKeyObj = keyFactory!!.generatePublic(keySpec)

        // do the verification
        val sig = signatureObj
        sig.initVerify(publicKeyObj)
        sig.update(data)
        return sig.verify(signature)
    }

    /**
     * Sign given data with a private key
     * @param data raw data to sign
     * @param privateKey to use for the signage process
     * @return signature of data which can be verified with corresponding public key
     */
    @Throws(Exception::class)
    fun sign(data: ByteArray, privateKey: ByteArray): ByteArray {
        // construct a PrivateKey-object from raw bytes
        val keySpec = PKCS8EncodedKeySpec(privateKey)
        val privateKeyObj = keyFactory!!.generatePrivate(keySpec)

        // do the signage
        val sig = signatureObj
        sig.initSign(privateKeyObj)
        sig.update(data)
        return sig.sign()
    }

}
package com.supbank.blockchain.utils

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import org.slf4j.LoggerFactory
import sun.security.tools.keytool.CertAndKeyGen
import sun.security.x509.X500Name
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature
import java.security.cert.Certificate
import java.security.spec.X509EncodedKeySpec
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

object CryptoUtil {

    // Info about ur algorithm
    private const val CRYPTO_PROVIDER = "SupBankBlockchain"
    private const val CRYPTO_ALGORITHM = "RSA"
    private const val CRYPTO_METHOD = "SHA256WithRSA"
    private const val CRYPTO_BITS = 2048
    private const val CRYPTO_TRANSFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"

    private const val CRYPTO_SYMMETRICAL_ALGORITHM = "AES"
    private const val CRYPTO_SYMMETRICAL_BITS = 256

    const val CERT_VALIDITY = 5.toLong() * 365 * 24 * 60 * 60
    val CERT_NAME = X500Name("CN=$CRYPTO_PROVIDER, O=$CRYPTO_PROVIDER, L=Nantes, C=FR")

    // Json message key
    private const val JSON_KEY_MESSAGE="_A2}+7tT@/Y!F`Et"
    private const val JSON_KEY_AES="x@?4g]TZ7!:ESDd]"

    // Cipher
    private val cipher = Cipher.getInstance(CRYPTO_TRANSFORM)
    private val cipherAes = Cipher.getInstance(CRYPTO_SYMMETRICAL_ALGORITHM)

    // Logger
    private val log = LoggerFactory.getLogger(this::class.java)

    /**
     * Function used to create a certificate
     */
    fun createKeyPair() : CertAndKeyGen {
        log.debug("Generating keypair using Cert and KeyGen")
        val certGen = CertAndKeyGen(CRYPTO_ALGORITHM, CRYPTO_METHOD, CRYPTO_PROVIDER)
        certGen.generate(CRYPTO_BITS)

        return certGen
    }

    /**
     * Function used to check that the private key match the public certificate
     */
    fun checkKeyPair(privateKey: PrivateKey, publicCert: Certificate) : Boolean {
        val challenge = ByteArray(1000)
        ThreadLocalRandom.current().nextBytes(challenge)

        // Sign the challenge with the private key
        val sig = Signature.getInstance(CRYPTO_METHOD)
        sig.initSign(privateKey)
        sig.update(challenge)
        val signature = sig.sign()

        // Verify with the public key
        sig.initVerify(publicCert)
        sig.update(challenge)

        return sig.verify(signature)
    }

    /**
     * Get the public key corresponding to an encoded string
     */
    fun getPubKeyFromEncodedString(pubString: String) : PublicKey {
        val pubSpec = X509EncodedKeySpec(Base64.getDecoder().decode(pubString))
        return KeyFactory.getInstance(CRYPTO_ALGORITHM).generatePublic(pubSpec)
    }

    /**
     * Function used to generate a symetrical AES key
     */
    private fun generateAesKey() : SecretKey {
        val generator = KeyGenerator.getInstance(CRYPTO_SYMMETRICAL_ALGORITHM)
        generator.init(CRYPTO_SYMMETRICAL_BITS)
        return generator.generateKey()
    }

    /**
     * Function used to encrypt a String using a public key
     *
     * (Generate a symetrical key, encrypt the message with it, encrypt the symetrical key, convert the 2 element to gson and return them)
     */
    fun encrypt(msg: String, key: PublicKey) : String {
        // Create AES key & init cipher
        cipher.init(Cipher.PUBLIC_KEY, key)
        val aesKey = generateAesKey()
        cipherAes.init(Cipher.ENCRYPT_MODE, aesKey)

        // Crypt message
        val msgCrypted = cipherAes.doFinal(msg.toByteArray(StandardCharsets.UTF_8))

        // Crypt AES key
        val aesCrypted = cipher.doFinal(aesKey.encoded)

        // Create json object
        val jsonObj = JsonObject()
        jsonObj.addProperty(JSON_KEY_AES, Base64.getEncoder().encodeToString(aesCrypted))
        jsonObj.addProperty(JSON_KEY_MESSAGE, Base64.getEncoder().encodeToString(msgCrypted))

        // Compile and send ur json obj
        return jsonObj.toString()
    }

    /**
     * Function used to decrypt a String using a private key
     *
     * (Decode gson, decode symetrical key, decode message wit the decoded symetrical key)
     */
    fun decrypt(msg: String, key: PrivateKey) : String {
        // Get obj and extract data
        val jsonObj = Gson().fromJson<JsonElement>(msg, JsonElement::class.java).asJsonObject
        val cryptedAes = Base64.getDecoder().decode(jsonObj.get(JSON_KEY_AES).asString)
        val cryptedMsg = Base64.getDecoder().decode(jsonObj.get(JSON_KEY_MESSAGE).asString)

        // Decrypt aes key
        cipher.init(Cipher.PRIVATE_KEY, key)
        val decrytedAesKey = cipher.doFinal(cryptedAes)
        val aesKey = SecretKeySpec(decrytedAesKey, 0, decrytedAesKey.size, CRYPTO_SYMMETRICAL_ALGORITHM)

        // Decrypt message
        cipherAes.init(Cipher.DECRYPT_MODE, aesKey)
        val decryptedMsg = cipherAes.doFinal(cryptedMsg)

        // Convert & send the result
        return String(decryptedMsg, StandardCharsets.UTF_8)
    }
}

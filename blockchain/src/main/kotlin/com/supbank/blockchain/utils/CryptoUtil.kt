package com.supbank.blockchain.utils

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

object CryptoUtil {

    // Const
    const val CRYPTO_PROVIDER = "SupBankBlockchain"
    const val CRYPTO_ALGORITHM = "RSA"
    const val CRYPTO_METHOD = "SHA256WithRSA"
    const val CRYPTO_BITS = 2048
    const val CRYPTO_TRANSFORM = "RSA/ECB/OAEPWithSHA1AndMGF1Padding"
    const val CERT_VALIDITY = 5.toLong() * 365 * 24 * 60 * 60
    val CERT_NAME = X500Name("CN=$CRYPTO_PROVIDER, O=$CRYPTO_PROVIDER, L=Nantes, C=FR")

    const val PUB_KEY_NAME = "pub.key"
    const val PRIV_KEY_NAME = "priv.key"

    // Factory and cipher
    private val factory = KeyFactory.getInstance(CRYPTO_ALGORITHM)
    private val cipher = Cipher.getInstance(CRYPTO_TRANSFORM)

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

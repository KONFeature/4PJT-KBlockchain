package com.supbank.blockchain.models

import com.fasterxml.jackson.annotation.JsonIgnore
import com.google.gson.annotations.Expose
import com.supbank.blockchain.utils.CryptoUtil
import java.security.PrivateKey
import java.security.PublicKey
import java.util.*
import javax.persistence.*

/**
 * Represent a wallet of the blockchain
 * The @Transient field are not stored in db (for the amount they are calculated from the blockchain, and for the privkey it's loaded if the wallet is connected)
 * The @Expose field are exposed when sending request to socket
 */
@Entity
data class Wallet(
        @Expose
        @Column(nullable = false)
        val name: String,

        @Expose
        @Lob
        @Column(nullable = false)
        @Convert(converter = PublicKeyConverter::class)
        @JsonIgnore
        val pubKey: PublicKey,

        @Transient
        @JsonIgnore
        var privKey: PrivateKey
)
{
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    // Amount of coin in the wallet confirmed (deduced by the block present in the chain)
    @Expose
    @Column(nullable = false)
    var amount: Long = 0L

    @Column
    var mail: String? = null

    @Column
    var token: String? = null

    override fun toString(): String {
        return "name: $name, id: $id, amount: $amount"
    }

    /**
     * Class helping us to store the public key in db
     */
    class PublicKeyConverter: AttributeConverter<PublicKey, String> {
        override fun convertToEntityAttribute(dbData: String?): PublicKey {
            return CryptoUtil.getPubKeyFromEncodedString(dbData?:"")
        }

        override fun convertToDatabaseColumn(attribute: PublicKey?): String {
            return Base64.getEncoder().encodeToString(attribute?.encoded)
        }
    }
}

package com.supbank.blockchain.models

import com.google.gson.annotations.Expose
import java.security.PrivateKey
import java.security.PublicKey
import javax.persistence.*
import kotlin.jvm.Transient

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
        @Column(unique = true, nullable = false)
        val pubKey: PublicKey,

        @Transient
        var privKey: PrivateKey
)
{
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    // Amount of coin in the wallet confirmed (deduced by the block present in the chain)
    @Transient
    val amount: Long = 0L

    // Amount of coin in the wallet for transaction not mined
    @Transient
    val futurAmount: Long = 0L
}

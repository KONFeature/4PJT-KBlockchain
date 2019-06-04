package com.supbank.blockchain.models

import com.google.gson.annotations.Expose
import com.supbank.blockchain.utils.CryptoUtil
import com.supbank.blockchain.utils.hash
import java.time.Instant
import javax.persistence.*

@Entity
data class Transaction(
        @Expose
        @Column(nullable = false)
        val senderId: Long,

        @Expose
        @Column(nullable = false)
        val receiverId: Long,

        @Expose
        @Column(nullable = false)
        val amount: Int,

        @Expose
        @Lob
        @Column
        val message: String
)
{
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Expose
    @Column
    var timestamp: Long = Instant.now().toEpochMilli()

    @Expose
    @Column(nullable = false)
    var mined: Boolean = false

    fun hash() = "$senderId$receiverId$amount$message".hash()

    override fun toString() =
            "Transaction{id=$id, sender=$senderId, receiver=$receiverId, amount=$amount, message=$message, mined=$mined}"

    companion object {
         fun create(receiver: Wallet, sender: Wallet, message: String, amount: Int) =
                 Transaction(sender.id,
                     receiver.id,
                     amount,
                     CryptoUtil.encrypt(message, receiver.pubKey))
    }
}

package com.supbank.blockchain.models

import com.supbank.blockchain.utils.CryptoUtil
import com.supbank.blockchain.utils.hash
import java.time.Instant
import javax.persistence.*

@Entity
data class Transaction(
        @Column(nullable = false)
        val senderId: Long,

        @Column(nullable = false)
        val receiverId: Long,

        @Column(nullable = false)
        val amount: Int,

        @Lob
        @Column
        val message: String
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column
    val timestamp: Long = Instant.now().toEpochMilli()

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

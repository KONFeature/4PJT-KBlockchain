package com.supbank.blockchain.models

import com.supbank.blockchain.utils.hash
import java.security.PrivateKey
import java.time.Instant
import javax.crypto.Cipher
import javax.persistence.*

@Entity
data class Transaction(
        @Column(nullable = false)
        val senderId: Long,

        @Column(nullable = false)
        val receiverId: Long,

        @Column(nullable = false)
        val amount: Int,

        @Column
        val message: String,

        @Column
        val timestamp: Long = Instant.now().toEpochMilli(),

        @Column(nullable = false)
        var mined: Boolean = false)
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    fun hash() : String {
        return "$senderId$receiverId$amount$message".hash()
    }

    override fun toString(): String {
        return "Transaction{id=$id, sender=$senderId, receiver=$receiverId, amount=$amount, message=$message, mined=$mined}"
    }

    companion object {
         fun createTransaction(receiver: Wallet, message: String, amount: Int) {
             // Encrypt message with the receiver public key
         }
    }
}

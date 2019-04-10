package com.supbank.blockchain.models

import com.supbank.blockchain.utils.hash
import java.time.Instant
import javax.persistence.*

@Entity
data class Transaction(
        @Column
        val senderId: Long,

        @Column
        val receiverId: Long,

        @Column
        val amount: Int,

        @Column
        val message: String,

        @Column
        val timestamp: Long = Instant.now().toEpochMilli(),

        @Column
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
}

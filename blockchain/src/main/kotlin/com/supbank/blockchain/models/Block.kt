package com.supbank.blockchain.models

import com.supbank.blockchain.utils.hash
import java.time.Instant
import javax.persistence.*

@Entity
data class Block(
        @Column
        val prevHash: String,

        @Column
        var hash: String = "",

        @OneToMany
        @JoinTable(uniqueConstraints = [UniqueConstraint(columnNames = ["transactions_id"])])
        val transactions: Collection<Transaction>,

        @Column
        val nonce: Long = 0,

        @Column
        val timestamp: Long = Instant.now().toEpochMilli())
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    init {
        hash = calculateHash()
    }

    /**
     * Function used to calculate the hash of a block
     */
    fun calculateHash(): String {
        val transactionHash = transactions.joinToString { transaction -> transaction.hash() }
        return "$prevHash$transactionHash$timestamp$nonce".hash()
    }

    override fun toString(): String {
        return "Block{id=$id, hash=$hash, prevHash=$prevHash, transactions=$transactions}"
    }
}

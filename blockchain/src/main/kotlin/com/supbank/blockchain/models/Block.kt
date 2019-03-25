package com.supbank.blockchain.models

import javax.persistence.*

@Entity
data class Block(
        @Column
        val prevHash: ByteArray,

        @Column
        val hash: ByteArray?,

        @Column
        val tries: Long,

        @Column
        val transactions: List<Transaction>,

        @Column
        val merkleRoot: ByteArray?,

        @Column
        val timestamp: Long?)
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0

    constructor(prevHash: ByteArray, transactions: List<Transaction>, tries: Long): this(prevHash, null, tries, transactions, null, null) {
        //type = "Unknown"
    }
}


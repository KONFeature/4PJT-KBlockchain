package com.supbank.blockchain.models

import javax.persistence.*

@Entity
data class Block(
        @Column
        val prevHash: ByteArray,

        @Column
        val hash: ByteArray,

        @Column
        val merkleRoot: ByteArray,

        @Column
        val transactions: List<Transaction>,

        @Column
        val difficulty: Long,

        @Column
        val timestamp: Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}
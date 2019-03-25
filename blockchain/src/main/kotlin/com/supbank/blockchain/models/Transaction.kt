package com.supbank.blockchain.models

import javax.persistence.*

@Entity
data class Transaction(
        @Column
        val hash: ByteArray,

        @Column
        val amount: Double,

        @Column
        val sender: ByteArray,

        @Column
        var receiver: ByteArray,

        @Column
        var message: String,

        @Column
        val signature: ByteArray,

        @Column
        val timestamp: Long) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
}
package com.supbank.blockchain.models

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
        val message: String)
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
}
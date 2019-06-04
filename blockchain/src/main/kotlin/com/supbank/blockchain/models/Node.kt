package com.supbank.blockchain.models

import javax.persistence.*

@Entity
data class Node(
        @Column
        val host: String,

        @Column
        val port: Int
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
}

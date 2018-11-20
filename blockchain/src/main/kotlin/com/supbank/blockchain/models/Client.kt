package com.supbank.blockchain.models

import java.net.InetAddress
import javax.persistence.*

@Entity
data class Client(
        @Column
        val addr: InetAddress
)
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int = 0
}
package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Transaction
import org.springframework.data.repository.CrudRepository

interface TransactionRepository: CrudRepository<Transaction, Long> {

    fun findAllByMinedFalse() : List<Transaction>

    fun findAllBySenderId(senderId: Long) : List<Transaction>

    fun findAllByReceiverId(receivedId: Long) : List<Transaction>

    fun getTopByOrderByIdDesc() : Transaction?

}

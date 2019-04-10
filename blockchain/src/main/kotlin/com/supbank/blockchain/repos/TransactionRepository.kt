package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Transaction
import org.springframework.data.repository.CrudRepository

interface TransactionRepository: CrudRepository<Transaction, Int> {

    fun findAllByMinedFalse() : List<Transaction>

}

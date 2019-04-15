package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Wallet
import org.springframework.data.repository.CrudRepository

interface WalletRepository: CrudRepository<Wallet, Int> {
}

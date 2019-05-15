package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Wallet
import org.springframework.data.repository.CrudRepository
import java.security.PublicKey

interface WalletRepository: CrudRepository<Wallet, Long> {

    fun getByPubKeyEquals(pubKey: PublicKey) : Wallet?
}

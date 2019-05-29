package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Wallet
import org.springframework.data.repository.CrudRepository
import java.security.PublicKey

interface WalletRepository: CrudRepository<Wallet, Long> {

    fun getByPubKeyEquals(pubKey: PublicKey) : Wallet?

    fun getTopByOrderByIdDesc() : Wallet?

    fun findByPubKeyLike(pubKey: PublicKey) : Wallet?

    fun findAllByNameLikeOrMailLike(name: String, mail: String) : List<Wallet>
}

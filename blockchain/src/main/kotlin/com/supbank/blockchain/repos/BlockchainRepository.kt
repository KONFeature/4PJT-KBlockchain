package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Block
import org.springframework.data.repository.CrudRepository

interface BlockchainRepository: CrudRepository<Block, Long> {

    fun getTopByOrderByIdDesc() : Block?

    fun findAllByHashLike(hash: String) : List<Block>

}

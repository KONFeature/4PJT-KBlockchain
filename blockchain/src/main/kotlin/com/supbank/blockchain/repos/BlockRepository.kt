package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Block
import org.springframework.data.repository.CrudRepository

interface BlockRepository: CrudRepository<Block, Int> {
}
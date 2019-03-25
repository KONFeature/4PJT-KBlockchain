package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Node
import org.springframework.data.repository.CrudRepository

interface NodeRepository : CrudRepository<Node, Int> {}
package com.supbank.blockchain.repos

import com.supbank.blockchain.models.Client
import org.springframework.data.repository.CrudRepository

interface ClientRepository : CrudRepository<Client, Int> {}
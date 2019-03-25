package com.supbank.blockchain.services

import com.supbank.blockchain.models.Transaction
import com.supbank.blockchain.utils.SignatureUtils
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class TransactionService @Autowired
constructor(private val addressService: AddressService,
            private val log: Logger) {


    /**
     * Pool of Transactions which are not included in a Block yet.
     */
    private val transactionPool = HashSet<Transaction>()


    fun getTransactionPool(): Set<Transaction> {
        return transactionPool
    }

    /**
     * Add a new Transaction to the pool
     * @param transaction Transaction to add
     * @return true if verifcation succeeds and Transaction was added
     */
    @Synchronized
    fun add(transaction: Transaction): Boolean {
        if (verify(transaction)) {
            transferFound(transaction)
            transactionPool.add(transaction)
            return true
        }
        return false
    }

    /**
     * Remove Transaction from pool
     * @param transaction Transaction to remove
     */
    fun remove(transaction: Transaction) {
        transactionPool.remove(transaction)
    }

    /**
     * Does the pool contain all given Transactions?
     * @param transactions Collection of Transactions to check
     * @return true if all Transactions are member of the pool
     */
    fun containsAll(transactions: Collection<Transaction>): Boolean {
        return transactionPool.containsAll(transactions)
    }

    /**
     * Verify if a transaction is correct
     * @param transaction
     * @return
     */
    private fun verify(transaction: Transaction): Boolean {
        // correct address
        if (transaction.sender.equals(transaction.receiver)) {
            log.warn("Same sender and receiver address {} {} ",
                    Base64.getEncoder().encodeToString(transaction.sender),
                    Base64.getEncoder().encodeToString(transaction.receiver))
            return false
        }
        val destination = addressService.getByHash(transaction.receiver)
        if (destination == null) {
            log.warn("Unknown destination address {} ", Base64.getEncoder().encodeToString(transaction.receiver))
            return false
        }
        val sender = addressService.getByHash(transaction.sender)
        if (sender == null) {
            log.warn("Unknown sender address {} ", Base64.getEncoder().encodeToString(transaction.sender))
            return false
        }

        // Correct sender balance
        if (sender!!.getBalance() < transaction.amount) {
            log.warn("Insufficient balance to perform this operation for {}", Base64.getEncoder().encodeToString(transaction.sender))
            return false
        }

        // check the signature
        try {
            if (!SignatureUtils.verify(transaction.getSignableData(), transaction.signature, sender!!.getPublicKey())) {
                log.warn("Invalid transaction signature")
                return false
            }
        } catch (e: Exception) {
            log.error("Error while verifying the transaction {}", e)
            return false
        }

        // correct hash
        if (!Arrays.equals(transaction.hash, transaction.calculateHash())) {
            log.warn("Invalid hash")
            return false
        }

        return true
    }

    /**
     * Transfer the found of a transaction
     * @param transaction
     */
    private fun transferFound(transaction: Transaction) {
        val destination = addressService.getByHash(transaction.receiver)
        val sender = addressService.getByHash(transaction.sender)

        sender.pullFromBalance(transaction.amount)
        destination.putInBalance(transaction.amount)
    }


    /**
     * Download Transactions from other Node and them to the pool
     * @param node Node to query
     * @param restTemplate RestTemplate to use
     */
    fun retrieveTransactions(node: Node, restTemplate: RestTemplate) {
        val transactions = restTemplate.getForObject<Array<Transaction>>(node.getAddress() + "/transaction", Array<Transaction>::class.java)
        Collections.addAll(transactionPool, transactions)
        log.info("Retrieved " + transactions!!.size + " transactions from node " + node.getAddress())
    }
}

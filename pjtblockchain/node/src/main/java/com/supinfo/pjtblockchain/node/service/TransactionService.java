package com.supinfo.pjtblockchain.node.service;


import com.supinfo.pjtblockchain.common.SignatureUtils;
import com.supinfo.pjtblockchain.common.domain.Address;
import com.supinfo.pjtblockchain.common.domain.Node;
import com.supinfo.pjtblockchain.common.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class TransactionService {

    private final AddressService addressService;


    /**
     * Pool of Transactions which are not included in a Block yet.
     */
    private Set<Transaction> transactionPool = new HashSet<>();

    @Autowired
    public TransactionService(AddressService addressService) {
        this.addressService = addressService;
    }


    public Set<Transaction> getTransactionPool() {
        return transactionPool;
    }

    /**
     * Add a new Transaction to the pool
     * @param transaction Transaction to add
     * @return true if verifcation succeeds and Transaction was added
     */
    public synchronized boolean add(Transaction transaction) {
        if (verify(transaction)) {
            transferFound(transaction);
            transactionPool.add(transaction);
            return true;
        }
        return false;
    }

    /**
     * Remove Transaction from pool
     * @param transaction Transaction to remove
     */
    public void remove(Transaction transaction) {
        transactionPool.remove(transaction);
    }

    /**
     * Does the pool contain all given Transactions?
     * @param transactions Collection of Transactions to check
     * @return true if all Transactions are member of the pool
     */
    public boolean containsAll(Collection<Transaction> transactions) {
        return transactionPool.containsAll(transactions);
    }

    /**
     * Verify if a transaction is correct
     * @param transaction
     * @return
     */
    private boolean verify(Transaction transaction) {
        // correct address
        if(transaction.getSenderHash().equals(transaction.getReceiverHash())) {
            log.warn("Same sender and receiver address {} {} ",
                    Base64.encodeBase64String(transaction.getSenderHash()),
                    Base64.encodeBase64String(transaction.getReceiverHash()));
            return false;
        }
        Address destination = addressService.getByHash(transaction.getReceiverHash());
        if (destination == null) {
            log.warn("Unknown destination address {} ", Base64.encodeBase64String(transaction.getReceiverHash()));
            return false;
        }
        Address sender = addressService.getByHash(transaction.getSenderHash());
        if (sender == null) {
            log.warn("Unknown sender address {} ", Base64.encodeBase64String(transaction.getSenderHash()));
            return false;
        }

        // Correct sender balance
        if(sender.getBalance() < transaction.getAmount()) {
            log.warn("Insufficient balance to perform this operation for {}", Base64.encodeBase64String(transaction.getSenderHash()));
            return false;
        }

        // check the signature
        try {
            if (!SignatureUtils.verify(transaction.getSignableData(), transaction.getSignature(), sender.getPublicKey())) {
                log.warn("Invalid transaction signature");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while verifying the transaction {}", e);
            return false;
        }

        // correct hash
        if (!Arrays.equals(transaction.getHash(), transaction.calculateHash())) {
            log.warn("Invalid hash");
            return false;
        }

        return true;
    }

    /**
     * Transfer the found of a transaction
     * @param transaction
     */
    private void transferFound(Transaction transaction) {
        Address destination = addressService.getByHash(transaction.getReceiverHash());
        Address sender = addressService.getByHash(transaction.getSenderHash());

        sender.pullFromBalance(transaction.getAmount());
        destination.putInBalance(transaction.getAmount());
    }


    /**
     * Download Transactions from other Node and them to the pool
     * @param node Node to query
     * @param restTemplate RestTemplate to use
     */
    public void retrieveTransactions(Node node, RestTemplate restTemplate) {
        Transaction[] transactions = restTemplate.getForObject(node.getAddress() + "/transaction", Transaction[].class);
        Collections.addAll(transactionPool, transactions);
        log.info("Retrieved " + transactions.length + " transactions from node " + node.getAddress());
    }
}

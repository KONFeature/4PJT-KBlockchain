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

    private boolean verify(Transaction transaction) {
        // correct signature
        Address sender = addressService.getByHash(transaction.getSenderHash());
        if (sender == null) {
            log.warn("Unknown address " + Base64.encodeBase64String(transaction.getSenderHash()));
            return false;
        }

        try {
            if (!SignatureUtils.verify(transaction.getSignableData(), transaction.getSignature(), sender.getPublicKey())) {
                log.warn("Invalid signature");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while verification", e);
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

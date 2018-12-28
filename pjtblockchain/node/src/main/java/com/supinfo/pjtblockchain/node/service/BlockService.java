package com.supinfo.pjtblockchain.node.service;


import com.supinfo.pjtblockchain.common.domain.Block;
import com.supinfo.pjtblockchain.common.domain.Node;
import com.supinfo.pjtblockchain.node.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BlockService {

    private final TransactionService transactionService;

    private List<Block> blockchain = new ArrayList<>();

    @Autowired
    public BlockService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    /**
     * Determine the last added Block
     * @return Last Block in chain
     */
    public Block getLastBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }
        return blockchain.get(blockchain.size() - 1);
    }

    /**
     * Append a new Block at the end of chain
     * @param block Block to append
     * @return true if verifcation succeeds and Block was appended
     */
    public synchronized boolean append(Block block) {
        if (verify(block)) {
            blockchain.add(block);

            // remove transactions from pool
            block.getTransactions().forEach(transactionService::remove);
            return true;
        }
        return false;
    }

    /**
     * Download Blocks from other Node and them to the blockchain
     * @param node Node to query
     * @param restTemplate RestTemplate to use
     */
    public void retrieveBlockchain(Node node, RestTemplate restTemplate) {
        Block[] blocks = restTemplate.getForObject(node.getAddress() + "/block", Block[].class);
        Collections.addAll(blockchain, blocks);
        log.info("Retrieved " + blocks.length + " blocks from node " + node.getAddress());
    }


    private boolean verify(Block block) {
        // references last block in chain
        if (blockchain.size() > 0) {
            byte[] lastBlockInChainHash = getLastBlock().getHash();
            if (!Arrays.equals(block.getPreviousBlockHash(), lastBlockInChainHash)) {
                return false;
            }
        } else {
            if (block.getPreviousBlockHash() != null) {
                return false;
            }
        }

        // correct hashes
        if (!Arrays.equals(block.getMerkleRoot(), block.calculateMerkleRoot())) {
            return false;
        }
        if (!Arrays.equals(block.getHash(), block.calculateHash())) {
            return false;
        }

        // transaction limit
        if (block.getTransactions().size() > Config.MAX_TRANSACTIONS_PER_BLOCK) {
            return false;
        }

        // all transactions in pool
        if (!transactionService.containsAll(block.getTransactions())) {
            return false;
        }

        // considered difficulty
        return block.getLeadingZerosCount() >= Config.DIFFICULTY;
    }
}

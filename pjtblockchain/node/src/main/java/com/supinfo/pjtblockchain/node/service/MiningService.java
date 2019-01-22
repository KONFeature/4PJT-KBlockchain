package com.supinfo.pjtblockchain.node.service;


import com.supinfo.pjtblockchain.common.domain.Address;
import com.supinfo.pjtblockchain.common.domain.Block;
import com.supinfo.pjtblockchain.common.domain.Transaction;
import com.supinfo.pjtblockchain.node.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MiningService implements Runnable {

    private final TransactionService transactionService;
    private final NodeService nodeService;
    private final BlockService blockService;
    private final AddressService addressService;

    private AtomicBoolean runMiner = new AtomicBoolean(false);

    private String minerAddressReward;

    @Autowired
    public MiningService(TransactionService transactionService, NodeService nodeService,
                         BlockService blockService, AddressService addressService) {
        this.transactionService = transactionService;
        this.nodeService = nodeService;
        this.blockService = blockService;
        this.addressService = addressService;
    }

    /**
     * Start the miner
     */
    public void startMiner(String miningAddressReward) {
        if (runMiner.compareAndSet(false, true)) {
            this.minerAddressReward = miningAddressReward;
            log.info("Starting miner with address rewarded {}", miningAddressReward);
            Thread thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stop the miner after next iteration
     */
    public void stopMiner() {
        log.info("Stopping miner");
        runMiner.set(false);
    }

    /**
     * Loop for new blocks until someone signals to stop
     */
    @Override
    public void run() {
        while (runMiner.get()) {
            Block block = mineBlock();
            if (block != null) {
                // Found block! Append and publish
                rewardAddress();
                log.info("Mined block with " + block.getTransactions().size() + " transactions and nonce " + block.getTries());
                blockService.append(block);
                nodeService.broadcastPut("block", block);
            }
        }
        log.info("Miner stopped");
    }

    /**
     * Function used to mine a block
     */
    private Block mineBlock() {
        long tries = 0;

        // get previous hash and transactions
        byte[] previousBlockHash = blockService.getLastBlock() != null ? blockService.getLastBlock().getHash() : null;
        List<Transaction> transactions = transactionService.getTransactionPool()
                .stream().limit(Config.MAX_TRANSACTIONS_PER_BLOCK).collect(Collectors.toList());

        // sleep if no more transactions left
        if (transactions.isEmpty()) {
            log.info("No transactions available, pausing");
            try {
                Thread.sleep(Config.REFRESH_RATE_EMPTY_TRANSACTION);
            } catch (InterruptedException e) {
                log.error("Thread interrupted", e);
            }
            return null;
        }

        // try new block until difficulty is sufficient
        while (runMiner.get()) {
            Block block = new Block(previousBlockHash, transactions, tries);
            if (block.getLeadingZerosCount() >= Config.DIFFICULTY) {
                log.info("Successfully mined a block, address {} will be rewarded", minerAddressReward);
                return block;
            }
            tries++;
        }
        return null;
    }

    /**
     * Send the mining reward to the properties address
     */
    private void rewardAddress() {
        if(minerAddressReward == null || minerAddressReward.isEmpty()) {
            log.info("No mining address reward specified");
            return;
        }

        Address rewardedAddress = addressService.getByHash(Base64.decodeBase64(minerAddressReward));
        rewardedAddress.putInBalance(Config.MINING_REWARD_PER_BLOCK_AMOUNT);
    }

}

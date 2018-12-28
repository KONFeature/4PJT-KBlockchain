package com.supinfo.pjtblockchain.common.domain;


import com.google.common.primitives.Longs;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Block {

    /**
     * Unique identifier which can be generated by hashing previousBlockHash, merkleRoot, tries and timestamp
     */
    private byte[] hash;

    /**
     * Hash of previous block in chain
     */
    private byte[] previousBlockHash;

    /**
     * List of Transaction which are part of this Block
     */
    private List<Transaction> transactions;

    /**
     * Hash of all Transaction hashes, calculated in a tree-like manner
     */
    private byte[] merkleRoot;

    /**
     * Self-chosen number to manipulate the Block hash
     */
    private long tries;

    /**
     * Creation time of this Block
     */
    private long timestamp;

    public Block(byte[] previousBlockHash, List<Transaction> transactions, long tries) {
        this.previousBlockHash = previousBlockHash;
        this.transactions = transactions;
        this.tries = tries;
        this.timestamp = System.currentTimeMillis();
        this.merkleRoot = calculateMerkleRoot();
        this.hash = calculateHash();
    }
    /**
     * Calculates the hash using relevant fields of this type
     * @return SHA256-hash as raw bytes
     */
    public byte[] calculateHash() {
        byte[] hashableData = ArrayUtils.addAll(previousBlockHash, merkleRoot);
        hashableData = ArrayUtils.addAll(hashableData, Longs.toByteArray(tries));
        hashableData = ArrayUtils.addAll(hashableData, Longs.toByteArray(timestamp));
        return DigestUtils.sha256(hashableData);
    }

    /**
     * Calculates the Hash of all transactions as hash tree.
     * https://en.wikipedia.org/wiki/Merkle_tree
     * @return SHA256-hash as raw bytes
     */
    public byte[] calculateMerkleRoot() {
        Queue<byte[]> hashQueue = new LinkedList<>(transactions.stream().map(Transaction::getHash).collect(Collectors.toList()));
        while (hashQueue.size() > 1) {
            // take 2 hashes from queue
            byte[] hashableData = ArrayUtils.addAll(hashQueue.poll(), hashQueue.poll());
            // put new hash at end of queue
            hashQueue.add(DigestUtils.sha256(hashableData));
        }
        return hashQueue.poll();
    }

    /**
     * Count the number of bytes in the hash, which are zero at the beginning
     * @return int number of leading zeros
     */
    public int getLeadingZerosCount() {
        for (int i = 0; i < getHash().length; i++) {
            if (getHash()[i] != 0) {
                return i;
            }
        }
        return getHash().length;
    }
}
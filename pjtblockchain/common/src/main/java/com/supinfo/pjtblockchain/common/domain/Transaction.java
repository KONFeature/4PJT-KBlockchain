package com.supinfo.pjtblockchain.common.domain;


import com.google.common.primitives.Longs;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Transaction {

    /**
     * Unique identifier which can be generated by hashing text, senderHash, signature and timestamp
     */
    private byte[] hash;

    /**
     * Payload of this transaction
     */
    private String text;

    /**
     * The hash of the address which is responsible for this Transaction
     */
    private byte[] senderHash;

    /**
     * Signature of text which can be verified with publicKey of sender address
     */
    private byte[] signature;

    /**
     * Creation time of this Transaction
     */
    private long timestamp;

    public Transaction() {
    }

    public Transaction(String text, byte[] senderHash, byte[] signature) {
        this.text = text;
        this.senderHash = senderHash;
        this.signature = signature;
        this.timestamp = System.currentTimeMillis();
        this.hash = calculateHash();
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte[] getSenderHash() {
        return senderHash;
    }

    public void setSenderHash(byte[] senderHash) {
        this.senderHash = senderHash;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public byte[] getSignableData() {
        return text.getBytes();
    }

    /**
     * Calculates the hash using relevant fields of this type
     * @return SHA256-hash as raw bytes
     */
    public byte[] calculateHash() {
        byte[] hashableData = ArrayUtils.addAll(text.getBytes(), senderHash);
        hashableData = ArrayUtils.addAll(hashableData, signature);
        hashableData = ArrayUtils.addAll(hashableData, Longs.toByteArray(timestamp));
        return DigestUtils.sha256(hashableData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        return Arrays.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(hash);
    }
}

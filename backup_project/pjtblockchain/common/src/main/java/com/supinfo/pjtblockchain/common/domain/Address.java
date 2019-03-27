package com.supinfo.pjtblockchain.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    /**
     * Unique identifier which can be generated by hashing name and publicKey
     */
    private byte[] hash;

    /**
     * Self-given name for this Address
     */
    private String name;

    /**
     * The public key for this Address to ensure everybody is able to verify signed messages
     */
    private byte[] publicKey;

    /**
     * The balance of coin for this address
     */
    private Double balance;

    public Address(String name, byte[] publicKey) {
        this.name = name;
        this.publicKey = publicKey;
        this.hash = calculateHash();
        this.balance = 10.0;
    }

    /**
     * Calculates the hash using relevant fields of this type
     * @return SHA256-hash as raw bytes
     */
    private byte[] calculateHash() {
        byte[] hashableData = ArrayUtils.addAll(name.getBytes(), publicKey);
        return DigestUtils.sha256(hashableData);
    }

    /**
     * Put in the balance the amount
     * @param amount
     */
    public void putInBalance(Double amount) {
        this.balance += amount;
    }

    /**
     * Pull from the balance the amount
     * @param amount
     */
    public void pullFromBalance(Double amount) {
        this.balance -= amount;
    }
}
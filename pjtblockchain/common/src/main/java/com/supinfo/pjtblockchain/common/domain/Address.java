package com.supinfo.pjtblockchain.common.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

@NoArgsConstructor
@Data
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

    public Address(String name, byte[] publicKey) {
        this.name = name;
        this.publicKey = publicKey;
        this.hash = calculateHash();
    }

    /**
     * Calculates the hash using relevant fields of this type
     * @return SHA256-hash as raw bytes
     */
    private byte[] calculateHash() {
        byte[] hashableData = ArrayUtils.addAll(name.getBytes(), publicKey);
        return DigestUtils.sha256(hashableData);
    }
}

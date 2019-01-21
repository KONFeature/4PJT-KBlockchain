package com.supinfo.pjtblockchain.client;

import com.supinfo.pjtblockchain.common.SignatureUtils;
import com.supinfo.pjtblockchain.common.domain.Address;
import com.supinfo.pjtblockchain.common.domain.Transaction;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Simple class to help building REST calls for the blockchain from the wallet.
 */
public class BlockchainHelper {

    protected static void generateKeyPair(String privKey, String pubKey) throws NoSuchProviderException, NoSuchAlgorithmException, IOException {
        KeyPair keyPair = SignatureUtils.generateKeyPair();
        Files.write(Paths.get(privKey), keyPair.getPrivate().getEncoded());
        Files.write(Paths.get(pubKey), keyPair.getPublic().getEncoded());
    }

    protected static void publishAddress(URL node, Path publicKey, String name) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        Address address = new Address(name, Files.readAllBytes(publicKey));
        restTemplate.put(node.toString() + "/address?publish=true", address);
        System.out.println("Hash of new address: " + Base64.encodeBase64String(address.getHash()));
    }

    protected static void publishTransaction(URL node, Path privateKey, String text, byte[] senderHash, byte[] receiverHash, Double amount) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        byte[] signature = SignatureUtils.sign(text.getBytes(), Files.readAllBytes(privateKey));
        Transaction transaction = new Transaction(text, amount != null ? amount : 1.0, senderHash, receiverHash, signature);
        restTemplate.put(node.toString() + "/transaction?publish=true", transaction);
        System.out.println("Hash of new transaction: " + Base64.encodeBase64String(transaction.getHash()));
    }


}

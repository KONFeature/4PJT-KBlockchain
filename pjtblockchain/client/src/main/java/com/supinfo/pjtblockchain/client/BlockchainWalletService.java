package com.supinfo.pjtblockchain.client;

import com.supinfo.pjtblockchain.client.helper.FileHelper;
import com.supinfo.pjtblockchain.common.SignatureUtils;
import com.supinfo.pjtblockchain.common.domain.Address;
import com.supinfo.pjtblockchain.common.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * Spring service performing all the wallet action
 * Simple class to help building REST calls for the blockchain from the wallet.
 */
@Slf4j
@Service
public class BlockchainWalletService {

    @Value("${save.file.address.hash:save/addressHash.txt}")
    private String addressSaveFilePath;

    @Value("${save.file.transaction.hash:save/transaction.txt}")
    private String transactionSaveFilePath;

    /**
     * Generate a private and public key
     */
    public void generateKeyPair(String privKey, String pubKey) throws NoSuchProviderException, NoSuchAlgorithmException, IOException {
        log.info("Generating keypair");
        KeyPair keyPair = SignatureUtils.generateKeyPair();
        Path privPath = Files.write(Paths.get(privKey), keyPair.getPrivate().getEncoded());
        Path pubPath = Files.write(Paths.get(pubKey), keyPair.getPublic().getEncoded());
        log.info("Keypair successfully generated, private key path {}, public key path {}", privPath.toAbsolutePath(), pubPath.toAbsolutePath());
    }

    /**
     * Publish a new address on the blockchain
     */
    public void publishAddress(URL node, Path publicKey, String name) throws IOException {
        log.info("Publishing the address");
        RestTemplate restTemplate = new RestTemplate();
        Address address = new Address(name, Files.readAllBytes(publicKey));
        restTemplate.put(node.toString() + "/address?publish=true", address);
        String addressHash = Base64.encodeBase64String(address.getHash());
        log.info("Address successfully published, hash {}", addressHash);
        saveAddressHash(addressHash);
    }

    /**
     * Publish a new transaction on the blockchain
     */
    public void publishTransaction(URL node, Path privateKey, String text, byte[] senderHash, byte[] receiverHash, Double amount) throws Exception {
        log.info("Publishing the transaction");
        RestTemplate restTemplate = new RestTemplate();
        byte[] signature = SignatureUtils.sign(text.getBytes(), Files.readAllBytes(privateKey));
        Transaction transaction = new Transaction(text, amount, senderHash, receiverHash, signature);
        restTemplate.put(node.toString() + "/transaction?publish=true", transaction);
        String transactionHash = Base64.encodeBase64String(transaction.getHash());
        log.info("Transaction successfully published, hash {}", transactionHash);
        saveTransactionHash(transactionHash);
    }

    /**
     * Save the address hash to a file
     */
    private void saveAddressHash(String addressHash) {
        try {
            File file = FileHelper.writeToFile(new File(addressSaveFilePath), addressHash, false);
            log.info("Saved your address hash in {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.warn("Exception occured when saving your address hash {}", e);
        }
    }

    /**
     * Save the transaction hash to a file
     */
    private void saveTransactionHash(String transactionHash) {
        try {
            File file = FileHelper.writeToFile(new File(transactionSaveFilePath), transactionHash, true);
            log.info("Saved your transaction hash in {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.warn("Exception occured when saving your transaction hash {}", e);
        }
    }

}

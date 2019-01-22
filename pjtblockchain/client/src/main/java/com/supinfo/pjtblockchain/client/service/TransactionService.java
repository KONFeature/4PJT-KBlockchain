package com.supinfo.pjtblockchain.client.service;

import com.supinfo.pjtblockchain.client.helper.FileHelper;
import com.supinfo.pjtblockchain.common.SignatureUtils;
import com.supinfo.pjtblockchain.common.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class used to perform all the transaction part of the wallet
 */
@Slf4j
@Service
public class TransactionService {

    @Value("${save.file.transaction.hash:save/transaction.txt}")
    private String transactionSaveFilePath;

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

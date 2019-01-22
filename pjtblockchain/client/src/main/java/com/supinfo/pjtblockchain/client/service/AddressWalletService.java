package com.supinfo.pjtblockchain.client.service;

import com.supinfo.pjtblockchain.client.helper.FileHelper;
import com.supinfo.pjtblockchain.common.SignatureUtils;
import com.supinfo.pjtblockchain.common.domain.Address;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.ParseException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.rmi.server.ExportException;

@Slf4j
@Service
public class AddressWalletService {

    private final CryptoWalletService cryptoWalletService;

    @Value("${save.file.address.hash:save/addressHash.txt}")
    private String addressSaveFilePath;

    @Autowired
    public AddressWalletService(CryptoWalletService cryptoWalletService) {
        this.cryptoWalletService = cryptoWalletService;
    }

    /**
     * Publish a new address on the blockchain
     */
    public void publishAddress(URL node, String name) throws IOException, SecurityException {
        if(!cryptoWalletService.keyExists()) {
            throw new SecurityException("The keypair wasn't found, make sure to generate it.");
        }

        log.info("Publishing the address");
        RestTemplate restTemplate = new RestTemplate();
        Address address = new Address(name, cryptoWalletService.getPublicKey());
        restTemplate.put(node.toString() + "/address?publish=true", address);
        String addressHash = Base64.encodeBase64String(address.getHash());
        log.info("Address successfully published, hash {}", addressHash);
        saveAddressHash(addressHash);
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
}

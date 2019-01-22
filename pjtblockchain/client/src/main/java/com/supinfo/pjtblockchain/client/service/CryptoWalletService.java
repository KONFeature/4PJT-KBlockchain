package com.supinfo.pjtblockchain.client.service;

import ch.qos.logback.core.util.FileUtil;
import com.supinfo.pjtblockchain.common.SignatureUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Slf4j
@Service
public class CryptoWalletService {

    @Value("${key.private:key.priv}")
    private String privateKey;

    @Value("${key.public:key.pub}")
    private String publicKey;

    /**
     * Generate a private and public key
     */
    public void generateKeyPair() throws NoSuchAlgorithmException, IOException {
        log.info("Generating keypair");
        KeyPair keyPair = SignatureUtils.generateKeyPair();
        Path privPath = Files.write(Paths.get(privateKey), keyPair.getPrivate().getEncoded());
        Path pubPath = Files.write(Paths.get(publicKey), keyPair.getPublic().getEncoded());
        log.info("Keypair successfully generated, private key path {}, public key path {}", privPath.toAbsolutePath(), pubPath.toAbsolutePath());
    }

    /**
     * Check if the key exist
     */
    public boolean keyExists() {
        return new File(privateKey).exists() && new File(publicKey).exists();
    }

    /**
     * Get the the private key
     */
    public byte[] getPrivateKey() throws IOException {
        return Files.readAllBytes(new File(privateKey).toPath());
    }

    /**
     * Get the public key
     */
    public byte[] getPublicKey() throws IOException {
        return Files.readAllBytes(new File(publicKey).toPath());
    }

}

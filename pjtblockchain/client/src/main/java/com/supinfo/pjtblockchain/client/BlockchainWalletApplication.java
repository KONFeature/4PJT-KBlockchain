package com.supinfo.pjtblockchain.client;


import com.supinfo.pjtblockchain.client.helper.CliHelper;
import com.supinfo.pjtblockchain.client.service.AddressWalletService;
import com.supinfo.pjtblockchain.client.service.CryptoWalletService;
import com.supinfo.pjtblockchain.client.service.TransactionWalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.net.URL;
import java.util.Map;

import static java.lang.System.exit;


/**
 * Simple command line application to help us communicate with the node.
 * Just run it in command line for instructions on how to use it.
 *
 * Functions include:
 * - Generate Private/Public-Key
 * - Publish a new Address
 * - Publish a new Transaction
 */
@Slf4j
@SpringBootApplication
@PropertySource("file:${application_home}/config/wallet.properties")
public class BlockchainWalletApplication implements CommandLineRunner {

    private final TransactionWalletService transactionWalletService;
    private final AddressWalletService addressWalletService;
    private final CryptoWalletService cryptoWalletService;

    @Value("${node.local.address:http://127.0.0.1:8080/}")
    private String localNodeAddress;

    static URL localNode;

    @Autowired
    public BlockchainWalletApplication(TransactionWalletService transactionWalletService,
                                       AddressWalletService addressWalletService,
                                       CryptoWalletService cryptoWalletService) {
        this.transactionWalletService = transactionWalletService;
        this.addressWalletService = addressWalletService;
        this.cryptoWalletService = cryptoWalletService;
    }

    /**
     * Start the wallet application with Spring
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BlockchainWalletApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    /**
     * Fetch and parse a command
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String[] args) throws Exception {
        // Create the local node Url
        localNode = new URL(localNodeAddress);
        log.info("Started the wallet with the node {}", localNode);

        // Parse and execute the command
        CommandLineParser parser = new DefaultParser();
        Options options = CliHelper.getOptions();

        try {
            CommandLine line = parser.parse(options, args);
            executeCommand(line);
        } catch (ParseException e) {
            log.error("Erreurs lors du parse de la commande {}", e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("BlockchainClient", options , true);
        } catch (SecurityException e) {
            log.error("Erreurs de sécurité lors de l'éxécution de la commande {}", e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("BlockchainClient", options , true);
        }

        exit(0);
    }

    /**
     * Execute a parsed command
     * @param line
     * @throws Exception
     */
    private void executeCommand(CommandLine line) throws Exception {
        if (line.hasOption("keypair")) {
            cryptoWalletService.generateKeyPair();
        } else if (line.hasOption("address")) {
            publishAddress(line);
        } else if (line.hasOption("transaction")) {
            publishTransaction(line);
        }
    }


    /**
     * Publishing a new address
     */
    private void publishAddress(CommandLine commandLine) throws Exception {
        Map<String, String> lineArg = CliHelper.parseAddressCl(commandLine);
        addressWalletService.publishAddress(localNode, lineArg.get("name"));
    }

    /**
     * Publishing a new transaction
     */
    private void publishTransaction(CommandLine commandLine) throws Exception {
        Map<String, String> lineArg = CliHelper.parseTransactionCl(commandLine);
        transactionWalletService.publishTransaction(localNode,
                lineArg.get("message"),
                Base64.decodeBase64(lineArg.get("sender")),
                Base64.decodeBase64(lineArg.get("receiver")),
                Double.parseDouble(lineArg.get("amount")));
    }
}

package com.supinfo.pjtblockchain.client;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

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
@PropertySource("file:${application_home}/config/blockchain.properties")
public class BlockchainWalletApplication implements CommandLineRunner {

    @Autowired
    BlockchainWalletService blockchainWallet;

    @Value("${key.private:key.priv}")
    private String privateKey;

    @Value("${key.public:key.pub}")
    private String publicKey;

    @Value("${node.local.ip:localhost}")
    private String localNodeIp;

    @Value("${node.local.port:8080}")
    private int localNodePort;

    @Value("${node.local.path:/}")
    private String localNodePath;

    static URL localNode;

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
        localNode = new URL("http", localNodeIp, localNodePort, localNodePath);
        log.info("Started the wallet with the node {}", localNode);

        // Parse and execute the command
        CommandLineParser parser = new DefaultParser();
        Options options = getOptions();
        try {
            CommandLine line = parser.parse(options, args);
            executeCommand(line);
        } catch (ParseException e) {
            log.error("Erreurs lors du parse de la commande {}", e.getMessage());
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
            blockchainWallet.generateKeyPair(privateKey, publicKey);

        } else if (line.hasOption("address")) {
            publishAddress(line);

        } else if (line.hasOption("transaction")) {
            publishTransaction(line);
        }
    }

    /**
     * List the available option
     */
    private Options getOptions() {
        OptionGroup actions = new OptionGroup();
        actions.addOption(new Option("k", "keypair", false, "generate private/public key pair"));
        actions.addOption(new Option("a", "address", false, "publish new address"));
        actions.addOption(new Option("t", "transaction", false, "publish new transaction"));
        actions.setRequired(true);

        Options options = new Options();
        options.addOptionGroup(actions);
        options.addOption(Option.builder("n")
                .longOpt("name")
                .hasArg()
                .argName("address")
                .desc("name used to connect to the blockchain, needed for address publishing")
                .build());
        options.addOption(Option.builder("m")
                .longOpt("message")
                .hasArg()
                .argName("message")
                .desc("message for the transaction, needed for transaction publishing")
                .build());
        options.addOption(Option.builder("am")
                .longOpt("amount")
                .hasArg()
                .argName("amount")
                .desc("amount to send for the transaction, needed for transaction publishing")
                .build());
        options.addOption(Option.builder("s")
                .longOpt("sender")
                .hasArg()
                .argName("address")
                .desc("sender address hash (Base64), needed for transaction publishing")
                .build());
        options.addOption(Option.builder("d")
                .longOpt("destination")
                .hasArg()
                .argName("address")
                .desc("destination address hash (Base64), needed for transaction publishing")
                .build());

        return options;
    }

    /**
     * Publishing a new address
     */
    private void publishAddress(CommandLine line) throws Exception {
        String name = line.getOptionValue("name");
        if (name == null) {
            throw new ParseException("name is required");
        }
        if(!(new File(Paths.get(publicKey).toUri()).exists())) {
            throw new ParseException("publickey not found with the value : " + publicKey);
        }
        blockchainWallet.publishAddress(localNode, Paths.get(publicKey), name);
    }

    /**
     * Publishing a new transaction
     */
    private void publishTransaction(CommandLine line) throws Exception {
        String message = line.getOptionValue("message");
        String amountStr = line.getOptionValue("amount");
        Double amount = NumberUtils.isCreatable(amountStr) ? Double.valueOf(amountStr) : null;
        String sender = line.getOptionValue("sender");
        String receiver = line.getOptionValue("destination");
        if (message == null || sender == null || amount == null || receiver == null) {
            throw new ParseException(" message, amount, sender, receiver is required");
        }
        if(!(new File(Paths.get(privateKey).toUri()).exists())) {
            throw new ParseException("privatekey not found with the value : " + privateKey);
        }
        blockchainWallet.publishTransaction(localNode, Paths.get(privateKey), message, Base64.decodeBase64(sender), Base64.decodeBase64(receiver), amount);
    }
}

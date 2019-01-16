package com.supinfo.pjtblockchain.client;


import com.supinfo.pjtblockchain.common.SignatureUtils;
import com.supinfo.pjtblockchain.common.domain.Address;
import com.supinfo.pjtblockchain.common.domain.Transaction;
import org.apache.commons.cli.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


/**
 * Simple command line application to help us communicate with the node.
 * Just run it in command line for instructions on how to use it.
 *
 * Functions include:
 * - Generate Private/Public-Key
 * - Publish a new Address
 * - Publish a new Transaction
 */
public class BlockchainWallet {

    static final String DEFAULT_PRIV_KEY = "key.priv";
    static final String DEFAULT_PUB_KEY = "key.pub";

    public static void main(String args[]) throws Exception {
        CommandLineParser parser = new DefaultParser();
        Options options = getOptions();
        try {
            CommandLine line = parser.parse(options, args);
            executeCommand(line);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("BlockchainClient", options , true);
        }
    }

    private static void executeCommand(CommandLine line) throws Exception {
        if (line.hasOption("keypair")) {
            BlockchainHelper.generateKeyPair();

        } else if (line.hasOption("address")) {
            publishAddress(line);

        } else if (line.hasOption("transaction")) {
            publishTransaction(line);
        }
    }

    private static Options getOptions() {
        OptionGroup actions = new OptionGroup();
        actions.addOption(new Option("k", "keypair", false, "generate private/public key pair"));
        actions.addOption(new Option("a", "address", false, "publish new address"));
        actions.addOption(new Option("t", "transaction", false, "publish new transaction"));
        actions.setRequired(true);

        Options options = new Options();
        options.addOptionGroup(actions);
        options.addOption(Option.builder("o")
                .longOpt("node")
                .hasArg()
                .argName("Node URL")
                .desc("needed for address and transaction publishing")
                .build());
        options.addOption(Option.builder("n")
                .longOpt("name")
                .hasArg()
                .argName("name for new address")
                .desc("needed for address publishing")
                .build());
        options.addOption(Option.builder("p")
                .longOpt("publickey")
                .hasArg()
                .argName("path to key file")
                .desc("needed for address publishing")
                .build());
        options.addOption(Option.builder("v")
                .longOpt("privatekey")
                .hasArg()
                .argName("path to key file")
                .desc("needed for transaction publishing")
                .build());
        options.addOption(Option.builder("m")
                .longOpt("message")
                .hasArg()
                .argName("message for the transaction")
                .desc("needed for transaction publishing")
                .build());
        options.addOption(Option.builder("a")
                .longOpt("amount")
                .hasArg()
                .argName("amount to send")
                .desc("needed for transaction publishing")
                .build());
        options.addOption(Option.builder("s")
                .longOpt("sender")
                .hasArg()
                .argName("sender address hash (Base64)")
                .desc("needed for transaction publishing")
                .build());
        options.addOption(Option.builder("r")
                .longOpt("receiver")
                .hasArg()
                .argName("target of the transaction address hash (Base64)")
                .desc("needed for transaction publishing")
                .build());

        return options;
    }

    /**
     * Publishing a new address
     */
    private static void publishAddress(CommandLine line) throws Exception {
        String node = line.getOptionValue("node");
        String name = line.getOptionValue("name");
        String publickey = line.getOptionValue("publickey");
        if(publickey == null || publickey.isEmpty()) {
            publickey = DEFAULT_PUB_KEY;
        }
        if (node == null || name == null) {
            throw new ParseException("node, name and publickey is required");
        }
        if(!(new File(Paths.get(publickey).toUri()).exists())) {
            throw new ParseException("publickey not found with the value : " + publickey);
        }
        BlockchainHelper.publishAddress(new URL(node), Paths.get(publickey), name);
    }

    /**
     * Publishing a new transaction
     */
    private static void publishTransaction(CommandLine line) throws Exception {
        String node = line.getOptionValue("node");
        String message = line.getOptionValue("message");
        String amountStr = line.getOptionValue("amount");
        Double amount = Double.isNaN(Double.valueOf(amountStr)) ? null : Double.valueOf(amountStr);
        String sender = line.getOptionValue("sender");
        String receiver = line.getOptionValue("receiver");
        String privatekey = line.getOptionValue("privatekey");
        if(privatekey == null || privatekey.isEmpty()) {
            privatekey = DEFAULT_PRIV_KEY;
        }
        if (node == null || message == null || sender == null || privatekey == null) {
            throw new ParseException("node, message, amount, sender, receiver and privatekey is required");
        }
        if(!(new File(Paths.get(privatekey).toUri()).exists())) {
            throw new ParseException("privatekey not found with the value : " + privatekey);
        }
        BlockchainHelper.publishTransaction(new URL(node), Paths.get(privatekey), message, Base64.decodeBase64(sender), Base64.decodeBase64(receiver), amount);
    }
}

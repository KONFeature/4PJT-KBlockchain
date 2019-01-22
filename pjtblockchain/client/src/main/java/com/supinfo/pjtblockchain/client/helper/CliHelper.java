package com.supinfo.pjtblockchain.client.helper;

import org.apache.commons.cli.*;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;

public class CliHelper {


    /**
     * List the available option
     */
    public static Options getOptions() {
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
     * Function used to assert and parse a transaction command
     * @param line
     * @throws ParseException
     */
    public static HashMap<String, String> parseTransactionCl(CommandLine line) throws ParseException {
        String message = line.getOptionValue("message");
        String amountStr = line.getOptionValue("amount");
        Double amount = NumberUtils.isCreatable(amountStr) ? Double.valueOf(amountStr) : null;
        String sender = line.getOptionValue("sender");
        String receiver = line.getOptionValue("destination");
        if (message == null || sender == null || amount == null || receiver == null) {
            throw new ParseException(" message, amount, sender, receiver is required for a transaction");
        }
        if(sender.equals(receiver)) {
            throw new ParseException(" the sender and the receiver of the transaction have to be different");
        }
        if(amount <= 0 ) {
            throw new ParseException(" the amout cannot be prior to 0");
        }

        HashMap map = new HashMap();
        map.put("message", message);
        map.put("amount", Double.toString(amount));
        map.put("sender", sender);
        map.put("receiver", receiver);
        return map;
    }

    /**
     * Function used to assert and parse an address command
     * @param line
     * @throws ParseException
     */
    public static HashMap<String, String> parseAddressCl(CommandLine line) throws ParseException {
        String name = line.getOptionValue("name");
        if (name == null) {
            throw new ParseException("name is required");
        }

        HashMap map = new HashMap();
        map.put("name", name);
        return map;
    }

}

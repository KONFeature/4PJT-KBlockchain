package com.supinfo.pjtblockchain.node;


public abstract class Config {

    /**
     * Minimum number of leading zeros every block hash has to fulfill
     */
    public static final int DIFFICULTY = 3;

    /**
     * Maximum numver of Transactions a Block can hold
     */
    public static final int MAX_TRANSACTIONS_PER_BLOCK = 4;

    /**
     * The amount added to ur balance when we successfully mined a block
     */
    public static final int MINING_REWARD_AMOUNT = 10;

    /**
     * Refresh rate for the miner when we have no transaction to mine
     */
    public static final int REFRESH_RATE_EMPTY_TRANSACTION = 10000;


}

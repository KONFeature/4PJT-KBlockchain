package com.supbank.blockchain.configs

class Constant {
    companion object {

        /**
         * Minimum number of leading zeros every block hash has to fulfill
         */
        const val DIFFICULTY = 3

        /**
         * Maximum numver of Transactions a Block can hold
         */
        const val MAX_TRANSACTIONS_PER_BLOCK: Long = 4

        /**
         * The amount added to ur balance when we successfully mined a block
         */
        const val MINING_REWARD_AMOUNT = 10

        /**
         * Refresh rate for the miner when we have no transaction to mine
         */
        const val REFRESH_RATE_EMPTY_TRANSACTION: Long = 10000
    }
}
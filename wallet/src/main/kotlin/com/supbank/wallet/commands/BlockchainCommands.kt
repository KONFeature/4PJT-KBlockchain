package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import com.supbank.wallet.print
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class BlockchainCommands(private val blockchainService: BlockchainService) {

    /**
     * Function used to list all the transaction in the pool
     */
    @ShellMethod("Liste toutes les transactions pas encore minées")
    fun transactions() {
        blockchainService.repository.listTransactionsInPool()
                .doOnNext {result ->
                    result.forEach { "Transaction : $it".print() }
                }
                .doOnComplete {
                    "Fin de la récuperation des transactions".print()
                }
                .doOnError { error ->
                    "Echec lors de la récuperation des transactions : ${error.message}".print()
                }
                .subscribe()
    }

    /**
     * Function used tto list the block that compose the blockchain
     */
    @ShellMethod("Liste tous les blocks de la blockchain", key = ["blockchain", "blocks"])
    fun blockchain() {
        blockchainService.repository.listBlocks()
                .doOnNext {result ->
                    result.forEach { "Block : $it".print() }
                }
                .doOnComplete {
                    "Fin de la récuperation des blocks".print()
                }
                .doOnError { error ->
                    "Echec lors de la récuperation des blocks : ${error.message}".print()
                }
                .subscribe()
    }

    /**
     * Function used to call the publish transaction wallet method of the blockchain
     */
    @ShellMethod("Création d'une transaction")
    fun transaction(receiverId: Long, amount: Int, message: String) {
        blockchainService.repository.publishTransaction(message, amount, receiverId)
                .doOnNext {result ->
                    "Transaction crée : ${result?.toString()?:"Aucune transaction"}".print()
                }
                .doOnComplete {
                    "Fin de la création de la transaction".print()
                }
                .doOnError { error ->
                    "Echec lors de la création de la tansaction : ${error.message}".print()
                }
                .subscribe()
    }
}

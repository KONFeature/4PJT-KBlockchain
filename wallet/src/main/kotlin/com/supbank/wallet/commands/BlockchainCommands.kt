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
                .subscribe({result ->
                    result.forEach { " - Transaction : $it".print() }
                }, { error ->
                    "Echec lors de la récuperation des transactions : ${error.message}".print()
                }, {
                    "Fin de la récuperation des transactions".print()
                })
    }

    /**
     * Function used tto list the block that compose the blockchain
     */
    @ShellMethod("Liste tous les blocks de la blockchain", key = ["blockchain", "blocks"])
    fun blockchain() {
        blockchainService.repository.listBlocks()
                .subscribe({result ->
                    result.forEach { " - Block : $it".print() }
                }, { error ->
                    "Echec lors de la récuperation des blocks : ${error.message}".print()
                }, {
                    "Fin de la récuperation des blocks".print()
                })
    }

    /**
     * Function used to call the publish transaction wallet method of the blockchain
     */
    @ShellMethod("Création d'une transaction")
    fun transaction(receiverId: Long, amount: Int, message: String) {
        blockchainService.repository.publishTransaction(message, amount, receiverId)
                .subscribe({result ->
                    "Transaction crée : ${result?.toString()?:"Aucune transaction"}".print()
                }, { error ->
                    "Echec lors de la création de la tansaction : ${error.message}".print()
                }, {
                    "Fin de la création de la transaction".print()
                })
    }
}

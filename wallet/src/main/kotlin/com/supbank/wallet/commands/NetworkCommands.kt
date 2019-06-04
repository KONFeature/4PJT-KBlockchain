package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import com.supbank.wallet.print
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod


@ShellComponent
class NetworkCommands(private val blockchainService: BlockchainService) {


    /**
     * Function used to all the current nodes o the network
     */
    @ShellMethod("Liste les noeuds du réseau")
    fun nodes() {
        blockchainService.repository.nodes()
                .subscribe({result ->
                    result.forEach { " - Noeud : $it".print() }
                }, { error ->
                    "Echec lors de la recuperation des noeuds : ${error.message}".print()
                }, {
                    "Fin de la récuperation des noeuds".print()
                })
    }
}

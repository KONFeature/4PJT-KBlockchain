package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod


@ShellComponent
class NetworkCommand(private val blockchainService: BlockchainService) {


    /**
     * Function used to all the current nodes o the network
     */
    @ShellMethod("Liste les noeud du reseau")
    fun nodes() : String {
        val result = blockchainService.repository.nodes().execute()
        return if(result.isSuccessful) {
            result.body()?.toString()?:"Echec lors de la récuperation des noeuds"
        } else {
            "Echec lors de la récuperation des noeuds"
        }
    }

}

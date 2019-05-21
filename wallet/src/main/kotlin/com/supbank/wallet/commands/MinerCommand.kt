package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MinerCommand(private val blockchainService: BlockchainService) {

    /**
     * Function used to call the mining method  of the blockchain
     */
    @ShellMethod("Lancement du mineur de blockchain")
    fun mine() : String {
        val result = blockchainService.repository.mining(true).execute()
        return if(result.isSuccessful) {
            if(result.body() == true) {
                "Lancement du mineur avec success"
            } else {
                "Echec lors du lancement du mineur"
            }
        } else {
            "Erreur lors de la communication avec la blockchain"
        }
    }

    /**
     * Function used to stop the miner of the blockchain
     */
    @ShellMethod("Fin du mineur de blockchain")
    fun stopMine() : String {
        val result = blockchainService.repository.mining(false).execute()
        return if(result.isSuccessful) {
            if(result.body() == true) {
                "Fin du mineur avec success"
            } else {
                "Echec lors de l'arret du mineur"
            }
        } else {
            "Erreur lors de la communication avec la blockchain"
        }
    }
}

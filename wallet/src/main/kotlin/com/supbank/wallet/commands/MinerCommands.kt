package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import com.supbank.wallet.print
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class MinerCommands(private val blockchainService: BlockchainService) {

    /**
     * Function used to call the mining method  of the blockchain
     */
    @ShellMethod("Lancement du mineur de blockchain")
    fun mine() {
        blockchainService.repository.mining(true)
                .subscribe({result ->
                    if(result)
                        "Lancement du mineur avec succès".print()
                    else
                        "Echec lors du lancement du mineur".print()
                }, { error ->
                    "Echec lors du lancement du mineur : ${error.message}".print()
                })
    }

    /**
     * Function used to stop the miner of the blockchain
     */
    @ShellMethod("Fin du mineur de blockchain")
    fun stopMine() {
        blockchainService.repository.mining(false)
                .subscribe({result ->
                    if(result)
                        "Fin du mineur avec succès".print()
                    else
                        "Echec lors de la terminaison du mineur".print()
                }, { error ->
                    "Echec lors de la terminaison du mineur : ${error.message}".print()
                })
    }
}

package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import com.supbank.wallet.print
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.shell.standard.ShellOption

@ShellComponent
class WalletCommands(private val blockchainService: BlockchainService) {

    /**
     * Function used to change the url and the port of the blockchain
     */
    @ShellMethod("Définition de l'url et du port de la blockchain", key = ["set", "definir"])
    fun setBlockchain(url: String, port: Int) {
        blockchainService.url = url
        blockchainService.port = port
        blockchainService.create()
        getBlockchain()
    }

    /**
     * Function used to change the url and the port of the blockchain
     */
    @ShellMethod("Méthode permettant de voir l'url et le port de la blockchain", key = ["get", "voir"])
    fun getBlockchain() {
        blockchainService.repository.getWallet()
                .subscribe({ result ->
                    "Wallet chargé : ${result?.toString() ?: "aucun wallet"}".print()
                }, { error ->
                    "Echec lors de la récuperation du wallet, probablement aucun wallet chargé : ${error.message}".print()
                    "Url : ${blockchainService.url}, port : ${blockchainService.port}".print()
                }, {
                    "Url : ${blockchainService.url}, port : ${blockchainService.port}".print()
                })
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Création d'un wallet sur la blockchain")
    fun create(name: String) {
        blockchainService.repository.createWallet(name)
                .subscribe({ result ->
                    "Wallet créé : ${result?.toString() ?: "aucun wallet"}".print()
                }, { error ->
                    "Echec lors de la création du wallet : ${error.message}".print()
                }, {
                    "Fin de la création du wallet".print()
                })
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Chargement du wallet local")
    fun load(@ShellOption(help = "Nom ou mail du wallet que vous souhaitez charger") identifier: String) {
        blockchainService.repository.loadWallet(identifier)
                .subscribe({ result ->
                    "Wallet local : ${result?.toString() ?: "Aucun wallet"}".print()
                }, { error ->
                    "Echec lors de la récuperation du wallet pour l'identifier $identifier : ${error.message}".print()
                }, {
                    "Fin de la récuperation du wallet".print()
                })
    }

    /**
     * Function used to list all the wallet of the blockchain
     */
    @ShellMethod("Chargement du wallet local")
    fun wallets() {
        blockchainService.repository.listWallets()
                .subscribe({ result ->
                    result.forEach { " - Wallet : $it".print() }
                }, { error ->
                    "Echec lors de la récuperation des wallets : ${error.message}".print()
                }, {
                    "Fin de la récuperation des wallets".print()
                })
    }
}

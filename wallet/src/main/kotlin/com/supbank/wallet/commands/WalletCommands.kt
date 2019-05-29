package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import com.supbank.wallet.dto.Transaction
import com.supbank.wallet.dto.Wallet
import com.supbank.wallet.print
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class WalletCommands(private val blockchainService: BlockchainService) {

    /**
     * Function used to change the url and the port of the blockchain
     */
    @ShellMethod("Définition de l'url et du port de la blockchain", key = ["set", "definir"])
    fun setBlockchain(url : String, port: Int) {
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
                .doOnNext {result ->
                    "Wallet chargé : ${result?.toString()?:"aucun wallet"}".print()
                }
                .doOnComplete {
                    "Url : ${blockchainService.url}, port : ${blockchainService.port}".print()
                }
                .doOnError { error ->
                    "Echec lors de la récuperation du wallet : ${error.message}".print()
                }
                .subscribe()
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Création d'un wallet sur la blockchain")
    fun create(name: String) {
        blockchainService.repository.createWallet(name)
                .doOnNext {result ->
                    "Wallet créé : ${result?.toString()?:"aucun wallet"}".print()
                }
                .doOnComplete {
                    "Fin de la création du wallet".print()
                }
                .doOnError { error ->
                    "Echec lors de la création du wallet : ${error.message}".print()
                }
                .subscribe()
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Chargement du wallet local")
    fun load() {
        blockchainService.repository.loadWallet()
                .doOnNext {result ->
                    "Wallet local : ${result?.toString()?:"Aucun wallet"}".print()
                }
                .doOnComplete {
                    "Fin de la récuperation du wallet".print()
                }
                .doOnError { error ->
                    "Echec lors de la récuperation du wallet : ${error.message}".print()
                }
                .subscribe()
    }

    /**
     * Function used to list all the wallet of the blockchain
     */
    @ShellMethod("Chargement du wallet local")
    fun wallets() {
        blockchainService.repository.listWallets()
                .doOnNext {result ->
                    result.forEach {
                        "Wallet : $it".print()
                    }
                }
                .doOnComplete {
                    "Fin de la récuperation des wallets".print()
                }
                .doOnError { error ->
                    "Echec lors de la récuperation des wallets : ${error.message}".print()
                }
                .subscribe()
    }
}

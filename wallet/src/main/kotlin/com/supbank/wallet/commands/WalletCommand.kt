package com.supbank.wallet.commands

import com.supbank.wallet.BlockchainService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@ShellComponent
class WalletCommand(private val blockchainService: BlockchainService) {

    /**
     * Function used to change the url and the port of the blockchain
     */
    @ShellMethod("Definition de l'url et du port de la blockchain", key = ["set", "definir"])
    fun setBlockchain(url : String, port: Int) : String {
        blockchainService.url = url
        blockchainService.port = port
        blockchainService.create()
        return getBlockchain()
    }

    /**
     * Function used to change the url and the port of the blockchain
     */
    @ShellMethod("Methode permettant de voir l'url et le port de la blockchain", key = ["get", "voir"])
    fun getBlockchain() : String {
        val result = blockchainService.repository.getWallet().execute()
        return if(result.isSuccessful) {
            "Url : ${blockchainService.url}, port : ${blockchainService.port}, wallet charger : ${result.body()?:"aucun wallet"}"
        } else {
            "Url : ${blockchainService.url}, port : ${blockchainService.port}, wallet charger : aucun wallet"
        }
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Creation d'un wallet sur la blockchain")
    fun create(name: String) : String {
        val result = blockchainService.repository.createWallet(name).execute()
        return if(result.isSuccessful) {
            result.body()?:"Aucune réponse"
        } else {
            "Erreur lors de la communication avec la blockchain"
        }
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Chargement du wallet local")
    fun load() : String {
        val result = blockchainService.repository.loadWallet().execute()
        return if(result.isSuccessful) {
            result.body()?:"Aucune réponse"
        } else {
            "Erreur lors de la communication avec la blockchain"
        }
    }

    /**
     * Function used to call the publish transaction wallet method of the blockchain
     */
    @ShellMethod("Creation d'une transaction")
    fun transaction(receiverId: Long, amount: Int, message: String) : String {
        val result = blockchainService.repository.publishTransaction(message, amount, receiverId).execute()
        return if(result.isSuccessful) {
            result.body()?:"Aucune réponse"
        } else {
            "Erreur lors de la communication avec la blockchain"
        }
    }
}

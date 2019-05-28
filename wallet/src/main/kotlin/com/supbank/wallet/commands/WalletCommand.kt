package com.supbank.wallet.commands

import com.google.gson.stream.MalformedJsonException
import com.supbank.wallet.BlockchainService
import com.supbank.wallet.dto.Transaction
import com.supbank.wallet.dto.Wallet
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

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
        var result: Wallet? = null
        try {
            result = blockchainService.repository.getWallet().execute().body()
        } catch(e: Exception) {
            System.out.println("Aucune réponse")
        }
        return "Url : ${blockchainService.url}, port : ${blockchainService.port}, wallet charger : ${result?.toString()?:"aucun wallet"}"
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Creation d'un wallet sur la blockchain")
    fun create(name: String) : String {
        var result: Wallet? = null
        try {
            result = blockchainService.repository.createWallet(name).execute().body()
        } catch(e: Exception) {
            System.out.println("Aucune réponse")
        }
        return result?.toString()?:"Aucune réponse"
    }

    /**
     * Function used to call the create wallet method of the blockchain
     */
    @ShellMethod("Chargement du wallet local")
    fun load() : String {
        var result: Wallet? = null
        try {
            result = blockchainService.repository.loadWallet().execute().body()
        } catch(e: Exception) {
            System.out.println("Aucune réponse")
        }
        return result?.toString()?:"Aucune réponse"
    }

    /**
     * Function used to call the publish transaction wallet method of the blockchain
     */
    @ShellMethod("Creation d'une transaction")
    fun transaction(receiverId: Long, amount: Int, message: String) : String {
        var result: Transaction? = null
        try {
            result = blockchainService.repository.publishTransaction(message, amount, receiverId).execute().body()
        } catch(e: Exception) {
            System.out.println("Aucune réponse")
        }
        return result?.toString()?:"Aucune réponse"
    }
}

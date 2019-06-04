package com.supbank.wallet

import com.supbank.wallet.dto.*
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import javax.annotation.PostConstruct

@Service
class BlockchainService {

    companion object {
        const val BASE_URL = "localhost"
        const val BASE_PORT = 8070
    }

    var url : String? = null
    var port : Int? = null

    lateinit var repository: Repository

    /**
     * Create the retrofit interface
     */
    @PostConstruct
    fun create() {
        if(url == null) url = BASE_URL
        if(port == null) port = BASE_PORT

        val client = OkHttpClient.Builder()
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://$url:$port/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

        repository = retrofit.create(Repository::class.java)
    }
}

interface Repository {

    @GET("wallet/create")
    fun createWallet(@Query("name") name: String) : Observable<Wallet>

    @GET("wallet/load")
    fun loadWallet(@Query(value = "identifier") identifier: String) : Observable<Wallet>

    @GET("wallet/status")
    fun getWallet() : Observable<Wallet>

    @POST("wallet/publish")
    fun publishTransaction(@Body request: CreateTransactionRequest) : Observable<Transaction>

    @GET("wallet/miner")
    fun mining(@Query(value = "status") status: Boolean) : Observable<Boolean>

    @POST("blockchain/wallets")
    fun listWallets() : Flowable<List<Wallet>>

    @POST("blockchain/pool")
    fun listTransactionsInPool() : Flowable<List<Transaction>>

    @POST("blockchain/blocks")
    fun listBlocks() : Flowable<List<Block>>

    @GET("network/nodes")
    fun nodes() : Observable<List<Node>>
}

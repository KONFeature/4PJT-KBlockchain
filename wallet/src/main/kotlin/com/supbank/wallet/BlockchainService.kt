package com.supbank.wallet

import okhttp3.OkHttpClient
import org.springframework.stereotype.Service
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.annotation.PostConstruct

@Service
class BlockchainService {

    companion object {
        // TODO : Load the base URL dynamically (terminal ? or properties ?)
        const val BASE_URL = "http://localhost:8070/"
    }

    lateinit var repository: Repository

    /**
     * Create the retrofit interface
     */
    @PostConstruct
    fun create() {
        val client = OkHttpClient.Builder()
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build()

        repository = retrofit.create(Repository::class.java)
    }
}

interface Repository {

    @GET("wallet/create")
    fun createWallet(@Query("name") name: String) : Call<String>

    @GET("wallet/load")
    fun loadWallet() : Call<String>

    @GET("wallet/publish")
    fun publishTransaction() : Call<String>

    @GET("wallet/miner")
    fun mining(@Query(value = "status") status: Boolean) : Call<Boolean>
}

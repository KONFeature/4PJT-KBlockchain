package com.supbank.blockchain.utils.p2p.sync

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.supbank.blockchain.utils.SyncField
import com.supbank.blockchain.utils.p2p.P2pPayload

class SyncPayload(private val toSync : Map<SyncField, List<Any>>) : P2pPayload(title) {
    companion object{
        const val title = "response_for_sync"
        val type = object : TypeToken< Map<SyncField, List<Any>>>() {}.type
        const val arraySize = 10
    }

    override fun getData(): String {
        return Gson().toJson(toSync)
    }
}

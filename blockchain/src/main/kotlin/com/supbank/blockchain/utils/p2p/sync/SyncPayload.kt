package com.supbank.blockchain.utils.p2p.sync

import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import com.supbank.blockchain.utils.GsonUtils
import com.supbank.blockchain.utils.SyncField
import com.supbank.blockchain.utils.p2p.P2pPayload
import java.lang.reflect.Type

class SyncPayload(private val toSync : Map<SyncField, List<Any>>) : P2pPayload(title) {
    companion object{
        const val title = "response_for_sync"
        val type: Type = object : TypeToken<Map<SyncField, JsonArray>>() {}.type
        const val arraySize = 10
    }

    override fun getData(): String {
        return GsonUtils.getWalletCustom().toJson(toSync)
    }
}

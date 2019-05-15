package com.supbank.blockchain.utils.p2p.sync

import com.google.gson.Gson
import com.supbank.blockchain.utils.SyncField
import com.supbank.blockchain.utils.p2p.P2pPayload

class AskSyncPayload(private val fields: List<SyncField>) : P2pPayload(title) {
    companion object{
        const val title = "ask_for_sync"
    }

    // Data = current node pojo
    override fun getData() = Gson().toJson(fields)
}

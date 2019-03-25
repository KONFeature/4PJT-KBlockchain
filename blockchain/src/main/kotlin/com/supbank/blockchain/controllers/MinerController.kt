package com.supbank.blockchain.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/miner")
class MinerController {
    /*
    lancer et stopper le mineur
    mineur = prendre une liste de transaction et la transformer en blocks
     */

    @GetMapping("/start-miner")
    fun startMiner(){

    }

    @GetMapping("/stop-miner")
    fun stopMiner(){

    }
}
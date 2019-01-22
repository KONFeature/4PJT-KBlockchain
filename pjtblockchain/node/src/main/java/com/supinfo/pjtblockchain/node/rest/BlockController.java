package com.supinfo.pjtblockchain.node.rest;


import com.supinfo.pjtblockchain.node.service.AddressService;
import com.supinfo.pjtblockchain.node.service.BlockService;
import com.supinfo.pjtblockchain.node.service.MiningService;
import com.supinfo.pjtblockchain.node.service.NodeService;
import com.supinfo.pjtblockchain.common.domain.Block;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("block")
public class BlockController {

    private final BlockService blockService;
    private final NodeService nodeService;
    private final MiningService miningService;
    private final AddressService addressService;

    @Autowired
    public BlockController(BlockService blockService, NodeService nodeService,
                           MiningService miningService, AddressService addressService) {
        this.blockService = blockService;
        this.nodeService = nodeService;
        this.miningService = miningService;
        this.addressService = addressService;
    }

    /**
     * Retrieve all Blocks in order of mine date, also known as Blockchain
     * @return JSON list of Blocks
     */
    @RequestMapping
    List<Block> getBlockchain() {
        return blockService.getBlockchain();
    }

    /**
     * Add a new Block at the end of the Blockchain.
     * It is expected that the Block is valid, see BlockService.verify(Block) for details.
     *
     * @param block the Block to add
     * @param publish if true, this Node is going to inform all other Nodes about the new Block
     * @param response Status Code 202 if Block accepted, 406 if verification fails
     */
    @RequestMapping(method = RequestMethod.PUT)
    void addBlock(@RequestBody Block block, @RequestParam(required = false) Boolean publish, HttpServletResponse response) {
        log.info("Add block " + Base64.encodeBase64String(block.getHash()));
        boolean success = blockService.append(block);

        if (success) {
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

            if (publish != null && publish) {
                nodeService.broadcastPut("block", block);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
        }
    }

    /**
     * Start mining of Blocks on this Node in a Thread
     */
    @RequestMapping(path = "start-miner")
    public void startMiner(@RequestParam(value = "address") Optional<String> addressHashOpt) {
        String addressHash = addressHashOpt.isPresent() ? addressHashOpt.get() : Base64.encodeBase64String(addressService.getRandom().getHash());
        miningService.startMiner(addressHash);
    }

    /**
     * Stop mining of Blocks on this Node
     */
    @RequestMapping(path = "stop-miner")
    public void stopMiner() {
        miningService.stopMiner();
    }

}

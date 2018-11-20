package com.supinfo.pjtblockchain.node.rest;


import com.supinfo.pjtblockchain.node.service.NodeService;
import com.supinfo.pjtblockchain.common.domain.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("node")
public class NodeController {

    private final NodeService nodeService;

    @Autowired
    public NodeController(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    /**
     * Get all Nodes this node knows
     * @return JSON list of addresses
     */
    @RequestMapping()
    Set<Node> getNodes() {
        return nodeService.getKnownNodes();
    }

    /**
     * Add a new Node
     * @param node the Node to add
     */
    @RequestMapping(method = RequestMethod.PUT)
    void addNode(@RequestBody Node node) {
        log.info("Add node " + node.getAddress());
        nodeService.add(node);
    }

    /**
     * Remove a Node
     * @param node the Node to remove
     */
    @RequestMapping(path = "remove", method = RequestMethod.POST)
    void removeNode(@RequestBody Node node) {
        log.info("Remove node " + node.getAddress());
        nodeService.remove(node);
    }

    /**
     * Helper to determine the external address for new Nodes.
     * @param request HttpServletRequest
     * @return the remote address
     */
    @RequestMapping(path = "ip")
    String getIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

}

package com.supinfo.pjtblockchain.node.service;


import com.supinfo.pjtblockchain.common.domain.Node;
import com.supinfo.pjtblockchain.node.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class NodeService implements ApplicationListener<WebServerInitializedEvent> {

    private final BlockService blockService;
    private final TransactionService transactionService;
    private final AddressService addressService;

    private Node self;
    private Set<Node> knownNodes = new HashSet<>();
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${master.node.address:http://127.0.0.1:8080/}")
    private String masterNode;

    @Autowired
    public NodeService(BlockService blockService, TransactionService transactionService, AddressService addressService) {
        this.blockService = blockService;
        this.transactionService = transactionService;
        this.addressService = addressService;
    }


    /**
     * Initial setup, query master Node for
     *  - Other Nodes
     *  - All Addresses
     *  - Current Blockchain
     *  - Transactions in pool
     *  and publish self on all other Nodes
     * @param webServerInitializedEvent serverletContainer for port retrieval
     */
    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        Node masterNode = getMasterNode();

        // construct self node
        String host = retrieveSelfExternalHost(masterNode, restTemplate);
        int port = webServerInitializedEvent.getWebServer().getPort();

        self = getSelfNode(host, port);
        log.info("Self address: " + self.getAddress());

        // download data if necessary
        if (self.equals(masterNode)) {
            log.info("Running as master node, nothing to init");
        } else {
            knownNodes.add(masterNode);

            // retrieve data
            retrieveKnownNodes(masterNode, restTemplate);
            addressService.retrieveAddresses(masterNode, restTemplate);
            blockService.retrieveBlockchain(masterNode, restTemplate);
            transactionService.retrieveTransactions(masterNode, restTemplate);

            // publish self
            broadcastPut("node", self);
        }
    }

    /**
     * Logout from every other Node before shutdown
     */
    @PreDestroy
    public void shutdown() {
        log.info("Shutting down");
        broadcastPost("node/remove", self);
        log.info(knownNodes.size() + " informed");
    }


    public Set<Node> getKnownNodes() {
        return knownNodes;
    }

    public synchronized void add(Node node) {
        knownNodes.add(node);
    }

    public synchronized void remove(Node node) {
        knownNodes.remove(node);
    }

    /**
     * Invoke a PUT request on all other Nodes
     * @param endpoint the endpoint for this request
     * @param data the data to send
     */
    public void broadcastPut(String endpoint, Object data) {
        knownNodes.parallelStream().forEach(node -> restTemplate.put(node.getAddress() + "/" + endpoint, data));
    }

    /**
     * Invoke a POST request on all other Nodes
     * @param endpoint the endpoint for this request
     * @param data the data to send
     */
    public void broadcastPost(String endpoint, Object data) {
        knownNodes.parallelStream().forEach(node -> restTemplate.postForLocation(node.getAddress() + "/" + endpoint, data));
    }

    /**
     * Download Nodes from other Node and them to known Nodes
     * @param node Node to query
     * @param restTemplate RestTemplate to use
     */
    public void retrieveKnownNodes(Node node, RestTemplate restTemplate) {
        Node[] nodes = restTemplate.getForObject(node.getAddress() + "/node", Node[].class);
        Collections.addAll(knownNodes, nodes);
        log.info("Retrieved " + nodes.length + " nodes from node " + node.getAddress());
    }

    private String retrieveSelfExternalHost(Node node, RestTemplate restTemplate) {
        return restTemplate.getForObject(node.getAddress() + "/node/ip", String.class);
    }

    private Node getSelfNode(String host, int port) {
        try {
            return new Node(new URL("http", host, port, ""));
        } catch (MalformedURLException e) {
            log.error("Invalid self URL", e);
            return new Node();
        }
    }

    private Node getMasterNode() {
        try {
            return new Node(new URL(masterNode));
        } catch (MalformedURLException e) {
            log.error("Invalid master node URL", e);
            return new Node();
        }
    }
}

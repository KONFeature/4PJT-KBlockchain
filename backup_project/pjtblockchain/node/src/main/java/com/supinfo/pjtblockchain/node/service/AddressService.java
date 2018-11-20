package com.supinfo.pjtblockchain.node.service;


import com.supinfo.pjtblockchain.common.domain.Address;
import com.supinfo.pjtblockchain.common.domain.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AddressService {

    /**
     * Mapping of Address hash -> Address object
     */
    private Map<String, Address> addresses = new HashMap<>();

    /**
     * Get a specific Address
     * @param hash hash of Address
     * @return Matching Address for hash
     */
    public Address getByHash(byte[] hash) {
        return addresses.get(Base64.encodeBase64String(hash));
    }

    /**
     * Return all Addresses from map
     * @return Collection of Addresses
     */
    public Collection<Address> getAll() {
        return addresses.values();
    }

    /**
     * Add a new Address to the map
     * @param address Address to add
     */
    public synchronized void add(Address address) {
        addresses.put(Base64.encodeBase64String(address.getHash()), address);
    }

    /**
     * Download Addresses from other Node and them to the map
     * @param node Node to query
     * @param restTemplate RestTemplate to use
     */
    public void retrieveAddresses(Node node, RestTemplate restTemplate) {
        Address[] addresses = restTemplate.getForObject(node.getAddress() + "/address", Address[].class);
        Arrays.asList(addresses).forEach(this::add);
        log.info("Retrieved " + addresses.length + " addresses from node " + node.getAddress());
    }
}

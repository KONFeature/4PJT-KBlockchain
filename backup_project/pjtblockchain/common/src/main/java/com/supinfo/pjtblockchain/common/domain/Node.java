package com.supinfo.pjtblockchain.common.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Node {

    /**
     * HTTP-Address including port on which the addressed node listens for incoming connections
     */
    private URL address;
}

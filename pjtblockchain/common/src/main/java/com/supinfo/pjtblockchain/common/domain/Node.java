package com.supinfo.pjtblockchain.common.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Node {

    /**
     * HTTP-Address including port on which the addressed node listens for incoming connections
     */
    private URL address;
}

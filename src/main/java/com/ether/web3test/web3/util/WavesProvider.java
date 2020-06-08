package com.ether.web3test.web3.util;

import com.wavesplatform.wavesj.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WavesProvider {

    public static final byte CHAIN_ID = (byte) 'L';

    private Node node;

    @Value("${waves.connection.url}")
    private String URL;

    public Node node() {
        if (node == null) {
            try {
                node = new Node(URL, CHAIN_ID);
            } catch (Exception e) {
                log.error("Connection to Waves error, message: {}", e.getMessage());
            }
            return node;
        } else {
            return node;
        }
    }
}

package com.ether.web3test.web3.service.block;

import com.ether.web3test.web3.util.WavesProvider;
import com.wavesplatform.wavesj.Block;
import com.wavesplatform.wavesj.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlockServiceImpl implements BlockService{

    @Autowired
    private WavesProvider wavesProvider;

    public Block getByHeight(int height){
        Node node = wavesProvider.node();
        Block block = null;
        try {
            block = node.getBlock(height);
        }catch (Exception e){
            log.error("Get block error, message: {}", e.getMessage());
        }
        return block;
    }
}

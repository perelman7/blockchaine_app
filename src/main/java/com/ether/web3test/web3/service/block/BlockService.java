package com.ether.web3test.web3.service.block;

import com.wavesplatform.wavesj.Block;

public interface BlockService {

    Block getByHeight(int height);
}

package com.ether.web3test.service.block;

import com.ether.web3test.model.block.TrxResponse;
import com.ether.web3test.model.transaction.TypeRequest;

public interface Web3jBlockProvider {

    TrxResponse getAllTransactions(String privateKey, TypeRequest typeRequest);
}

package com.ether.web3test.service.contract;

import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.model.contracts.request.DeploySmartContractRequest;

public interface Web3jSmartContractService {

    String deploySC(DeploySmartContractRequest request);

    FileStorageContract loadSC(String hashAddress, String privateKey);
}

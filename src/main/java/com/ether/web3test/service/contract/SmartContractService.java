package com.ether.web3test.service.contract;

import com.ether.web3test.Test_sol_Test;

public interface SmartContractService {

    String deploySC(String privateKey);
    Test_sol_Test loadSC(String hashAddress, String privateKey);
}

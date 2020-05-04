package com.ether.web3test.service.contract;

import com.ether.web3test.Test_sol_Test;
import com.ether.web3test.model.transaction.GasInfo;
import com.ether.web3test.service.util.Web3jProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

@Service
@Slf4j
public class SmartContractServiceImpl implements SmartContractService {

    @Autowired
    private Web3jProvider web3jProvider;

    /**
     * Deploy smart contract by private key and return hash address of contract
     *
     * @param privateKey wallet`s private key
     * @return hash address of contract
     */
    public String deploySC(String privateKey) {
        String result = null;
        try {
            Web3j web3j = web3jProvider.getWeb3j();
            GasInfo gasInfo = web3jProvider.getGasInfo(null);
            Credentials credentials = Credentials.create(privateKey);
            log.info("Gas info: {}", gasInfo);
            Test_sol_Test contract = Test_sol_Test.deploy(web3j, credentials, gasInfo.getGasPrice(), gasInfo.getGasLimit()).send();
            result = contract.getContractAddress();
        } catch (Exception e) {
            log.error("Deploy smart contract error, message: {}", e.getMessage());
        }
        return result;
    }

    public Test_sol_Test loadSC(String hashAddress, String privateKey) {
        Test_sol_Test result = null;
        try {
            Web3j web3j = web3jProvider.getWeb3j();
            GasInfo gasInfo = web3jProvider.getGasInfo(null);
            Credentials credentials = Credentials.create(privateKey);
            result = Test_sol_Test.load(hashAddress, web3j, credentials, gasInfo.getGasPrice(), gasInfo.getGasLimit());
        } catch (Exception e) {
            log.error("Load smart contract error, message: {}", e.getMessage());
        }
        return result;
    }
}

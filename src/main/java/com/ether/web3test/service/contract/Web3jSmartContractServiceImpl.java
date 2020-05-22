package com.ether.web3test.service.contract;

import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.model.contracts.request.DeploySmartContractRequest;
import com.ether.web3test.model.transaction.GasInfo;
import com.ether.web3test.service.util.Web3jMetadataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.Date;

@Service
@Slf4j
public class Web3jSmartContractServiceImpl implements Web3jSmartContractService {

    @Autowired
    private Web3jMetadataProvider web3jMetadataProvider;

    /**
     * Deploy smart contract by private key and return hash address of contract
     *
     * @param request detailed info of smart contract
     * @return hash address of contract
     */
    public String deploySC(DeploySmartContractRequest request) {
        String result = null;
        try {
            Web3j web3j = web3jMetadataProvider.getWeb3j();
            Credentials credentials = Credentials.create(request.getPrivateKey());
//            DefaultGasProvider defaultGasProvider = new DefaultGasProvider();
            GasInfo gasInfo = web3jMetadataProvider.getGasInfo(null);
            StaticGasProvider defaultGasProvider = new StaticGasProvider(gasInfo.getGasPrice(), gasInfo.getGasLimit());
            log.info("Gas info: limit({}) and price({})", defaultGasProvider.getGasLimit(), defaultGasProvider.getGasPrice());

            FileStorageContract contract = FileStorageContract.deploy(web3j, new RawTransactionManager(web3j, credentials), defaultGasProvider,
                    request.getFileInfo().getName(), request.getFileInfo().getPath(), request.getFileInfo().getExtension(),
                    request.getDescription(), request.getRecipient(), BigInteger.valueOf(new Date().getTime()))
                    .send();

            result = contract.getContractAddress();
        } catch (Exception e) {
            log.error("Deploy smart contract error, message: {}", e.getMessage());
        }
        log.info("FINISH DEPLOY CONTRACT: {}", result);
        return result;
    }

    public FileStorageContract loadSC(String hashAddress, String privateKey) {
        FileStorageContract result = null;
        try {
            Web3j web3j = web3jMetadataProvider.getWeb3j();
            GasInfo gasInfo = web3jMetadataProvider.getGasInfo(null);
            Credentials credentials = Credentials.create(privateKey);
            result = FileStorageContract.load(hashAddress, web3j, credentials, gasInfo.getGasPrice(), gasInfo.getGasLimit());
        } catch (Exception e) {
            log.error("Load smart contract error, message: {}", e.getMessage());
        }
        return result;
    }
}

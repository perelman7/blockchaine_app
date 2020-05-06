package com.ether.web3test.service.contract;

import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.model.transaction.GasInfo;
import com.ether.web3test.service.util.Web3jMetadataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
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
     * @param privateKey wallet`s private key
     * @return hash address of contract
     */
    public String deploySC(String privateKey, MultipartFile file, String description, String recipientAddress) {
        String result = null;
        try {
            Web3j web3j = web3jMetadataProvider.getWeb3j();
            GasInfo gasInfo = web3jMetadataProvider.getGasInfo(null);
            Credentials credentials = Credentials.create(privateKey);
            log.info("Gas info: {}", gasInfo);

            byte[] content = file.getBytes();
            String filename = file.getOriginalFilename();
            String extension = filename.substring(filename.indexOf(".") + 1);

            RawTransactionManager transactionManager = new RawTransactionManager(web3j, credentials);
            DefaultGasProvider defaultGasProvider = new DefaultGasProvider();

            StaticGasProvider staticGasProvider = new StaticGasProvider(defaultGasProvider.getGasPrice().multiply(BigInteger.valueOf(2L)), defaultGasProvider.getGasPrice().multiply(BigInteger.valueOf(2L)));
            System.out.println(staticGasProvider.getGasPrice() + " " + staticGasProvider.getGasPrice());
            FileStorageContract contract = FileStorageContract.deploy(web3j, transactionManager, staticGasProvider,
                    filename, content, extension, description, recipientAddress, BigInteger.valueOf(new Date().getTime()))
                    .send();
//            FileStorageContract contract = FileStorageContract.deploy(web3j, credentials,
//                    gasInfo.getGasPrice(), gasInfo.getGasPrice(),
//                    filename, content, extension, description, recipientAddress, BigInteger.valueOf(new Date().getTime())
//            ).send();
            result = contract.getContractAddress();
        } catch (Exception e) {
            log.error("Deploy smart contract error, message: {}", e.getMessage());
        }
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

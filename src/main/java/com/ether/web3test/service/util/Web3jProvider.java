package com.ether.web3test.service.util;

import com.ether.web3test.model.transaction.GasInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

import static org.web3j.protocol.core.DefaultBlockParameterName.LATEST;

@Component
@Slf4j
public class Web3jProvider {

    @Value("${web3j.connection.url}")
    private String CONNECTION_URL;

    private Web3j web3j;
    private Admin admin;

    private BigInteger gasPrice;
    private BigInteger gasLimit;

    public Admin getAdmin() {
        if (admin == null) {
            admin = Admin.build(new HttpService(CONNECTION_URL));
        }
        return admin;
    }

    public Web3j getWeb3j() {
        if (web3j == null) {
            web3j = Web3j.build(new HttpService(CONNECTION_URL));
        }
        return web3j;
    }

    public GasInfo getGasInfo(String accountName) {
        GasInfo result = null;
        try {
            Web3j web3j = this.getWeb3j();
            BigInteger nonce = null;
            if(accountName != null){
                //Getting current nonce
                EthGetTransactionCount currentTransaction = web3j.ethGetTransactionCount(accountName, LATEST).send();
                nonce = currentTransaction.getTransactionCount();
            }

            if (this.gasLimit == null) {
                //Getting block info
                EthBlock send3 = web3j.ethGetBlockByNumber(LATEST, true).send();
                this.gasLimit = send3.getResult().getGasLimit();
            }
            if (this.gasPrice == null){
                //getting gas prise for transaction
                EthGasPrice send4 = web3j.ethGasPrice().send();
                this.gasPrice = send4.getGasPrice();
            }

            result = GasInfo.builder()
                    .nonce(nonce)
                    .gasLimit(this.gasLimit)
                    .gasPrice(this.gasPrice)
                    .build();
        } catch (Exception e) {
            log.error("Getting gas info ERROR, message: {}", e.getMessage());
        }
        return result;
    }
}

package com.ether.web3test.service.transaction;

import com.ether.web3test.model.transaction.GasInfo;
import com.ether.web3test.service.util.Web3jMetadataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalTime;
import java.util.Optional;

@Service
@Slf4j
public class Web3jTransactionServiceImpl implements Web3jTransactionService {

    @Autowired
    private Web3jMetadataProvider web3jMetadataProvider;

    public String sendTrx(String pk, String recipientAddress, BigDecimal amount) {
        String trxId = null;
        Web3j web3 = web3jMetadataProvider.getWeb3j();

        try {
            Credentials credentials = Credentials.create(pk);
            GasInfo gasInfo = web3jMetadataProvider.getGasInfo(credentials.getAddress());
            BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    gasInfo.getNonce(),
                    gasInfo.getGasPrice(),
                    BigInteger.valueOf(3_000_000L),
                    recipientAddress,
                    value);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
            trxId = ethSendTransaction.getTransactionHash();
            log.info("transactionHash: " + trxId);

            if(trxId == null){
                throw new IOException("Transaction hash is null");
            }
            Optional<TransactionReceipt> transactionReceipt = null;
            do {
                System.out.println("checking if transaction " + trxId + " is mined....");
                EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(trxId).send();
                transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                Thread.sleep(3000);
            } while (!transactionReceipt.isPresent());

            log.info("Transaction " + trxId + " was mined in block # " + transactionReceipt.get().getBlockNumber());

        } catch (IOException | InterruptedException ex) {
            log.error("Send transaction error, message: {}", ex.getMessage());
        }
        return trxId;
    }

    public String send(String pk, String recipientAddress, BigDecimal amount, int nonce) {
        String trxId = null;
        Web3j web3 = web3jMetadataProvider.getWeb3j();

        try {
            Credentials credentials = Credentials.create(pk);
            GasInfo gasInfo = web3jMetadataProvider.getGasInfo(credentials.getAddress());
            BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                    gasInfo.getNonce().add(BigInteger.valueOf(nonce)),
                    gasInfo.getGasPrice().add(gasInfo.getGasPrice().divide(BigInteger.valueOf(10))),
                    BigInteger.valueOf(3_000_000L),
                    recipientAddress,
                    value, "test: " + LocalTime.now());

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
            trxId = ethSendTransaction.getTransactionHash();
        } catch (IOException ex) {
            log.error("Send transaction error, message: {}", ex.getMessage());
        }
        return trxId;
    }
}

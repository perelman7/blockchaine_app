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
import java.util.Optional;

@Service
@Slf4j
public class Web3jTransactionServiceImpl implements Web3jTransactionService {

    @Autowired
    private Web3jMetadataProvider web3jMetadataProvider;

    public void sendTrx(String pk, String recipientAddress, BigDecimal amount) {
        Web3j web3 = web3jMetadataProvider.getWeb3j();

        try {
            Credentials credentials = Credentials.create(pk);
            GasInfo gasInfo = web3jMetadataProvider.getGasInfo(credentials.getAddress());
            BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    gasInfo.getNonce(),
                    gasInfo.getGasPrice(),
                    gasInfo.getGasLimit(),
                    recipientAddress,
                    value);

            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).send();
            String transactionHash = ethSendTransaction.getTransactionHash();
            log.info("transactionHash: " + transactionHash);

            Optional<TransactionReceipt> transactionReceipt = null;
            do {
                System.out.println("checking if transaction " + transactionHash + " is mined....");
                EthGetTransactionReceipt ethGetTransactionReceiptResp = web3.ethGetTransactionReceipt(transactionHash).send();
                transactionReceipt = ethGetTransactionReceiptResp.getTransactionReceipt();
                Thread.sleep(3000);
            } while (!transactionReceipt.isPresent());

            log.info("Transaction " + transactionHash + " was mined in block # " + transactionReceipt.get().getBlockNumber());

        } catch (IOException | InterruptedException ex) {
            log.error("Send transaction error, message: {}", ex.getMessage());
        }
    }
}

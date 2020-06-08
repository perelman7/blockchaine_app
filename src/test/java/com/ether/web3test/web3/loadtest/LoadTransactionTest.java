package com.ether.web3test.web3.loadtest;

import com.ether.web3test.service.transaction.Web3jTransactionService;
import com.ether.web3test.service.util.Web3jMetadataProvider;
import com.ether.web3test.service.wallet.Web3jWalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class LoadTransactionTest {

    @Autowired
    private Web3jTransactionService web3jTransactionService;

    @Autowired
    private Web3jWalletService web3jWalletService;

    @Autowired
    private Web3jMetadataProvider web3jMetadataProvider;

    private static final String PK_SENDER = "5a2e9eef49ea8d45dafd3cbabf54cc2738824f5dfeebfb23fe74f22c155bc25b";
    private static final String PK_RECIPIENT = "bd339d9ce08da57f57dc80c602531310194a44e2ea110c731c27517360d52551";

    @Test
    public void loadTest() {
        Credentials sender = web3jWalletService.getCredentialByPrivateKey(PK_SENDER);
        Credentials recipient = web3jWalletService.getCredentialByPrivateKey(PK_RECIPIENT);
        String senderAddress = sender.getAddress();
        String recipientAddress = recipient.getAddress();
        BigInteger senderBefore = web3jWalletService.getBalanceByAccountNumber(senderAddress);
        BigInteger recipientBefore = web3jWalletService.getBalanceByAccountNumber(recipientAddress);
        System.out.println("BEFORE: sender: " + senderBefore + ", recipient: " + recipientBefore);

        List<String> ids = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 1; i < 1000; i++) {
            String trxHash = web3jTransactionService.send(PK_SENDER, recipientAddress, BigDecimal.valueOf(6), i + 1);
            if (trxHash != null) {
                ids.add(trxHash);
            } else {
                System.out.println("Index : " + i + ", trx id is null");
            }
        }
        System.out.println(ids);
        System.out.println("SPEND: " + (System.currentTimeMillis() - start));

        BigInteger senderAfter = web3jWalletService.getBalanceByAccountNumber(senderAddress);
        BigInteger recipientAfter = web3jWalletService.getBalanceByAccountNumber(recipientAddress);
        System.out.println("AFTER: sender: " + senderAfter + ", recipient: " + recipientAfter);
    }


    @Test
    public void loadTest2() {
        Credentials sender = web3jWalletService.getCredentialByPrivateKey(PK_SENDER);
        Credentials recipient = web3jWalletService.getCredentialByPrivateKey(PK_RECIPIENT);
        String senderAddress = sender.getAddress();
        String recipientAddress = recipient.getAddress();
        BigInteger senderBefore = web3jWalletService.getBalanceByAccountNumber(senderAddress);
        BigInteger recipientBefore = web3jWalletService.getBalanceByAccountNumber(recipientAddress);
        System.out.println("BEFORE: sender: " + senderBefore + ", recipient: " + recipientBefore);

        List<String> ids = new ArrayList<>();
        String trxHash = web3jTransactionService.sendTrx(PK_SENDER, recipientAddress, BigDecimal.ONE);
        if (trxHash != null) {
            ids.add(trxHash);
        }
        System.out.println(ids);
    }

    @Test
    public void test() throws Exception {
        Web3j web3j = web3jMetadataProvider.getWeb3j();
        EthBlock send = web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(BigInteger.valueOf(7291)), true).send();
        System.out.println();
    }
}

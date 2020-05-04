package com.ether.web3test;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

//@SpringBootApplication
public class Web3jApplication {

    public static void main(String[] args) throws Exception {
//		SpringApplication.run(Web3jApplication.class, args);

        //connect to node
        Web3j web3j = Web3j.build(new HttpService("http://localhost:8888"));

        //Get all accounts
        EthAccounts send1 = web3j.ethAccounts().send();
        String accountName = send1.getAccounts().get(0);
        String accountName2 = send1.getAccounts().get(1);

        //admin console for secure actions
        Admin admin = Admin.build(new HttpService("http://localhost:8888"));
        PersonalUnlockAccount acc1 = admin.personalUnlockAccount(accountName, "acc1").send();
        PersonalUnlockAccount acc2 = admin.personalUnlockAccount(accountName2, "acc2").send();

        //Dir where store my account or wallet files
        //todo get to know how get this dir for configs
        File file = new File("C:\\Users\\HP\\regestry\\private\\keystore");

        String path1 = file.getAbsolutePath() + File.separator + "UTC--2020-03-07T08-22-06.145097600Z--92bc143e61c1ec5ae59aacbeeee9c2400c404df8";
        String path2 = file.getAbsolutePath() + File.separator + "UTC--2020-03-07T08-23-13.495328200Z--96905afa0f738cd5954d8be33c84c3e349ffbd33";

        //upload credential from account or wallet file
        Credentials password1 = WalletUtils.loadCredentials("acc1", path1);
        Credentials password2 = WalletUtils.loadCredentials("acc2", path2);

        //init transaction manager
//        TransactionManager transactionManager = new RawTransactionManager(web3j, password1);

        EthBlock send3 = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send();
        BigInteger gasLimit = send3.getResult().getGasLimit();

        //getting gas prise for transaction
        EthGasPrice send4 = web3j.ethGasPrice().send();
        BigInteger gasPrice = send4.getGasPrice();

//        Test_sol_Test send = Test_sol_Test.deploy(web3j, password1, gasPrice, gasLimit).send();
//        String contractAddress = send.getContractAddress();

        String contract = "0x26ed9f4fee361950fd548b108e620bc5c350a196";

        Test_sol_Test test = Test_sol_Test.load(contract, web3j, password1, gasPrice, gasLimit);

        TransactionReceipt send = test.setAge(BigInteger.valueOf(62L)).send();
        TransactionReceipt test_name = test.setName("test name").send();

        BigInteger send2 = test.getAge().send();
        String send5 = test.getName().send();

        System.out.println();


    }

    private void createWallet(String pathToAccountFile) throws CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        //Generate new wallet (account) with json format
        String walletFile1 = WalletUtils.generateFullNewWalletFile("acc1", new File(pathToAccountFile));
    }

    private void checkBalance(Web3j web3j, String account) throws IOException {
        //check balance
        EthGetBalance balance3 = web3j.ethGetBalance(account, DefaultBlockParameterName.LATEST).send();

        //Getting current balance in WEI format by account address
//        EthGetBalance balance1 = web3j.ethGetBalance(accountName, DefaultBlockParameterName.LATEST).send();
//        EthGetBalance balance2 = web3j.ethGetBalance(accountName2, DefaultBlockParameterName.LATEST).send();
    }

    private void sendTransaction(Web3j web3j, String accountName, String accountName2) throws IOException {
        //Getting current nonce
        EthGetTransactionCount nonce = web3j.ethGetTransactionCount(accountName, DefaultBlockParameterName.LATEST).send();

        //todo all gas props can be used as "const" by starting application
        //Getting block info
        EthBlock send3 = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send();
        BigInteger gasLimit = send3.getResult().getGasLimit();

        //getting gas prise for transaction
        EthGasPrice send4 = web3j.ethGasPrice().send();
        BigInteger gasPrice = send4.getGasPrice();

        //init amount of value for transaction
        BigInteger amountToTransferInWei = Convert.toWei(BigDecimal.ONE, Convert.Unit.WEI).toBigInteger();

        //init transaction
//        Transaction etherTransaction = Transaction.createEtherTransaction(accountName, nonce.getTransactionCount(), gasPrice, gasLimit, accountName2, amountToTransferInWei);
        Transaction etherTransaction = Transaction.createFunctionCallTransaction(accountName, nonce.getTransactionCount(), gasPrice, gasLimit, accountName2, amountToTransferInWei, "data test");

        //send transaction
        EthSendTransaction send2 = web3j.ethSendTransaction(etherTransaction).send();
        String transactionHash = send2.getTransactionHash();

        //getting transaction by hash
        EthTransaction send = web3j.ethGetTransactionByHash(transactionHash).send();
    }

}

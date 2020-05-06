package com.ether.web3test.service.block;

import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.service.contract.Web3jSmartContractService;
import com.ether.web3test.service.util.Web3jMetadataProvider;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class Web3jBlockProviderImpl implements Web3jBlockProvider {

    @Autowired
    private Web3jMetadataProvider web3jMetadataProvider;

    @Autowired
    private Web3jSmartContractService web3jSmartContractService;

    public void getAllTransactions(String privateKey) {
        //todo initiate result with transactions and contracts

        Admin admin = web3jMetadataProvider.getAdmin();
        Web3j web3j = web3jMetadataProvider.getWeb3j();

        Disposable disposable = admin.replayPastBlocksFlowable(DefaultBlockParameter.valueOf(BigInteger.ZERO), true)
                .doOnComplete(() -> System.out.println("Subscribe is complete"))
                .doOnError(err -> System.out.println("Error in subscribe: " + err.getMessage()))
                .subscribe(tx -> {
                    int size = tx.getBlock().getTransactions().size();
                    if (size != 0) {
                        System.out.println("**********************************************\nBlock: " + tx.getBlock().getNumberRaw());
                        List<EthBlock.TransactionResult> transactions = tx.getBlock().getTransactions();
                        for (EthBlock.TransactionResult transactionResult : transactions) {
                            EthBlock.TransactionObject o = (EthBlock.TransactionObject) transactionResult.get();
                            Transaction transaction = o.get();

                            if (transaction.getValue().equals(BigInteger.ZERO)) {
                                System.out.println("BLOCK CONTRACT, TX: " + transaction.getBlockNumberRaw() + " "
                                        + transaction.getValue() + " " + transaction.getTransactionIndexRaw()
                                        + "\n****************************************************************");
                                EthGetTransactionReceipt send = web3j.ethGetTransactionReceipt(transaction.getHash()).send();
                                TransactionReceipt transactionReceipt = send.getTransactionReceipt().get();
                                String contractAddress = transactionReceipt.getContractAddress();
                                FileStorageContract fileStorageContract = web3jSmartContractService.loadSC(contractAddress, privateKey);
                                System.out.println("Contract address: " + contractAddress);
                            } else {
                                System.out.println("BLOCK TRANSACTION, TX: " + transaction.getBlockNumberRaw() + " "
                                        + transaction.getValue() + " " + transaction.getFrom() + " " + transaction.getTo() +
                                        " " + transaction.getCreates() + " " + transaction.getHash()
                                        + "\n****************************************************************");
                            }
                        }
                    }
                });

        while (!disposable.isDisposed()) {
            System.out.println("Wait for disposable");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("disposable: " + disposable.isDisposed());
    }
}

package com.ether.web3test.service.block;

import com.ether.web3test.model.block.TrxResponse;
import com.ether.web3test.model.block.items.ContractModel;
import com.ether.web3test.model.block.items.TrxModel;
import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.model.transaction.TypeRequest;
import com.ether.web3test.service.contract.Web3jSmartContractService;
import com.ether.web3test.service.util.SmartContractConvector;
import com.ether.web3test.service.util.Web3jMetadataProvider;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class Web3jBlockProviderImpl implements Web3jBlockProvider {

    @Autowired
    private Web3jMetadataProvider web3jMetadataProvider;

    @Autowired
    private Web3jSmartContractService web3jSmartContractService;

    @Autowired
    private SmartContractConvector convector;

    public TrxResponse getAllTransactions(String privateKey, TypeRequest typeRequest) {
        Admin admin = web3jMetadataProvider.getAdmin();
        Web3j web3j = web3jMetadataProvider.getWeb3j();

        Credentials credentials = Credentials.create(privateKey);

        List<ContractModel> contracts = new ArrayList<>();
        List<TrxModel> trxes = new ArrayList<>();

        Disposable disposable = admin.replayPastBlocksFlowable(DefaultBlockParameter.valueOf(BigInteger.ZERO), true)
                .doOnComplete(() -> log.info("Subscribe is complete"))
                .doOnError(err -> log.error("Error in subscribe: {}", err.getMessage()))
                .subscribe(tx -> {
                    int size = tx.getBlock().getTransactions().size();
                    if (size != 0) {
                        log.info("**************************************\nBlock: {}", tx.getBlock().getNumberRaw());
                        List<EthBlock.TransactionResult> transactions = tx.getBlock().getTransactions();
                        for (EthBlock.TransactionResult transactionResult : transactions) {
                            EthBlock.TransactionObject o = (EthBlock.TransactionObject) transactionResult.get();
                            Transaction transaction = o.get();

                            if (transaction.getValue().equals(BigInteger.ZERO)) {
                                log.info("Transaction with Smart contract");
                                EthGetTransactionReceipt send = web3j.ethGetTransactionReceipt(transaction.getHash()).send();
                                TransactionReceipt transactionReceipt = send.getTransactionReceipt().get();
                                String contractAddress = transactionReceipt.getContractAddress();
                                FileStorageContract fileStorageContract = web3jSmartContractService.loadSC(contractAddress, privateKey);

                                ContractModel convertedContract = convector.convert(fileStorageContract);

                                if (typeRequest.equals(TypeRequest.RECEIVE)
                                        && convertedContract.getRecipient().equals(credentials.getAddress())) {

                                    log.info("Recipient of contract");
                                    contracts.add(convertedContract);

                                } else if (typeRequest.equals(TypeRequest.SEND)
                                        && convertedContract.getSender().equals(credentials.getAddress())) {

                                    log.info("Sender of contract");
                                    contracts.add(convertedContract);
                                }else{
                                    log.info("Contract of other user");
                                }

                                log.info("Contract address: {} \n***************************************", contractAddress);
                            } else {
                                log.info("Transaction send ether");

                                BigDecimal ethBalance = Convert.fromWei(transaction.getValue().toString(), Convert.Unit.ETHER);
                                TrxModel trxModel = TrxModel.builder()
                                        .amount(ethBalance)
                                        .blockNumber(transaction.getBlockNumber().toString(10))
                                        .sender(transaction.getFrom())
                                        .recipient(transaction.getTo())
                                        .trxHash(transaction.getHash())
                                        .build();

                                if (typeRequest.equals(TypeRequest.RECEIVE)
                                        && trxModel.getRecipient().equals(credentials.getAddress())) {

                                    log.info("Recipient of transaction");
                                    trxes.add(trxModel);

                                } else if (typeRequest.equals(TypeRequest.SEND)
                                        && trxModel.getSender().equals(credentials.getAddress())) {

                                    log.info("Sender of transaction");
                                    trxes.add(trxModel);
                                }else{
                                    log.info("Transaction of other user");
                                }

                                log.info("******************************************");
                            }
                        }
                    }
                });

        while (!disposable.isDisposed()) {
            log.info("Wait for disposable");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error("Interrupt error due waiting for disposable, message: {}", e.getMessage());
            }
        }

        log.info("Disposable: {}, size contracts: {}, size: transactions: {}",
                disposable.isDisposed(), contracts.size(), trxes.size());

        return TrxResponse.builder()
                .contracts(contracts)
                .transactions(trxes)
                .build();
    }
}

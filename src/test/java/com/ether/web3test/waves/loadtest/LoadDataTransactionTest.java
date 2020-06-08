package com.ether.web3test.waves.loadtest;

import com.ether.web3test.model.block.items.ContractModel;
import com.ether.web3test.web3.service.account.AccountService;
import com.ether.web3test.web3.service.getter.GetterTransactionService;
import com.ether.web3test.web3.service.sender.SenderService;
import com.ether.web3test.web3.util.ConverterDataTransaction;
import com.wavesplatform.wavesj.DataEntry;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.Transaction;
import com.wavesplatform.wavesj.transactions.DataTransaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class LoadDataTransactionTest {

    @Autowired
    private SenderService senderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConverterDataTransaction converter;

    @Autowired
    private GetterTransactionService getterTransactionService;

    private static final String USER_1 = "main1";
    private static final String USER_2 = "user1";

    @Test
    public void test(){
        PrivateKeyAccount sender = accountService.generateAccount(USER_1);
        PrivateKeyAccount recipient = accountService.generateAccount(USER_2);

        List<String> hashes = new ArrayList<>();

        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++){
            ContractModel contractModel = ContractModel.builder()
                    .sender(sender.getAddress())
                    .recipient(recipient.getAddress())
                    .description("Description_" + i)
                    .data(String.valueOf(new Date().getTime()))
                    .filename("test")
                    .filepath(UUID.randomUUID().toString())
                    .extension(".txt")
                    .build();
            Collection<DataEntry<?>> dataEntries = converter.convertObject(contractModel);
            if(!dataEntries.isEmpty()){
                Transaction transaction = new DataTransaction(sender, dataEntries, 100_000, new Date().getTime());
                String trxHash = senderService.send(transaction);
                if(trxHash != null){
                    hashes.add(trxHash);
                }
            }
        }
        System.out.println("SPEND ALL: " + (System.currentTimeMillis() - start));
        System.out.println("Size result: " + hashes.size());

        try {
            Thread.sleep(20_000);
        }catch (Exception e){
            System.out.println("ERROR massage: " + e.getMessage());
        }

        List<ContractModel> contracts = new ArrayList<>();
        for (String hash : hashes) {
            Transaction byId = getterTransactionService.getById(hash);
            ContractModel convert = converter.convert(byId, ContractModel.class);
            if(convert != null){
                contracts.add(convert);
            }
        }
        System.out.println("Contracts size: " + contracts.size());
    }
}

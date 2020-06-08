package com.ether.web3test.waves.loadtest;

import com.ether.web3test.web3.model.TransferTransactionRequest;
import com.ether.web3test.web3.service.account.AccountService;
import com.ether.web3test.web3.service.balance.BalanceService;
import com.ether.web3test.web3.service.getter.GetterTransactionService;
import com.ether.web3test.web3.service.sender.SenderService;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class LoadTransactionWavesTest {

    @Autowired
    private SenderService senderService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BalanceService balanceService;

    @Autowired
    private GetterTransactionService getterTransactionService;

    private static final String seedSender = "main1";
    private static final String seedRecipient = "user1";

    List<String> ids = new ArrayList<>();

    @Test
    public void sendTransaction() {
        PrivateKeyAccount sender = accountService.generateAccount(seedSender);
        PrivateKeyAccount recipient = accountService.generateAccount(seedRecipient);

        long sendBefore = balanceService.getBalance(sender);
        long receiveBefore = balanceService.getBalance(recipient);
        System.out.println("BEFORE: Sender: " + sendBefore + ", recipient: " + receiveBefore);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            TransferTransactionRequest request = TransferTransactionRequest.builder()
                    .sender(sender)
                    .recipientAddress(recipient.getAddress())
                    .amount(100_000 + i)
                    .fee(100_000)
                    .message("Test message" + i)
                    .build();
            String transactionId = senderService.send(request);

            if(transactionId != null){
                ids.add(transactionId);
            }else{
                System.out.println("Transaction is null, index: " + i);
            }
        }
        System.out.println("SPEND TIME: " + (System.currentTimeMillis() - start));

        try {
            Thread.sleep(20_000);
        }catch (Exception e){
            System.out.println("ERROR, message: " + e.getMessage());
        }

        System.out.println();
        long sendAfter = balanceService.getBalance(sender);
        long receiveAfter = balanceService.getBalance(recipient);
        System.out.println("AFTER 2: Sender: " + sendAfter + ", recipient: " + receiveAfter);

        Map<Integer, Integer> blockIds = new HashMap<>();
        int find = 0;
        int notFind = 0;
        for(String tid: ids){
            Transaction byId = getterTransactionService.getById(tid);
            if(byId != null){
                if(blockIds.containsKey(byId.getHeight())){
                    Integer integer = blockIds.get(byId.getHeight());
                    blockIds.put(byId.getHeight(), ++integer);
                }else{
                    blockIds.put(byId.getHeight(), 1);
                }
                find++;
            }else{
                notFind++;
            }
        }
        System.out.println("Find: " + find + ", not find: " + notFind);
        System.out.println("Block size: " + blockIds.size());
        System.out.println("Blocks: " + blockIds);
    }
}

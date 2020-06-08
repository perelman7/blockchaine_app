package com.ether.web3test.web3.service.sender;

import com.ether.web3test.web3.model.TransferTransactionRequest;
import com.ether.web3test.web3.util.WavesProvider;
import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SenderServiceImpl implements SenderService{

    @Autowired
    private WavesProvider wavesProvider;

    public String send(Transaction transaction){
        String transactionId = null;
        if(transaction != null){
            Node node = wavesProvider.node();
            try {
                transactionId = node.send(transaction);
            }catch (Exception e){
                log.error("Send transaction error, message: {}", e.getMessage());
            }
        }
        return transactionId;
    }

    public String send(TransferTransactionRequest transaction){
        String transactionId = null;
        try {
            Node node = wavesProvider.node();
            transactionId = node.transfer(transaction.getSender(),
                    transaction.getRecipientAddress(), transaction.getAmount(),
                    transaction.getFee(), transaction.getMessage());
        }catch (Exception e){
            log.error("Send transaction error, message: {}", e.getMessage());
        }
        return transactionId;
    }
}

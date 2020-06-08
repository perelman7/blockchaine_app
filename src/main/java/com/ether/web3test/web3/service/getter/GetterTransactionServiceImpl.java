package com.ether.web3test.web3.service.getter;

import com.ether.web3test.web3.util.WavesProvider;
import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GetterTransactionServiceImpl implements GetterTransactionService{

    @Autowired
    private WavesProvider wavesProvider;

    public Transaction getById(String transactionId){
        Transaction transaction = null;
        if(transactionId != null){
            Node node = wavesProvider.node();
            try{
                transaction = node.getTransaction(transactionId);
            }catch (Exception e){
                log.error("Get transaction error, message: {}", e.getMessage());
            }
        }
        return transaction;
    }

    public List<Transaction> getByAddress(String address, int limit){
        List<Transaction> transactions = new ArrayList<>();
        if(address != null){
            Node node = wavesProvider.node();
            try {
                transactions = node.getAddressTransactions(address, limit);
            }catch (Exception e){
                log.error("Get transaction by address error, message: {}", e.getMessage());
            }
        }
        return transactions;
    }
}

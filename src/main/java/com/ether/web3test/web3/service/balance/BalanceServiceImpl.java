package com.ether.web3test.web3.service.balance;

import com.ether.web3test.web3.util.WavesProvider;
import com.wavesplatform.wavesj.Node;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private WavesProvider wavesProvider;

    public long getBalance(String address){
        long balance = 0;
        if(address != null){
            Node node = wavesProvider.node();
            try {
                balance = node.getBalance(address);
            }catch (Exception e){
                log.error("1. Get balance error, message: {}", e.getMessage());
            }
        }
        return balance;
    }

    public long getBalance(PrivateKeyAccount account){
        long balance = 0;
        if(account != null){
            Node node = wavesProvider.node();
            try {
                balance = node.getBalance(account.getAddress());
            }catch (Exception e){
                log.error("2. Get balance error, message: {}", e.getMessage());
            }
        }
        return balance;
    }
}

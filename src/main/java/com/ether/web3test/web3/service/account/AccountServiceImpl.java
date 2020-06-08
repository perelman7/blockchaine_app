package com.ether.web3test.web3.service.account;

import com.ether.web3test.web3.util.WavesProvider;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{

    public PrivateKeyAccount generateAccount(String seed){
        PrivateKeyAccount account = null;
        if(seed != null){
            account = PrivateKeyAccount.fromSeed(seed, 0, WavesProvider.CHAIN_ID);
        }
        return account;
    }

}

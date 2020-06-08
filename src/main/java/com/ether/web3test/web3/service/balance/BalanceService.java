package com.ether.web3test.web3.service.balance;

import com.wavesplatform.wavesj.PrivateKeyAccount;

public interface BalanceService {

    long getBalance(String address);

    long getBalance(PrivateKeyAccount account);
}

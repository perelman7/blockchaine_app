package com.ether.web3test.web3.service.account;

import com.wavesplatform.wavesj.PrivateKeyAccount;

public interface AccountService {

    PrivateKeyAccount generateAccount(String seed);
}

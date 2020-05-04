package com.ether.web3test.service.transaction;

import java.math.BigDecimal;

public interface Web3jTransactionService {

    void sendTrx(String pk, String recipientAddress, BigDecimal amount);
}

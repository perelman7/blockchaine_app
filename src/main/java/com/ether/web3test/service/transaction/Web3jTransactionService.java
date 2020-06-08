package com.ether.web3test.service.transaction;

import java.math.BigDecimal;

public interface Web3jTransactionService {

    String sendTrx(String privateKeySender, String recipientAddress, BigDecimal amount);

    String send(String pk, String recipientAddress, BigDecimal amount, int nonce);
}

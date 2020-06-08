package com.ether.web3test.web3.service.sender;

import com.ether.web3test.web3.model.TransferTransactionRequest;
import com.wavesplatform.wavesj.Transaction;

public interface SenderService {

    String send(Transaction transaction);

    String send(TransferTransactionRequest transaction);
}

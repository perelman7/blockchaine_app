package com.ether.web3test.web3.service.getter;

import com.wavesplatform.wavesj.Transaction;

import java.util.List;

public interface GetterTransactionService {

    Transaction getById(String transactionId);

    List<Transaction> getByAddress(String address, int limit);
}

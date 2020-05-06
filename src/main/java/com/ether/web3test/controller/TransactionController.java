package com.ether.web3test.controller;

import com.ether.web3test.service.transaction.Web3jTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private Web3jTransactionService web3jTransactionService;

    @PostMapping("/send")
    public ResponseEntity sendRowTransaction(String privateKeySender,
                                             String accountRecipient,
                                             BigDecimal amount) {
        web3jTransactionService.sendTrx(privateKeySender, accountRecipient, amount);
        return new ResponseEntity(HttpStatus.OK);
    }

}

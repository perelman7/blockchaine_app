package com.ether.web3test.controller;

import com.ether.web3test.model.wallet.CredentialsWallet;
import com.ether.web3test.service.wallet.Web3jWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/web3/wallet")
public class CreateWalletController {

    @Autowired
    private Web3jWalletService web3JWalletService;

    @PostMapping("/create")
    public ResponseEntity<CredentialsWallet> createWallet(String password){
        if(password != null){
            CredentialsWallet wallet = web3JWalletService.createWallet(password);
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/balance")
    public ResponseEntity<BigInteger> getBalance(String accountNumber){
        BigInteger balanceByAccountNumber = web3JWalletService.getBalanceByAccountNumber(accountNumber);
        return new ResponseEntity<>(balanceByAccountNumber, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<CredentialsWallet> getBalance(String filename, String pwd){
        CredentialsWallet credentialsWallet = web3JWalletService.getCredentialsWallet(filename, pwd);
        return new ResponseEntity<>(credentialsWallet, HttpStatus.OK);
    }

}

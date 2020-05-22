package com.ether.web3test.controller;

import com.ether.web3test.model.wallet.CredentialsWallet;
import com.ether.web3test.security.util.JwtUtil;
import com.ether.web3test.service.wallet.Web3jWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/web3/wallet")
public class WalletController {

    @Autowired
    private Web3jWalletService web3JWalletService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<CredentialsWallet> createWallet(String password) {
        if (password != null) {
            CredentialsWallet wallet = web3JWalletService.createWallet(password);
            return new ResponseEntity<>(wallet, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/balance")
    public ResponseEntity<BigInteger> getBalance(String accountNumber) {
        BigInteger balanceByAccountNumber = web3JWalletService.getBalanceByAccountNumber(accountNumber);
        return new ResponseEntity<>(balanceByAccountNumber, HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<CredentialsWallet> getBalance(String filename, String pwd) {
        CredentialsWallet credentialsWallet = web3JWalletService.getCredentialsWallet(filename, pwd);
        return new ResponseEntity<>(credentialsWallet, HttpStatus.OK);
    }

    @GetMapping("/accounts")
    public ResponseEntity<List<String>> getAllAccounts(@RequestHeader("Authorization") String jwt) {
        String currentAccount = jwtUtil.extractAccountAddress(jwt);
        List<String> allAccounts = web3JWalletService.getAllAccounts();
        allAccounts = allAccounts.stream().filter(e -> !currentAccount.equals(e)).collect(Collectors.toList());
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

}

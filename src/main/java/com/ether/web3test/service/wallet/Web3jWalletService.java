package com.ether.web3test.service.wallet;

import com.ether.web3test.model.wallet.CredentialsWallet;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public interface Web3jWalletService {

    CredentialsWallet createWallet(String password);
    CredentialsWallet getCredentialsWallet(String filename, String pwd);

    Credentials getCredentialByPrivateKey(String privateKey);
    BigInteger getBalanceByAccountNumber(String accountNumber);
}

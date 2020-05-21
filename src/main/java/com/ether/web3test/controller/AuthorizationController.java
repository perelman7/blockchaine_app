package com.ether.web3test.controller;

import com.ether.web3test.security.model.LoginRequest;
import com.ether.web3test.security.util.JwtUtil;
import com.ether.web3test.service.wallet.Web3jWalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private Web3jWalletService web3jWalletService;

    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(@RequestBody LoginRequest loginRequest) {
        if (loginRequest != null) {
            log.info("Login request: {}", loginRequest);
            String jwt = null;
            try {
                Credentials credentials = web3jWalletService
                        .getCredentialsByAccountAddressAndPassword(loginRequest.getAccountAddress(), loginRequest.getPassword());
                String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
                String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(privateKey, publicKey));
                jwt = jwtUtil.generateToken(credentials);
            } catch (Exception e) {
                log.error("Log in error, message: {}", e.getMessage());
            }
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

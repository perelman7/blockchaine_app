package com.ether.web3test.controller;

import com.ether.web3test.Test_sol_Test;
import com.ether.web3test.service.contract.SmartContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;
import java.math.BigInteger;
import java.util.Random;

@RestController
@RequestMapping("/contract")
@Slf4j
public class SmartContractController {

    @Autowired
    private SmartContractService smartContractService;

    @PostMapping("/deploy")
    public ResponseEntity<String> deploySC(String privateKey){
        String scName = smartContractService.deploySC(privateKey);
        return new ResponseEntity<>(scName, HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<BigInteger> loadSC(String hashAddress, String privateKey) throws Exception {
        Test_sol_Test test_sol_test = smartContractService.loadSC(hashAddress, privateKey);
        Random random = new Random();
        int i = random.nextInt(256);
            log.info("I : {}", i);
        test_sol_test.setAge(BigInteger.valueOf(i)).send();
        return new ResponseEntity<>(test_sol_test.getAge().send(), HttpStatus.OK);
    }
}

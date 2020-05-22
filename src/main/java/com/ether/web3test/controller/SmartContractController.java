package com.ether.web3test.controller;

import com.ether.web3test.model.block.items.ContractModel;
import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.model.contracts.request.DeploySmartContractRequest;
import com.ether.web3test.service.contract.Web3jSmartContractService;
import com.ether.web3test.service.util.SmartContractConvector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contract")
@Slf4j
public class SmartContractController {

    @Autowired
    private Web3jSmartContractService web3jSmartContractService;

    @Autowired
    private SmartContractConvector convector;

    @PostMapping("/deploy")
    public ResponseEntity<String> deploySC(@RequestBody DeploySmartContractRequest request) {
        String scName = web3jSmartContractService.deploySC(request);
        return new ResponseEntity<>(scName, HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity<ContractModel> loadSC(String hashAddress, String privateKey) {
        FileStorageContract fileStorageContract = web3jSmartContractService.loadSC(hashAddress, privateKey);
        ContractModel result = convector.convert(fileStorageContract);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

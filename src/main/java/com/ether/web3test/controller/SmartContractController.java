package com.ether.web3test.controller;

import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.service.contract.Web3jSmartContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/contract")
@Slf4j
public class SmartContractController {

    @Autowired
    private Web3jSmartContractService web3jSmartContractService;

    @PostMapping("/deploy")
    public ResponseEntity<String> deploySC(String privateKey, MultipartFile file, String description, String recipient) {
        String scName = web3jSmartContractService.deploySC(privateKey, file, description, recipient);
        return new ResponseEntity<>(scName, HttpStatus.OK);
    }

    @PostMapping("/load")
    public ResponseEntity loadSC(String hashAddress, String privateKey) throws Exception {
        FileStorageContract fileStorageContract = web3jSmartContractService.loadSC(hashAddress, privateKey);
//        String sender = fileStorageContract.getSender().send();
//        String recipient = fileStorageContract.getRecipient().send();
//        return new ResponseEntity<>(sender + " : " + recipient, HttpStatus.OK);
        byte[] content = fileStorageContract.getContent().send();
        String filename = fileStorageContract.getFilename().send();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + filename)
                .contentType(MediaType.IMAGE_JPEG)
                .body(content);
    }
}

package com.ether.web3test.service.contract;

import com.ether.web3test.model.contracts.FileStorageContract;
import org.springframework.web.multipart.MultipartFile;

public interface Web3jSmartContractService {

    String deploySC(String privateKey, MultipartFile file, String description, String recipientAddress);

    FileStorageContract loadSC(String hashAddress, String privateKey);
}

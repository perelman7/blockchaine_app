package com.ether.web3test.service.util;

import com.ether.web3test.model.block.items.ContractModel;
import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.service.util.timeconvertor.TimeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@Slf4j
public class SmartContractConvector {

    public ContractModel convert(FileStorageContract smartContract){
        ContractModel result = null;
        if(smartContract != null){
            try {
                String filename = smartContract.getFilename().send();
                String filepath = smartContract.getFilepath().send();
                String extension = smartContract.getExtension().send();

                String recipient = smartContract.getRecipient().send();
                BigInteger date = smartContract.getDate().send();
                String sender = smartContract.getSender().send();
                String description = smartContract.getDescription().send();

                result = ContractModel.builder()
                        .filename(filename)
                        .filepath(filepath)
                        .extension(extension)
                        .recipient(recipient)
                        .data(TimeConverter.convert(date))
                        .sender(sender)
                        .description(description)
                        .build();
            }catch (Exception e){
                log.error("Convert smart contract error, message: {}", e.getMessage());
            }

        }
        return result;
    }
}

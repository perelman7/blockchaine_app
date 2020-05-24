package com.ether.web3test.model.block.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractModel implements Serializable {

    private String filename;
    private String filepath;
    private String extension;
    private String description;
    private String recipient;
    private String sender;
    private String data;

}

package com.ether.web3test.model.contracts.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeploySmartContractRequest implements Serializable {

    private String privateKey;
    private String description;
    private String recipient;
    private FileDetailInfo fileInfo;
}

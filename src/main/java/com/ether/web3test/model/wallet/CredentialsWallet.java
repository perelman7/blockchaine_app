package com.ether.web3test.model.wallet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CredentialsWallet implements Serializable {

    private String accountNumber;
    private String privateKey;
    private String publicKey;
}

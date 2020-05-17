package com.ether.web3test.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtAccount implements Serializable {

    private String privateKey;
    private String publicKey;
    private String address;
}

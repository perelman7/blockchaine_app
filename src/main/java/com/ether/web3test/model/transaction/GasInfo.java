package com.ether.web3test.model.transaction;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@Builder
public class GasInfo implements Serializable {

    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private BigInteger nonce;
}

package com.ether.web3test.model.block.items;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrxModel implements Serializable {

    private String sender;
    private String recipient;
    private BigInteger amount;
    private String date;
    private String trxHash;
}


package com.ether.web3test.web3.model;

import com.wavesplatform.wavesj.PrivateKeyAccount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferTransactionRequest implements Serializable {

    private PrivateKeyAccount sender;
    private String recipientAddress;
    private long amount;
    private long fee;
    private String message;
}

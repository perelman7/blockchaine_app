package com.ether.web3test.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequestModel implements Serializable {

    private String privateKeySender;
    private String accountRecipient;
    private BigDecimal amount;
}

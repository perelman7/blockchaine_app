package com.ether.web3test.model.block;

import com.ether.web3test.model.block.items.ContractModel;
import com.ether.web3test.model.block.items.TrxModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrxResponse implements Serializable {

    private List<TrxModel> transactions;
    private List<ContractModel> contracts;
}

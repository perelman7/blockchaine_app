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
public class FileDetailInfo implements Serializable {

    private String name;
    private String path;
    private String extension;
}

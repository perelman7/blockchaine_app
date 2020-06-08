package com.ether.web3test.web3.loadtest;

import com.ether.web3test.model.contracts.FileStorageContract;
import com.ether.web3test.model.contracts.request.DeploySmartContractRequest;
import com.ether.web3test.model.contracts.request.FileDetailInfo;
import com.ether.web3test.service.contract.Web3jSmartContractService;
import com.ether.web3test.service.util.Web3jMetadataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class LoadContractTest {

    @Autowired
    private Web3jSmartContractService smartContractService;

    @Autowired
    private Web3jMetadataProvider web3jMetadataProvider;

    private static final String PK_SENDER = "5a2e9eef49ea8d45dafd3cbabf54cc2738824f5dfeebfb23fe74f22c155bc25b";
    private static final String RECIPIENT = "0x97abba3fb2cca9ff2c718772233d38580234b61b";

    @Test
    public void test() {
        List<String> ids = this.testDeploy();
        this.receiveContracts(ids);
    }

    public List<String> testDeploy() {
        List<String> ids = new ArrayList<>();

        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            long startCurrent = System.currentTimeMillis();
            String hash = smartContractService.deploySC(DeploySmartContractRequest.builder()
                    .privateKey(PK_SENDER)
                    .description("description.v_" + i)
                    .recipient(RECIPIENT)
                    .fileInfo(FileDetailInfo.builder()
                            .name("filename" + i)
                            .path(UUID.randomUUID().toString())
                            .extension(".txt")
                            .build())
                    .build());
            if (hash != null) {
                ids.add(hash);
            }
            System.out.println("Spend: " + (System.currentTimeMillis() - startCurrent) + ", Index: " + i);
        }
        System.out.println("SPEND ON DEPLOY : " + (System.currentTimeMillis() - start));
        return ids;
    }

    public void receiveContracts(List<String> ids) {
        long startMethod = System.currentTimeMillis();
        for (String id : ids) {
            FileStorageContract fileStorageContract = smartContractService.loadSC(id, PK_SENDER);
            System.out.println();
            System.out.println("Description: " + (fileStorageContract != null ? fileStorageContract.getDescription() : null));
        }
        System.out.println("Spend on getting contract: " + (System.currentTimeMillis() - startMethod));
    }
}

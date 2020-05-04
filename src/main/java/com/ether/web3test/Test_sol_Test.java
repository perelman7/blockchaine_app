package com.ether.web3test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class Test_sol_Test extends Contract {
    public static final String BINARY = "60806040523480156100115760006000fd5b50610017565b6103bb806100266000396000f3fe60806040523480156100115760006000fd5b50600436106100515760003560e01c806317d7de7c14610057578063967e6e65146100db578063c47f0027146100f9578063d5dcf127146101bc57610051565b60006000fd5b61005f6101eb565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100a05780820151818401525b602081019050610084565b50505050905090810190601f1680156100cd5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6100e3610295565b6040518082815260200191505060405180910390f35b6101ba600480360360208110156101105760006000fd5b810190808035906020019064010000000081111561012e5760006000fd5b8201836020820111156101415760006000fd5b803590602001918460018302840111640100000000831117156101645760006000fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509090919290909192905050506102a7565b005b6101e9600480360360208110156101d35760006000fd5b81019080803590602001909291905050506102c5565b005b606060006000508054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102865780601f1061025b57610100808354040283529160200191610286565b820191906000526020600020905b81548152906001019060200180831161026957829003601f168201915b50505050509050610292565b90565b600060016000505490506102a4565b90565b80600060005090805190602001906102c09291906102d5565b505b50565b8060016000508190909055505b50565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061031657805160ff1916838001178555610349565b82800160010185558215610349579182015b828111156103485782518260005090905591602001919060010190610328565b5b509050610356919061035a565b5090565b6103829190610364565b8082111561037e5760008181506000905550600101610364565b5090565b9056fea2646970667358221220beb4cbd2ca1caf1d2f91fee53baeefc629fe605b3c13cef1a907fbea9970891464736f6c63430006030033";

    public static final String FUNC_GETAGE = "getAge";

    public static final String FUNC_GETNAME = "getName";

    public static final String FUNC_SETAGE = "setAge";

    public static final String FUNC_SETNAME = "setName";

    @Deprecated
    protected Test_sol_Test(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Test_sol_Test(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Test_sol_Test(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Test_sol_Test(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> getAge() {
        final Function function = new Function(FUNC_GETAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getName() {
        final Function function = new Function(FUNC_GETNAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setAge(BigInteger newAge) {
        final Function function = new Function(
                FUNC_SETAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(newAge)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setName(String newName) {
        final Function function = new Function(
                FUNC_SETNAME, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(newName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Test_sol_Test load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Test_sol_Test(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Test_sol_Test load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Test_sol_Test(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Test_sol_Test load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Test_sol_Test(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Test_sol_Test load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Test_sol_Test(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Test_sol_Test> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Test_sol_Test.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Test_sol_Test> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Test_sol_Test.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Test_sol_Test> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Test_sol_Test.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Test_sol_Test> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Test_sol_Test.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}

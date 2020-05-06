package com.ether.web3test.model.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int64;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class FileStorageContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b506040516108b03803806108b0833981810160405260c081101561003357600080fd5b810190808051604051939291908464010000000082111561005357600080fd5b90830190602082018581111561006857600080fd5b825164010000000081118282018810171561008257600080fd5b82525081516020918201929091019080838360005b838110156100af578181015183820152602001610097565b50505050905090810190601f1680156100dc5780820380516001836020036101000a031916815260200191505b50604052602001805160405193929190846401000000008211156100ff57600080fd5b90830190602082018581111561011457600080fd5b825164010000000081118282018810171561012e57600080fd5b82525081516020918201929091019080838360005b8381101561015b578181015183820152602001610143565b50505050905090810190601f1680156101885780820380516001836020036101000a031916815260200191505b50604052602001805160405193929190846401000000008211156101ab57600080fd5b9083019060208201858111156101c057600080fd5b82516401000000008111828201881017156101da57600080fd5b82525081516020918201929091019080838360005b838110156102075781810151838201526020016101ef565b50505050905090810190601f1680156102345780820380516001836020036101000a031916815260200191505b506040526020018051604051939291908464010000000082111561025757600080fd5b90830190602082018581111561026c57600080fd5b825164010000000081118282018810171561028657600080fd5b82525081516020918201929091019080838360005b838110156102b357818101518382015260200161029b565b50505050905090810190601f1680156102e05780820380516001836020036101000a031916815260200191505b506040526020018051604051939291908464010000000082111561030357600080fd5b90830190602082018581111561031857600080fd5b825164010000000081118282018810171561033257600080fd5b82525081516020918201929091019080838360005b8381101561035f578181015183820152602001610347565b50505050905090810190601f16801561038c5780820380516001836020036101000a031916815260200191505b5060405260209081015188519093506103ab925060009189019061043e565b5084516103bf90600190602088019061043e565b5083516103d390600290602087019061043e565b5082516103e790600390602086019061043e565b50600480546001600160a01b03191633179055815161040d90600590602085019061043e565b506006805460079290920b6001600160401b03166001600160401b0319909216919091179055506104d99350505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061047f57805160ff19168380011785556104ac565b828001600101855582156104ac579182015b828111156104ac578251825591602001919060010190610491565b506104b89291506104bc565b5090565b6104d691905b808211156104b857600081556001016104c2565b90565b6103c8806104e86000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c806359016c791161005b57806359016c79146101285780635e01eb5a14610130578063776ce6a114610154578063cef2320c1461015c5761007d565b80631a092541146100825780631b88094d146100ff578063430fe9c114610107575b600080fd5b61008a610164565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100c45781810151838201526020016100ac565b50505050905090810190601f1680156100f15780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b61008a6101fa565b61010f61025b565b60408051600792830b90920b8252519081900360200190f35b61008a610264565b6101386102c4565b604080516001600160a01b039092168252519081900360200190f35b61008a6102d3565b61008a610331565b60038054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156101f05780601f106101c5576101008083540402835291602001916101f0565b820191906000526020600020905b8154815290600101906020018083116101d357829003601f168201915b5050505050905090565b60058054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156101f05780601f106101c5576101008083540402835291602001916101f0565b60065460070b90565b60018054604080516020601f600260001961010087891615020190951694909404938401819004810282018101909252828152606093909290918301828280156101f05780601f106101c5576101008083540402835291602001916101f0565b6004546001600160a01b031690565b60028054604080516020601f60001961010060018716150201909416859004938401819004810282018101909252828152606093909290918301828280156101f05780601f106101c5576101008083540402835291602001916101f0565b60008054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156101f05780601f106101c5576101008083540402835291602001916101f056fea26469706673582212208499fa64eeb5d5577a0834b365240bf3c72f243a6117e5e4a276f7ab6e6e7dbb64736f6c63430006070033";

    public static final String FUNC_GETCONTENT = "getContent";

    public static final String FUNC_GETDATE = "getDate";

    public static final String FUNC_GETDESCRIPTION = "getDescription";

    public static final String FUNC_GETEXTENSION = "getExtension";

    public static final String FUNC_GETFILENAME = "getFilename";

    public static final String FUNC_GETRECIPIENT = "getRecipient";

    public static final String FUNC_GETSENDER = "getSender";

    @Deprecated
    protected FileStorageContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected FileStorageContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected FileStorageContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected FileStorageContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<byte[]> getContent() {
        final Function function = new Function(FUNC_GETCONTENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getDate() {
        final Function function = new Function(FUNC_GETDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int64>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getDescription() {
        final Function function = new Function(FUNC_GETDESCRIPTION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getExtension() {
        final Function function = new Function(FUNC_GETEXTENSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getFilename() {
        final Function function = new Function(FUNC_GETFILENAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getRecipient() {
        final Function function = new Function(FUNC_GETRECIPIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> getSender() {
        final Function function = new Function(FUNC_GETSENDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    @Deprecated
    public static FileStorageContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new FileStorageContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static FileStorageContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new FileStorageContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static FileStorageContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new FileStorageContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static FileStorageContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new FileStorageContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<FileStorageContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _filename, byte[] _content, String _extension, String _description, String _recipient, BigInteger _date) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_filename), 
                new org.web3j.abi.datatypes.DynamicBytes(_content), 
                new org.web3j.abi.datatypes.Utf8String(_extension), 
                new org.web3j.abi.datatypes.Utf8String(_description), 
                new org.web3j.abi.datatypes.Utf8String(_recipient), 
                new org.web3j.abi.datatypes.generated.Int64(_date)));
        return deployRemoteCall(FileStorageContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<FileStorageContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _filename, byte[] _content, String _extension, String _description, String _recipient, BigInteger _date) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_filename), 
                new org.web3j.abi.datatypes.DynamicBytes(_content), 
                new org.web3j.abi.datatypes.Utf8String(_extension), 
                new org.web3j.abi.datatypes.Utf8String(_description), 
                new org.web3j.abi.datatypes.Utf8String(_recipient), 
                new org.web3j.abi.datatypes.generated.Int64(_date)));
        return deployRemoteCall(FileStorageContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<FileStorageContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _filename, byte[] _content, String _extension, String _description, String _recipient, BigInteger _date) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_filename), 
                new org.web3j.abi.datatypes.DynamicBytes(_content), 
                new org.web3j.abi.datatypes.Utf8String(_extension), 
                new org.web3j.abi.datatypes.Utf8String(_description), 
                new org.web3j.abi.datatypes.Utf8String(_recipient), 
                new org.web3j.abi.datatypes.generated.Int64(_date)));
        return deployRemoteCall(FileStorageContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<FileStorageContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _filename, byte[] _content, String _extension, String _description, String _recipient, BigInteger _date) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_filename), 
                new org.web3j.abi.datatypes.DynamicBytes(_content), 
                new org.web3j.abi.datatypes.Utf8String(_extension), 
                new org.web3j.abi.datatypes.Utf8String(_description), 
                new org.web3j.abi.datatypes.Utf8String(_recipient), 
                new org.web3j.abi.datatypes.generated.Int64(_date)));
        return deployRemoteCall(FileStorageContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}

package io.lamden.blockchain;

import io.lamden.api.MasterNodeApi;
import io.lamden.api.Network;
import io.lamden.api.datatypes.GenericValue;
import io.lamden.api.json.constitution.Constitution;
import io.lamden.api.json.contract.ContractInfoResult;
import io.lamden.api.json.contract.Contracts;
import io.lamden.api.json.method.MethodsResult;
import io.lamden.api.json.transaction.TransactionResult;

import java.math.BigDecimal;

/**
 * A client to interact with the Lamden blockchain
 *
 * This client can be used to interact with the Lamden blockchain.
 * It uses several endpoints provided by the masternodes.
 */
public class LamdenBlockChain {

    private Network network;

    /**
     * Creates an client instance to interact with a particular network (mainnet, testnet, e.g.)
     * @param network the desired <tt>network</tt>
     */
    public LamdenBlockChain(Network network) {
        this.network = network;
    }

    /**
     * Creates a <tt>TransactionSender</tt> object, which will be used to send signed transactions
     * to the blockchain
     * @param txInfo the <tt>TransactionInfo</tt> to be sent
     * @return the created <tt>TransactionSender</tt>
     */
    public TransactionSender createTransactionSender(TransactionInfo txInfo){
        return new TransactionSender(this.network, txInfo);
    }

    /**
     * Reads a transaction from the blockchain
     * @param hash the transaction hash
     * @return the <tt>TransactionResult</tt> including the whole transaction data
     */
    public TransactionResult readTransaction(String hash){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readTransaction(hash);
    }

    /**
     * Reads the code of a smart contract from the blockchain
     * @param contractName name of the contract
     * @return the <tt>ContractInfoResult</tt> including the whole contract code
     */
    public ContractInfoResult readContractInfo(String contractName){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readContractInfo(contractName);
    }

    /**
     * Reads a variable of a smart contract from the blochchain
     * @param contractName name of the contract
     * @param variableName name of the variable
     * @return the <tt>GenericValue</tt> if the read operation was successful.
     * If the contract or the variable was not found <tt>null</tt> will be returned
     */
    public GenericValue readVariable(String contractName, String variableName){
        return readVariable(contractName, variableName, GenericValue.class);
    }

    /**
     * Reads a variable of a smart contract from the blochchain
     * @param contractName name of the contract
     * @param variableName name of the variable
     * @param resultType type to be used for deserialization
     * @return the <tt>resultType</tt> if the read operation was successful.
     * If the contract or the variable was not found <tt>null</tt> will be returned
     */
    public <T extends GenericValue> T readVariable(String contractName, String variableName, Class<T> resultType){
        return readVariable(contractName, variableName, null, resultType);
    }

    /**
     * Reads a variable of a smart contract from the blochchain
     * @param contractName name of the contract
     * @param variableName name of the variable
     * @param params name of the param
     * @return the <tt>GenericValue</tt> if the read operation was successful.
     * If the contract, the variable or the param was not found <tt>null</tt> will be returned
     */
    public GenericValue readVariable(String contractName, String variableName, String params){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readVariable(contractName, variableName, params, GenericValue.class);
    }

    /**
     * Reads a variable of a smart contract from the blochchain
     * @param contractName name of the contract
     * @param variableName name of the variable
     * @param params name of the param
     * @param resultType type to be used for deserialization
     * @return the <tt>resultType</tt> if the read operation was successful.
     * If the contract, the variable or the param was not found <tt>null</tt> will be returned
     */
    public <T extends GenericValue> T readVariable(String contractName, String variableName, String params, Class<T> resultType){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readVariable(contractName, variableName, params, resultType);
    }

    /**
     * Reads all methods and the according parameters of a smart contract from the blockchain
     * @param contractName name of the contract
     * @return the <tt>MethodsResult</tt> if the read operation was successful.
     * If the contract was not found, <tt>null</tt> will be returned
     */
    public MethodsResult readContractMethods(String contractName){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readContractMethods(contractName);
    }

    /**
     * Pings the network to see if the network is up
     * @return <tt>true</tt> if the network is up, <tt>false</tt> if the network is down
     */
    public boolean pingServer(){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.pingServer();
    }

    /**
     * Reads the ids of all masternodes and delegates currently participating in consensus.
     * @return <tt>true</tt> if the network is up, <tt>false</tt> if the network is down
     */
    public Constitution readConstitution(){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readConstitution();
    }

    /**
     * Returns all the smartcontracts on the Lamden blockchain
     * @return all <tt>Contracts</tt> with their name
     */
    public Contracts readContracts(){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readContracts();
    }

    /**
     * This is a wrapper method to read the balance of a specified publicKey
     * from the currency smart contract.
     * @param publicKey the publicKey to check
     * @return the amount of the publicKey if it was found on the currency contract, otherwise
     * <tt>null</tt>
     */
    public BigDecimal readCurrencyBalance(String publicKey){
        MasterNodeApi masterNodeApi = new MasterNodeApi(network);
        return masterNodeApi.readCurrencyBalance(publicKey);
    }

}

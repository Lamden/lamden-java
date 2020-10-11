package io.lamden;

import io.lamden.api.MainNet;
import io.lamden.api.Network;
import io.lamden.api.TestNet;
import io.lamden.api.datatypes.*;
import io.lamden.api.json.constitution.Constitution;
import io.lamden.api.json.contract.ContractInfoResult;
import io.lamden.api.json.contract.Contracts;
import io.lamden.api.json.method.MethodsResult;
import io.lamden.api.json.transaction.TransactionResult;
import io.lamden.blockchain.LamdenBlockChain;
import io.lamden.blockchain.TransactionInfo;
import io.lamden.blockchain.TransactionSender;
import io.lamden.exception.MasternodesNotAvailableException;
import io.lamden.wallet.KeyPair;
import io.lamden.wallet.LamdenWallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.util.*;

class LamdenBlockChainTest {
    
    private final Network mainnet = new MainNet();

    @Test
    void readTransactionResult_existing_returnsResult(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String txHash = "f9b274876486e16a55e3507079072003fd57593f05b7be2502c5b1c762203df6";

        //Act
        TransactionResult txResult = testee.readTransaction(txHash);

        //Assert
        Assertions.assertEquals(txHash, txResult.getHash());
    }

    @Test
    void readTransactionResult_nonExisting_returnsNull(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String txHash = "000000000086e16a55e3507079072003fd57593f05b7be2502c5b1c762203df6";

        //Act
        TransactionResult txResult = testee.readTransaction(txHash);

        //Assert
        Assertions.assertNull(txResult);
    }

    @Test
    void readContractInfo_existing_returnsResult(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String contractName = "currency";

        //Act
        ContractInfoResult contractInfoResult = testee.readContractInfo(contractName);

        //Assert
        Assertions.assertEquals(contractName, contractInfoResult.getName());
    }

    @Test
    void readContractInfo_nonExisting_returnsNull(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);

        //Act
        ContractInfoResult contractInfoResult = testee.readContractInfo("currrrrrency");

        //Assert
        Assertions.assertNull(contractInfoResult);
    }

    @Test
    void readVariableWithoutParams_existing_returnsResult() throws Exception{
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String contractName = "con_hopium";
        String variable = "distributionAmount";

        //Act
        FloatValue result = testee.readVariable(contractName, variable, FloatValue.class);

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getValue().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void readVariable_nonExisting_returnsNull(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String contractName = "con_hopium";
        String variable = "misterx";

        //Act
        GenericValue<String> result = testee.readVariable(contractName, variable);

        //Assert
        Assertions.assertNull(result);
    }

    @Test
    void readVariableWithParams_existing_returnsResult(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String contractName = "currency";
        String variable = "balances";
        String address = "0000803efd5df09c75c0c6670742db5074e5a011b829dfd8a0c50726d263a345";

        //Act
        GenericValue result = testee.readVariable(contractName, variable, address);


        //Assert
        Assertions.assertNotNull(result);
        String amount = ((Map<String, String>) result.getValue()).get("__fixed__");
        Assertions.assertTrue(Double.parseDouble(amount) > 0);
    }

    @Test
    void readContractMethods_existing_returnsResult(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String contractName = "currency";

        //Act
        MethodsResult result = testee.readContractMethods(contractName);

        //Assert
        Assertions.assertEquals(5, result.getMethods().size());
    }

    @Test
    void readContractMethods_nonExisting_returnsNull(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);
        String contractName = "currrrrrrency";

        //Act
        MethodsResult result = testee.readContractMethods(contractName);

        //Assert
        Assertions.assertNull(result);
    }

    @Test
    void pingServer_existing_returnsTrue(){
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);

        //Act
        boolean result = testee.pingServer();

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    void readContractInfo_allWrongHosts_throwsException() throws Exception{
        //Arrange
        Network myNetwork = new MainNet();
        myNetwork.setMasterNodes(
                Arrays.asList(
                        new URI("https://masternode-01.lambo.io"),
                        new URI("https://masternode-02.lambo.io"),
                        new URI("https://masternode-03.lambo.io")
                        )
        );

        LamdenBlockChain testee = new LamdenBlockChain(myNetwork);
        String contractName = "currency";

        //Act
        MasternodesNotAvailableException exception = Assertions.assertThrows(MasternodesNotAvailableException.class, () -> {
            testee.readContractInfo(contractName);
        });

        //Assert
        Assertions.assertTrue(exception.getMessage().endsWith(" could not be processed despite 2 retries!"));

    }

    @Test
    void readCurrencyBalance_existingWallet_returnsAmount() {
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);

        //Act
        BigDecimal result = testee.readCurrencyBalance("0000803efd5df09c75c0c6670742db5074e5a011b829dfd8a0c50726d263a345");

        //Assert
        Assertions.assertTrue(result.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void readCurrencyBalance_nonExistingWallet_returnsNull() {
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);

        //Act
        BigDecimal result = testee.readCurrencyBalance("000000000000009c75c0c6670742db5074e5a011b829dfd8a0c50726d263a345");

        //Assert
        Assertions.assertNull(result);
    }

    @Test
    void readConstitution_returnsValidResponse() {
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);

        //Act
        Constitution result = testee.readConstitution();

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getMasternodes().size() > 0);
        Assertions.assertTrue(result.getDelegates().size() > 0);

    }

    @Test
    void readContracts_returnsAllContracts() {
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(mainnet);

        //Act
        Contracts result = testee.readContracts();

        //Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getContracts().size() > 0);
    }


    @Test
    void readVariables_ofAllTypes_returnsResult() throws Exception{
        //Arrange
        LamdenBlockChain testee = new LamdenBlockChain(new TestNet());
        String contractName = "con_values_testing";

        //Test - String
        GenericValue genericValueString = testee.readVariable(contractName, "S", "lamden-java-testing,Str");
        Assertions.assertEquals("a normal string", genericValueString.getValue().toString());

        //Test - String
        StringValue stringValue = testee.readVariable(contractName, "S", "lamden-java-testing,Str", StringValue.class);
        Assertions.assertEquals("a normal string", stringValue.getValue());

        //Test - Bool
        GenericValue genericValueBool = testee.readVariable(contractName, "S", "lamden-java-testing,Bool");
        Assertions.assertEquals(true, Boolean.valueOf(genericValueBool.getValue().toString()));

        //Test - Bool
        BooleanValue booleanValue = testee.readVariable(contractName, "S", "lamden-java-testing,Bool", BooleanValue.class);
        Assertions.assertEquals(true, booleanValue.getValue());

        //Test - Map
        GenericValue genericValueMap = testee.readVariable(contractName, "S", "lamden-java-testing,Dict");
        Assertions.assertTrue(((Map<String, Object>)genericValueMap.getValue()).containsKey("item1"));

        //Test - Map
        MapValue mapValue = testee.readVariable(contractName, "S", "lamden-java-testing,Dict", MapValue.class);
        Assertions.assertTrue(mapValue.getValue().containsKey("item1"));

        //Test - Float
        GenericValue genericValueFloat = testee.readVariable(contractName, "S", "lamden-java-testing,Float");
        Assertions.assertTrue(((Map<String, Object>)genericValueFloat.getValue()).containsKey("__fixed__"));

        //Test - Float
        FloatValue floatValue = testee.readVariable(contractName, "S", "lamden-java-testing,Float", FloatValue.class);
        Assertions.assertTrue(floatValue.getValue().compareTo(BigDecimal.ZERO) > 0);

        //Test - Int
        GenericValue genericValueInt = testee.readVariable(contractName, "S", "lamden-java-testing,Int");
        Assertions.assertEquals(42, Integer.valueOf(genericValueInt.getValue().toString()));

        //Test - Int
        IntValue intValue = testee.readVariable(contractName, "S", "lamden-java-testing,Int", IntValue.class);
        Assertions.assertEquals(42, intValue.getValue());

        //Test - List
        GenericValue genericValueList = testee.readVariable(contractName, "S", "lamden-java-testing,List");
        Assertions.assertEquals("item1", ((List<String>)genericValueList.getValue()).get(0));

        //Test - List
        ListValue<String> listValue = testee.readVariable(contractName, "S", "lamden-java-testing,List", ListValue.class);
        Assertions.assertEquals("item1", listValue.getValue().get(0));

        //Test - DateTime
        GenericValue genericValueDateTime = testee.readVariable(contractName, "S", "lamden-java-testing,DateTime");
        Assertions.assertEquals(6, ((ArrayList<Integer>)genericValueDateTime.getValue()).size());

        //Test - DateTime
        DateTimeValue dateTimeValue = testee.readVariable(contractName, "S", "lamden-java-testing,DateTime", DateTimeValue.class);
        Assertions.assertEquals(2020, dateTimeValue.getValue().getYear());

        //Test - TimeDelta
        GenericValue genericValueTimedelta = testee.readVariable(contractName, "S", "lamden-java-testing,TimeDelta");
        Assertions.assertEquals(7, ((ArrayList<Integer>)genericValueTimedelta.getValue()).size());

        //Test - TimeDelta
        TimeDeltaValue timeDeltaValue = testee.readVariable(contractName, "S", "lamden-java-testing,TimeDelta", TimeDeltaValue.class);
        Assertions.assertEquals(1, timeDeltaValue.getValue().getWeeks());

    }

    @Test
    @Disabled("Cannot send transaction because of missing funds on testnet")
    void sendTransaction() {
        LamdenWallet testee = new LamdenWallet();
        KeyPair keyPair = testee.generateKeyPairFromPrivateKey("c3a542e8a03067781f6b1352bf9b3fbeb65c6fa54cf0da5b1815a477ea656147");
        //publicKey: b30aa7639eb57a084e82746c338ec663053f976c5c70ff605d37157649c874bb
        String publicKey = keyPair.getPublicKey();


        Map<String, Object> args = new HashMap<>();
        args.put("Str", new StringValue("a normal string"));
        args.put("Bool", new BooleanValue(true));
        args.put("DateTime", new DateTimeValue(2020, 6, 28, 19, 16, 35));
        args.put("TimeDelta", new TimeDeltaValue(1,4,12, 8, 13,30,199));

        Map<String, Object> dictMap = new HashMap<>();
        dictMap.put("item1", Collections.singletonMap("item2", "value2"));
        args.put("Dict", new MapValue(dictMap));

        args.put("Float", new FloatValue(new BigDecimal("3.1415")));
        args.put("Int", new IntValue(42));
        args.put("ANY", new StringValue("it's me"));

        List<String> stringList = Arrays.asList("item1", "item2");
        args.put("List", new ListValue(stringList));

        args.put("UID", new StringValue("lamden-java-testing"));


        TransactionInfo tx = TransactionInfo.builder()
                .senderPublicKey(publicKey)
                .contractName("con_values_testing")
                .methodName("test_values")
                .stampLimit(200)
                .args(args)
                .build();


        LamdenBlockChain blockchain = new LamdenBlockChain(new TestNet());
        TransactionSender txBuilder = blockchain.createTransactionSender(tx);
        String txHash = txBuilder.send(keyPair.getPrivateKey());


        Assertions.assertNotNull(txHash);
    }

}
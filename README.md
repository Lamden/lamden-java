# lamden-java
This is a java implementation of the lamden-js library.
It interacts directly with the Lamden blockchain.

The features of this java library are:
* creating wallets
* signing and verifying messages
* sending transactions 
* reading smart contracts and variables
* reading balances

Masternode querying
* masternodes are accessed with round robin
* if a masternode is not healthy it will be marked and skipped for future request
* you can customize the timespan and number of retries until a masternode is marked as unhealthy

# Getting started

Add the lamden-java library as a dependency

For maven
```xml
<dependency>
    <groupId>io.lamden</groupId>
    <artifactId>lamden-java</artifactId>
    <version>not yet released to maven central</version>
</dependency>
```

For gradle
```
dependencies {
    implementation 'io.lamden:lamden-java:no yet released to maven central'  
}
```

## Create a new wallet
```java
LamdenWallet wallet = new LamdenWallet();
KeyPair keyPair = wallet.generateKeyPair();
System.out.println("Private Key: " + keyPair.getPrivateKey());
System.out.println("Public Key: " + keyPair.getPublicKey());
```

## Sign and verify a message
```java
String message = "this is my message i want to sign";
String signature = wallet.signMessage(keyPair.getPrivateKey(), message);
boolean verificationResult = wallet.verifyMessage(keyPair.getPublicKey(), message, signature);
```

## Interacting with the blockchain

### Datatypes

When interacting with the Lamden blockchain there are several supported datatypes:

| Datatype  	| Java Class     	|
|-----------	|----------------	|
| *all      	| GenericValue   	|
| boolean   	| BooleanValue   	|
| datetime  	| DateTimeValue  	|
| float     	| FloatValue     	|
| int       	| IntValue       	|
| list      	| ListValue      	|
| map       	| MapValue       	|
| string    	| StringValue    	|
| timedelta 	| TimeDeltaValue 	|

\* The `GenericValue` Class can be used to use raw json values for communication.

**It's recommended to use the appropriate `Value`-class to ensure correct marshalling/unmarshalling of the different datatypes.**

### Let's prepare for the first interaction 

First you need to define, which network you want to interact to. 
Therefore you need to create a `Network` instance first.
Actually there are two predefined network classes with the current active masternodes defined: `MainNet` and `TestNet`

In the following examples the `MainNet` instance will be used.

```java
Network mainnet = new MainNet();
LamdenBlockChain blockchain = new LamdenBlockChain(mainnet);
```

By default the `MainNet` and `TestNet` instances have the following settings:
* retriesUntilUnhealthy = 5
* healthCheckTimeSpanSeconds = 300

This means that if a masternode is not accessible more than 5 times within 300 seconds, it will be marked as unhealthy.
The affected masternode will not get any further requests.

You can customize these parameters by calling the appropriate constructor:
```java
Network mainnet = new MainNet(2, 180);
LamdenBlockChain blockchain = new LamdenBlockChain(mainnet);
```

You should only create a single instance of `MainNet` or `TestNet` and always reuse the same instance.
Otherwise you will lose the state of the masternodes health. 


### Reading Transactions

```java
String txHash = "f9b274876486e16a55e3507079072003fd57593f05b7be2502c5b1c762203df6";
TransactionResult txResult = blockchain.readTransaction(txHash);
```

### Read all available smart contracts

Returns all the smart contracts on the Lamden blockchain

```java
Contracts result = blockchain.readContracts();
```

### Reading all available methods and parameter of smart contracts

Returns all exported methods from a smart contract including each method's argument name and type

```java
String contractName = "currency";
Methods result = blockchain.readContractMethods(contractName);
```

### Reading all available variables and hashes of a smart contract

Returns all state variables and hashes defined in a smart contract

```java
String contractName = "currency";
Variables result = blockchain.readContractVariables(contractName);
```

### Reading python code of a smart contract

Returns a contract's name and python code.

```java
String contractName = "currency";
ContractInfo contractInfo = blockchain.readContractInfo(contractName);
```

### Reading untyped variable of smart contracts

Returns the generic value (untyped) of a smart contract variable

```java
String contractName = "con_hopium";
String variable = "distributionAmount";
GenericValue result = blockchain.readVariable(contractName, variable);
```

### Reading typed variable of smart contracts

Returns the float value (typed) of a smart contract variable.

```java
String contractName = "con_hopium";
String variable = "distributionAmount";
FloatValue result = blockchain.readVariable(contractName, variable, FloatValue.class);
```

### Reading typed variable with params of smart contracts

Returns the float value (typed) for a key in a hash variable on the smart contract

```java
String contractName = "currency";
String variable = "balances";
String address = "0000803efd5df09c75c0c6670742db5074e5a011b829dfd8a0c50726d263a345";
FloatValue result = blockchain.readVariable(contractName, variable, address, FloatValue.class);
```

### Read the current balance of a public key

This is a convenience method to read the balance of a public key from the `currency` contract

```java
String publicKey = "0000803efd5df09c75c0c6670742db5074e5a011b829dfd8a0c50726d263a345";
BigDecimal result = blockchain.readCurrencyBalance(publicKey);
```


### Ping the network to check if it's up and running

Check if at least one masternode is online and the network is up 

```
boolean result = blockchain.pingServer();
```

### Read network constitution

Returns the ids of all masternodes and delegates currently participating in consensus.

```
Constitution result = blockchain.readConstitution();
```

### Sending a signed transaction to the network

To make this code running, you need to fund the generated public key with some dTAU (Testnet TAU) first.
Copy the generated public key `b30aa7639eb57a084e82746c338ec663053f976c5c70ff605d37157649c874bb` and 
submit it to the [Lamden Faucet](https://faucet.lamden.io)

The following code will call the `currency` contract and send 5 dTAU from `b30aa7639eb57a084e82746c338ec663053f976c5c70ff605d37157649c874bb` to `919c1cb656873574ccbe4cfbaf968a480140bc4b7c6d3bc2531f17fb272322a6`

```java
LamdenWallet wallet = new LamdenWallet();
KeyPair keyPair = wallet.generateKeyPairFromPrivateKey("c3a542e8a03067781f6b1352bf9b3fbeb65c6fa54cf0da5b1815a477ea656147");
String publicKey = keyPair.getPublicKey();
//generated publicKey: b30aa7639eb57a084e82746c338ec663053f976c5c70ff605d37157649c874bb

// Set all the arguments needed for the "transfer" method on the "currency" contract
Map<String, Object> args = new HashMap<>();
args.put("to", new StringValue("919c1cb656873574ccbe4cfbaf968a480140bc4b7c6d3bc2531f17fb272322a6"));
args.put("amount", new IntValue(5));

// Create the appropriate TransactionInfo object
TransactionInfo tx = TransactionInfo.builder()
        .senderPublicKey(publicKey)
        .contractName("currency")
        .methodName("transfer")
        .stampLimit(200)
        .args(args)
        .build();

// Create a blockchain instance bound to the testnet
LamdenBlockchain blockchain = new LamdenBlockchain(new TestNet());

// Sign the transaction with the private key and send it
// If everything is working the transaction hash will be returned
TransactionSender txSender = blockchain.createTransactionSender(tx);
String txHash = txSender.send(keyPair.getPrivateKey());
```
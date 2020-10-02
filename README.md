# lamden-java
This is a java implementation of the lamden-js library.
It interacts directly with the Lamden blockchain.

The features of this java library are:
* creating wallets
* signing and verifying messages
* sending transactions 
* reading smart contracts and variables
* reading balances

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
boolean verificationResult = testee.verifyMessage(keyPair.getPublicKey(), message, signature);
```

## Interacting with the blockchain

First you need to defined, with which network you want to interact
Therefore you need create a `Network` instance first.
Actually there are two predefined network classes with the current active masternodes defined: `MainNet` and `TestNet`

In the following examples the `MainNet` instance will be used.

```java
Network mainnet = new MainNet();
LamdenBlockChain blockchain = new LamdenBlockChain(mainnet);
```

### Reading Transactions

```java
String txHash = "f9b274876486e16a55e3507079072003fd57593f05b7be2502c5b1c762203df6";
TransactionResult txResult = blockchain.readTransaction(txHash);
```

### Reading smart contracts

```java
String contractName = "currency";
ContractInfoResult contractInfoResult = blockchain.readContractInfo(contractName);
```

### Reading untyped variable of smart contracts

```java
String contractName = "con_hopium";
String variable = "distributionAmount";
ValueResult result = blockchain.readVariable(contractName, variable);
```

### Reading typed variable of smart contracts

```java
String contractName = "con_hopium";
String variable = "distributionAmount";
FloatValue result = blockchain.readVariable(contractName, variable, FloatValue.class);
```

### Reading typed variable with params of smart contracts

```java
String contractName = "currency";
String variable = "balances";
String address = "0000803efd5df09c75c0c6670742db5074e5a011b829dfd8a0c50726d263a345";
FloatValue result = blockchain.readVariable(contractName, variable, address, FloatValue.class);
```

### Reading all available methods and parameter of smart contracts

```java
String contractName = "currency";
MethodsResult result = blockchain.readContractMethods(contractName);
```

### Read the current balance of a public key

```java
String publicKey = "0000803efd5df09c75c0c6670742db5074e5a011b829dfd8a0c50726d263a345";
BigDecimal result = blockchain.readCurrencyBalance(publicKey);
```


### Ping the network to check if it's up and running

```
boolean result = blockchain.pingServer();
```

### Sending a signed transaction to the network

To make this code running, you need to fund the generated public key with some dTAU (Testnet TAU) first.
Copy the generated public key `b30aa7639eb57a084e82746c338ec663053f976c5c70ff605d37157649c874bb` and 
submit it to the [Lamden Faucet](https://faucet.lamden.io)

This code will call the `currency` contract and send 5 dTAU to `919c1cb656873574ccbe4cfbaf968a480140bc4b7c6d3bc2531f17fb272322a6`

```java
LamdenWallet testee = new LamdenWallet();
KeyPair keyPair = testee.generateKeyPairFromPrivateKey("c3a542e8a03067781f6b1352bf9b3fbeb65c6fa54cf0da5b1815a477ea656147");
String publicKey = keyPair.getPublicKey();
//generated publicKey: b30aa7639eb57a084e82746c338ec663053f976c5c70ff605d37157649c874bb

// Set all the arguments needed for the "transfer" method on the "currency" contract
Map<String, Object> args = new HashMap<>();
args.put("to", "919c1cb656873574ccbe4cfbaf968a480140bc4b7c6d3bc2531f17fb272322a6");
args.put("amount", 5);

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
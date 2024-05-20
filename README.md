# Cardano CLI Java Toolkit
###### _This Java toolkit provides functionality to interact with Cardano's command-line interface (CLI) for various tasks including wallet creation and transaction management. It simplifies the process for Java developers to integrate Cardano functionalities into their applications._

## Requirements
- Java 17 and higher
- Cardano CLI installed
- Access to a Cardano node with the appropriate socket path

## Installation
- Clone this repository to your local machine.

## Usage
### Cardano Wallet address Creator
The `BuildAddress` class facilitates the creation of Cardano wallet's addresses. It generates a private key, payment keys and addresses, and saves them to files.

Usage:
```sh
BuildAddress buildAddress = new BuildAddress();
buildAddress.addressGen();
```

# Cardano Transaction Toolkit

This toolkit provides Java classes for facilitating Cardano transactions by interacting with the Cardano CLI. It offers methods for querying protocol parameters, building transactions, signing transactions, and submitting transactions to the Cardano network.

## Usage
```sh
CardanoJBuildTransaction cardanoJBuildTransaction = new CardanoJBuildTransaction();
cardanoJBuildTransaction.transact();
```

```sh
cardanoJBuildTransaction.transactionSession();
```
```sh
CardanoJTransaction tr = new CardanoJTransaction();
```
# Creating Datum
```sh
CardanoJCreateDatum dat = new CardanoJCreateDatum();
String datum = dat.create(senderAddress,receiverAddress,lovelace,resourcePath);
```
# Building the transaction
```sh
tr.buildTransaction(cliPath,resourcePath,senderAddress,receiverAddress,network,lovelace,senderName,datum);
```
# Sign transaction
```sh
tr.signTransaction(cliPath,resourcePath,network,senderName);
```
# Submit transaction
```sh
String result = tr.submitTransaction(cliPath,resourcePath,network,senderName);
```
# Transaction validation
```sh
System.out.println("Cardanoscan: https://preview.cardanoscan.io/transaction/" + result);
```


> Note: _Customize paths and parameters according to your Cardano setup.
Ensure the Cardano CLI executable is available in the specified path.
For Unix/Linux/MacOs users, the program gives executable permissions to cardano-cli if necessary._

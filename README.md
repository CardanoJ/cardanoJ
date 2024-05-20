# Cardano CLI Java Toolkit
###### _This Java toolkit provides functionality to interact with Cardano's command-line interface (CLI) for various tasks including wallet creation and transaction management. It simplifies the process for Java developers to integrate Cardano functionalities into their applications._

## Requirements
- Java 17 and higher
- Cardano CLI installed
- Access to a Cardano node with the appropriate socket path

## Installation
- Clone this repository to your local machine.

## Usage
### Cardano Wallet Creator
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
TransactionCollect trancationCollect = new TransactionCollect();
```
```sh
// Query protocol parameters
String protocolParamFile = trancationCollect.queryProtocolParam(cliPath, resourcePath, network, socketPath);
```
```sh
// Build transaction
String bodyFile = trancationCollect.buildTransaction(cliPath, resourcePath, senderAddress, receiverAddress, network, lovelace, senderName, datumValue, socketPath);
```
```sh
// Sign transaction
trancationCollect.signTransaction(cliPath, resourcePath, network, receiverName);
```
```sh
// Submit transaction
String transactionID = trancationCollect.submitTransaction(cliPath, resourcePath, network, senderName);
```
> Note: _Customize paths and parameters according to your Cardano setup.
Ensure the Cardano CLI executable is available in the specified path.
For Unix/Linux/MacOs users, the program gives executable permissions to cardano-cli if necessary._

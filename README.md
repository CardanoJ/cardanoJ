# Cardano CLI Java Toolkit
*__This Java toolkit provides functionality to interact with Cardano's command-line interface (CLI) for various tasks including wallet creation and transaction management. It simplifies the process for Java developers to integrate Cardano functionalities into their applications.__*

## CardanoJ Code and Test Cases
[CardanoJ Code and Test Cases](https://www.youtube.com/watch?v=xklcwo6_GHk)

## Requirements
- Java 17 and higher
- Cardano CLI installed
- Access to a Cardano node with the appropriate socket path

## Installation
_- Clone this repository to your local machine._

## Usage
### Cardano Wallet address Creator
_The `BuildAddress` class facilitates the creation of Cardano wallet's addresses. It generates a private key, payment keys and addresses, and saves them to files._

**Usage:**
```sh
BuildAddress buildAddress = new BuildAddress();
buildAddress.addressGen();
```

# Cardano Transaction Toolkit

_This toolkit provides Java classes for facilitating Cardano transactions by interacting with the Cardano CLI. It offers methods for querying protocol parameters, building transactions, signing transactions, and submitting transactions to the Cardano network._

**Example:**
```sh
CardanoJCreateDatum dat = new CardanoJCreateDatum();
String datum = dat.create(senderAddress, receiverAddress, lovelace, resourcePath);
```
## Building the Transaction
_Construct a transaction with specified parameters._

**Example:**
```sh
CardanoJTransaction tr = new CardanoJTransaction();
tr.buildTransaction(cliPath, resourcePath, senderAddress, receiverAddress, network, lovelace, senderName, datum);
```
## Signing the Transaction
_Sign the constructed transaction._

**Example:**
```sh
tr.signTransaction(cliPath, resourcePath, network, senderName);
```
## Submitting the Transaction
_Submit the signed transaction to the Cardano network._

**Example:**
```sh
String result = tr.submitTransaction(cliPath, resourcePath, network, senderName);
```
## Transaction Validation
_Validate and check the transaction on Cardanoscan._

**Example:**
```sh
System.out.println("Cardanoscan: https://preview.cardanoscan.io/transaction/" + result);
```

## Notes
- Customize paths and parameters according to your Cardano setup.
- Ensure the Cardano CLI executable is available in the specified path.
- For Unix/Linux/MacOS users, the program can give executable permissions to cardano-cli if necessary.

# Example Code
```sh
package com.cardanoJ;

import com.cardanoJ.transaction.CardanoJBuildTransaction;

public class Main {
    public static void main(String[] args) {
        CardanoJBuildTransaction cardanoJBuildTransaction = new CardanoJBuildTransaction();
        cardanoJBuildTransaction.transact();
    }
}
```

## Classes and Methods

### `CardanoJBuildTransaction`
_This class handles the core functionality for querying, building, signing, and submitting transactions._

### Methods:
- **query() -** _Queries the balance of an address._
- **transactionSession() -** _Manages the entire transaction session._
- **transact() -** _Initializes paths and starts a transaction session._
- **setCliPath() -** _Sets the CLI path based._
- **getResourcePath() -** _Retrieves the resource path._
- **givingPermissionToCAcli() -** _Gives executable permissions to cardano-cli on Unix/Linux/MacOS systems._
  
### `CardanoJTransaction`
_This class provides methods for building, signing, and submitting transactions._

### Methods:
- **submitTransaction(String cliPath, String resourcePath, String network, String senderName) -** _Submits a transaction._
- **signTransaction(String cliPath, String resourcePath, String network, String name) -** _Signs a transaction._
- **buildTransaction(String cliPath, String resourcePath, String address, String receiver, String network, int lovelace, String senderName, String datumValue) -** _Builds a transaction._
- **getTransactionDetails(String cliPath, String resourcePath, String address, String network) -** _Retrieves transaction details._
- **getTransaction(String filePath) -** _Parses the transaction details from a file._
- **parseLovelace(String amount) -** _Parses the lovelace amount from a string._

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

### Example:

```sh
CardanoJCreateDatum dat = new CardanoJCreateDatum();
String datum = dat.create(senderAddress, receiverAddress, lovelace, resourcePath);
```
### Building the Transaction
Construct a transaction with specified parameters.

```sh
CardanoJTransaction tr = new CardanoJTransaction();
tr.buildTransaction(cliPath, resourcePath, senderAddress, receiverAddress, network, lovelace, senderName, datum);
```
### Example:
```sh
CardanoJTransaction tr = new CardanoJTransaction();
tr.buildTransaction(cliPath, resourcePath, senderAddress, receiverAddress, network, lovelace, senderName, datum);
```
### Signing the Transaction
Sign the constructed transaction.
### Example:
```sh
tr.signTransaction(cliPath, resourcePath, network, senderName);
```
### Submitting the Transaction
Submit the signed transaction to the Cardano network.

### Example:
```sh
String result = tr.submitTransaction(cliPath, resourcePath, network, senderName);
```
### Transaction Validation
Validate and check the transaction on Cardanoscan.
### Example:
```sh
System.out.println("Cardanoscan: https://preview.cardanoscan.io/transaction/" + result);
```

### Notes
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

### Classes and Methods
### `CardanoJBuildTransaction`
This class handles the core functionality for querying, building, signing, and submitting transactions.

### Methods:
- **query():** Queries the balance of an address.
- **transactionSession():** Manages the entire transaction session.
- **transact():** Initializes paths and starts a transaction session.
- **setCliPath():** Sets the CLI path based.
- **getResourcePath():** Retrieves the resource path.
- **givingPermissionToCAcli():** Gives executable permissions to cardano-cli on Unix/Linux/MacOS systems.
### `CardanoJTransaction`
This class provides methods for building, signing, and submitting transactions.
### Methods:
- **submitTransaction(String cliPath, String resourcePath, String network, String senderName):** Submits a transaction.
- **signTransaction(String cliPath, String resourcePath, String network, String name):** Signs a transaction.
- **buildTransaction(String cliPath, String resourcePath, String address, String receiver, String network, int lovelace, String senderName, String datumValue):** Builds a transaction.
- **getTransactionDetails(String cliPath, String resourcePath, String address, String network):** Retrieves transaction details.
- **getTransaction(String filePath):** Parses the transaction details from a file.
- **parseLovelace(String amount):** Parses the lovelace amount from a string.

# Cardano CLI Java Toolkit
###### _This Java toolkit provides functionality to interact with Cardano's command-line interface (CLI) for various tasks including address generation, wallet creation, and transaction management. It simplifies the process for Java developers to integrate Cardano functionalities into their applications._

## Requirements
- Java 8 or higher
- Cardano CLI installed
- Access to a Cardano node with the appropriate socket path
- Installation
- Clone this repository to your local machine.
- Ensure you have Java installed on your system.
- Install the Cardano CLI.
- For Windows users, ensure cardano-address.exe is placed in the src/main/resources/bin/ directory.

## Usage
### Cardano Address Generator
This program generates Cardano addresses for users based on their input. It interacts with the Cardano CLI to perform key generation and address building processes.

Usage:

```sh
$ java -jar CardanoAddressGenerator.jar
Enter name: Alice
--> Files Generated Successfully.
Exited with code : 0
```
### Cardano Wallet Creator
The CreateWallet class facilitates the creation of Cardano wallets. It generates a recovery phrase, derives the root private key, generates payment keys and addresses, and saves them to files.

Usage:
```sh
CreateWallet walletCreator = new CreateWallet();
walletCreator.create();
```

### Cardano Transaction Toolkit
This toolkit provides Java classes for facilitating Cardano transactions by interacting with the Cardano CLI. It offers methods for querying protocol parameters, building transactions, signing transactions, and submitting transactions to the Cardano network.

Usage:
```sh
TransactionCollect tc = new TransactionCollect();
```
```sh
// Query protocol parameters
String protocolParamFile = tc.queryProtocolParam(cliPath, resourcePath, network, socketPath);
```
```sh
// Build transaction
String bodyFile = tc.buildTransaction(cliPath, resourcePath, senderAddress, receiverAddress, network, lovelace, senderName, datumValue, socketPath);
```
```sh
// Sign transaction
tc.signTransaction(cliPath, resourcePath, network, receiverName);
```
```sh
// Submit transaction
String transactionID = tc.submitTransaction(cliPath, resourcePath, network, senderName);
```

> Note: _Customize paths and parameters according to your Cardano setup.
Ensure the Cardano CLI executable is available in the specified path.
For Unix/Linux/MacOS users, the program gives executable permissions to cardano-cli if necessary._.

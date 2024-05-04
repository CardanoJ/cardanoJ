# Cardano Blockchain REST API with Spring Boot

This repository contains a Spring Boot REST API that allows users to interact with the Cardano blockchain, akin to services provided by Blockfrost.

## Controller Classes

1. **CardanoController:** Handles endpoints for interacting with the Cardano blockchain, including UTXO retrieval, transaction building, signing, and submission.

2. **SignTransactionController:** Manages endpoints for signing transactions using provided signing keys and transaction bodies.

3. **BuildTransactionController:** Responsible for constructing transactions by specifying sender address, receiver address, amount, transaction hash, and ID.

4. **SubmitTransactionController:** Deals with endpoints for submitting signed transactions to the Cardano blockchain.

## Util Classes

1. **RandomNameGenerator:** Utility class for generating random names, likely used for unique identifiers within the application.

2. **CJConstant:** Contains constant values utilized throughout the application, including endpoint URLs and default values.

## Usage

### Step 1: Retrieve UTXO from Address

**Endpoint**: `GET http://localhost:8080/api/queryutxo/{address}`
Retrieves Unspent Transaction Outputs (UTXOs) associated with a given address.

### Step 2: Build Transaction

**Endpoint**: `GET http://localhost:8080/api/build/{senderAddress}/{receiverAddress}/{lovelace}/{transactionHash}/{transactionId}`
Builds a transaction with sender and receiver addresses, amount in lovelace, transaction hash, and ID.

### Step 3: Sign Transaction

**Endpoint**: `GET http://localhost:8080/api/sign?signKey={signKey}&txbody={transactionBody}`
Signs the transaction using provided signing key and transaction body.

### Step 4: Submit Transaction

**Endpoint**: `GET http://localhost:8080/api/submit?tx={signedTransaction}`
Submits the signed transaction to the Cardano blockchain.

## Running the Application

1. Clone this repository.
2. Navigate to the project directory.
3. Build the project using Maven or your preferred build tool.
4. Run the Spring Boot application.
5. Access the API at http://localhost:8080.

## Dependencies

- Spring Boot
- Cardano SDK (if applicable)
- Other dependencies specified in the pom.xml file.

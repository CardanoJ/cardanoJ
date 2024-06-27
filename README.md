# CardanoJ API Documentation

## Introduction

This document provides documentation for various controllers in the CardanoJ API, each serving specific functionalities related to the Cardano blockchain.

**how the CardanoJ API works, please watch the following YouTube video:**

[CardanoJ API Documentation and Use](https://youtu.be/CrMZbuiwQss?si=wq6qnLmIdS5aCAJ2)

## Table of Contents

1. [Address Controller](#address-controller)
2. [Script Transaction Collect Controller](#script-transaction-collect-controller)
3. [Script Transaction Make Controller](#script-transaction-make-controller)
4. [Stake Controller](#stake-controller)
5. [Transaction Controller](#transaction-controller)

---

## Address Controller
### CardanoJBuildAddressController
This endpoint generates a Cardano address.

- **Path:** `/api/address`
- **Method:** `GET`
- **Summary:** Get a Cardano address.
- **Responses:**
  - `200 OK`: Successfully generated Cardano address.
  - `500 Internal Server Error`: Failed to generated Cardano address due to an internal server error.

## Script Transaction Collect Controller
### CardanoJCollectBuildTransactionController
This endpoint generates the build address necessary for a Cardano transaction.

- **Path:** `/api/collect`
- **Method:** `GET`
- **Summary:** Retrieve the build address for a transaction.
- **Parameters:**
  - `collateral` (query parameter): Collateral for the transaction.
  - `scriptUtxoId` (query parameter): Script UTXO ID for the transaction.
  - `datumValue` (query parameter): Datum value for the transaction.
  - `redeemerValue` (query parameter): Redeemer value for the transaction.
  - `scriptFile` (query parameter): Script file for the transaction.
  - `feeAddress` (query parameter): Fee address for the transaction.
  - `receiverAddress` (query parameter): Receiver address for the transaction.
  - `datumHash` (query parameter): Datum hash for the transaction.
- **Responses:**
   - `200 OK`: The build address retrieved successfully.
     Content: String

### CardanoJQueryProtocolParamController
This endpoint queries the Cardano blockchain for protocol parameters and returns them in JSON format.

- **Path:** `/api/protocolparam`
- **Method:** `GET`
- **Summary:** Retrieve Cardano protocol parameters.
- **Responses:**
   - `200 OK`: Protocol parameters retrieved successfully.
      Content: String (JSON representation of the protocol parameters)

## Script Transaction Make Controller
### CardanoJScriptAddressController
This endpoint decodes CBOR encoded data, saves it to a file, and then generates a Cardano address from the file.

- **Path:** `/api/script`
- **Method:** `GET`
- **Summary:** Retrieve an address from CBOR encoded data.
- **Parameters:**
  - cbor (String): CBOR encoded data. This parameter is required.
- **Responses:**
   - `200 OK`: Address retrieved successfully.
     Content: JSON object containing the address.
   - `400 Bad Request`: Invalid CBOR encoding.
    Content: JSON object containing the error message.
   - `500 Internal Server Error`: Failed to generate Payment Script Address.
    Content: JSON object containing the error message.

### CardanoJScriptDatumHashController
This endpoint generates a hash for the given datum value using the Cardano CLI.

- **Path:** `/api/datum`
- **Method:** `GET`
- **Summary:** Retrieve an address from CBOR encoded data.
- **Parameters:**
  - datumValue (String): The datum value to hash. This parameter is required.
- **Responses:**
   - `200 OK`: Hash generated successfully.
Content: JSON object containing the datum hash.
   - `500 Internal Server Error`: Failed to generate the datum hash.
Content: JSON object containing the error message.

### CardanoJScriptMakeBuildTransactionController
This endpoint builds a Cardano transaction using the provided sender address, script address, amount of lovelace, datum hash, transaction hash, and transaction ID.

- **Path:** `/api/script/{senderAddress}/{scriptAddress}/{lovelace}/{datumHash}/{txHash}/{txID}`
- **Method:** `GET`
- **Summary:** Build a Cardano transaction with provided parameters.
- **Parameters:**
  - senderAddress (String): The address of the sender.
  - scriptAddress (String): The script address.
  - lovelace (String): The amount of lovelace.
  - datumHash (String): The datum hash.
  - txHash (String): The transaction hash.
  - txID (String): The transaction ID.
- **Responses:**
   - `200 OK`: Transaction body generated successfully.
     Content: JSON string containing the transaction body.
  - `500 Internal Server Error`: Failed to generate the transaction body.
     Content: Error message.


## Stake Controller
### CardanoJStakeBuildTransactionController
This endpoint accepts a request containing various parameters necessary to build a staking transaction. It constructs and executes a Cardano CLI command to build the transaction and returns the transaction body.

- **Path:** `/api/build`
- **Method:** `POST`
- **Summary:** Build a staking transaction with provided parameters.
- **Parameters:**
  - BuildTransactionRequest (JSON object): Contains the following fields:
  - regCert (String): Registration certificate content.
  - delCert (String): Delegation certificate content.
  - scriptFile (String): Script file content.
  - redeemerFile (String): Redeemer file content.
  - protocolparam (String): Protocol parameters content.
  - address (String): Change address.
  - txHash (String): Transaction hash.
  - txId (String): Transaction ID.
  - collateralTxHash (String): Collateral transaction hash.
  - collateralTxId (String): Collateral transaction ID.
- **Responses:**
  - `200 OK`: Successfully built the transaction.
     Content: JSON string containing the transaction body.
  - `500 Internal Server Error`: Failed to build the transaction.
     Content: Error message.

### CardanojCalculateFeeController
This endpoint takes several parameters related to the transaction and protocol, calculates the minimum required fee using the Cardano CLI, and returns the fee as a JSON response.

- **Path:** `/api/fee`
- **Method:** `GET`
- **Summary:** Calculate the minimum fee for a transaction.
- **Parameters:**
  - txBuild (String): The transaction build file content.
  - protocolParam (String): The protocol parameter file content.
  - txInCount (String): Number of transaction inputs.
  - txOutCount (String): Number of transaction outputs.
  - byronWitnessCount (String): Number of Byron witnesses.
- **Responses:**
  - `200 OK`: Successfully calculated the minimum fee.
    Content: JSON string containing the calculated fee.
  - `500 Internal Server Error`: Error occurred during fee calculation.
    Content: Error message.

### CardanoJQueryStakePoolController
This endpoint runs a Cardano CLI command to query stake pools and returns the result as a JSON response.

- **Path:** `/api/stakepool`
- **Method:** `GET`
- **Summary:** Query available stake pools in the Cardano network.
- **Responses:**
  - `200 OK`: Successfully queried stake pools.
   Content: JSON string containing the stake pools information.
  - `500 Internal Server Error`: Error occurred during stake pools query.
   Content: Error message.

### CardanoJstakeAddressBuildController
This endpoint decodes the provided payment key and script content, saves them to files, and then runs a Cardano CLI command to build the stake address. The result is returned in a JSON format.

- **Path:** `/api/stakebuild`
- **Method:** `GET`
- **Summary:** Build a stake address using the provided payment key and script content.
- **Parameters:**
  - paymentKey (String): The content of the payment key.
  - script (String): The content of the script.
- **Responses:**
  - `200 OK`: Successfully built the stake address.
    Content: JSON string containing the stake address.
  - `500 Internal Server Error`: Error occurred during the stake address build process.
    Content: Error message.

### CardanoJStakeAddressController
This endpoint decodes the provided script content, saves it to a file, and invokes a Cardano CLI command to build the stake address. The response contains the generated stake address or an error message if the generation fails.

- **Path:** `/api/stakeAddress`
- **Method:** `GET`
- **Summary:** Generate a stake address.
- **Parameters:**
  - script (String): The content of the script used to build the stake address.
- **Responses:**
  - `200 OK`: Successfully generated the stake address.
    Content: JSON string containing the stake address.
  - `400 Bad Request`: Invalid CBOR encoding (if decoding fails).
    Content: JSON string indicating the error.
  - `500 Internal Server Error` : Internal server error during the stake address generation process.
    Content: JSON string describing the error.

### CardanoJStakeAddressDelCertController
This endpoint decodes the provided script content, saves it to a file, and invokes a Cardano CLI command to generate the delegation certificate for the specified stake pool.

- **Path:** `/api/delcert`
- **Method:** `GET`
- **Summary:** Generate a stake address delegation certificate.
- **Parameters:**
  - script (String): The content of the stake script.
  - poolId (String): The ID of the stake pool.
- **Responses:**
  - `200 OK`: Successfully generated the delegation certificate.
    Content: JSON string containing the delegation certificate.
  - `500 Internal Server Error`: Internal server error during the delegation certificate generation process.
   Content: JSON string describing the error.

### CardanoJstakeAddressRegCertController
This endpoint decodes the provided script content, saves it to a file, and invokes a Cardano CLI command to generate the registration certificate.

- **Path:** `/api/regcert`
- **Method:** `GET`
- **Summary:** Generate a stake address registration certificate.
- **Parameters:**
  - script (String): The content of the stake script.
- **Responses:**
  - `200 OK`: Successfully generated the registration certificate.
    Content: JSON string containing the registration certificate.
  - `500 Internal Server Error`: Internal server error during the registration certificate generation process.
    Content: JSON string describing the error.

### CardanoJWithdrawUserBuildTransactionController
This endpoint decodes CBOR-encoded script and redeemer file parameters, saves them to temporary files, and invokes Cardano CLI to build the withdrawal transaction.

- **Path:** `/api/buildtx`
- **Method:** `GET`
- **Summary:** Build a withdrawal transaction.
- **Parameters:**
  - script (String): CBOR-encoded stake script.
  - reedemfile (String): CBOR-encoded redeemer file.
  - address (String): Transaction address.
  - txOut (String): Transaction output.
  - lovelace (String): Lovelace amount.
  - txIn (String): Transaction input.
  - collateral (String): Collateral.
  - withdrawlAddress (String): Withdrawal address.
  - withdrawlLovelace (String): Withdrawal lovelace amount.
- **Responses:**
  - `200 OK`: Successfully built the transaction.
    Content: JSON object with the transaction body.
  - `500 Internal Server Error`: Internal server error.
    Content: JSON object with error details.

## Transaction Controller
### CardanoJUtxoController
This endpoints to interact with Cardano's Unspent Transaction Output (UTXO) using CardanoJ. It allows querying UTXO details for a given address using the Cardano command-line interface.

- **Path:** `/api/queryutxo/{address}`
- **Method:** `GET`
- **Summary:** Querying UTXO details.
- **Parameters:**
   - address : Path variable representing the Cardano address to query UTXO details for.
- **Responses:**
  - `200 OK`: Description: Successfully retrieved the UTXO details.
    Content: A JSON array containing transaction details.
  - `500 Internal Server Error`:  Description: An error occurred during the process.
    Content: Error message indicating the failure reason.

### CardanoJBuildTransactionController
This endpoint builds a Cardano transaction based on provided parameters.

- **Path:** `/api/transaction/build/{senderAddress}/{receiverAddress}/{lovelace}/{txHash}/{txID}`
- **Method:** `GET`
- **Summary:** Build a Cardano transaction.
- **Parameters:**
   - senderAddress, receiverAddress, lovelace, txHash, txID (path parameters): Transaction details.
- **Responses:**
  - `200 OK`: Successfully built the transaction.
  - `500 Internal Server Error`: Failed to build the transaction due to an internal server error.

### CardanoJSignTransactionController
This endpoint signs a Cardano transaction using the provided signing key and transaction body.

- **Path:** `/api/transaction/sign`
- **Method:** `GET`
- **Summary:** Sign a Cardano transaction.
- **Parameters:**
   - signKey (query parameter): Signing key for the transaction.
   - txbody (query parameter): Transaction body to sign.
- **Responses:**
  - `200 OK`: Successfully signed the transaction.
  - `500 Internal Server Error`: Failed to sign the transaction due to an internal server error.

### CardanoJSubmitTransactionController
This endpoint submits a Cardano transaction to the blockchain.

- **Path:**  `/api/transaction/submit`
- **Method:** `GET`
- **Summary:** Submit a Cardano transaction.
- **Parameters:**
   - tx (query parameter): Transaction to submit.
- **Responses:**
  - `200 OK`:  Successfully submitted the transaction.
  - `500 Internal Server Error`: Failed to submit the transaction due to an internal server error.


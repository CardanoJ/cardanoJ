# CardanoJ API Documentation

## Introduction

This document provides documentation for various controllers in the CardanoJ API, each serving specific functionalities related to the Cardano blockchain.

## Table of Contents

1. [Address Controller](#address-controller)
2. [Script Transaction Collect Controller](#script-transaction-collect-controller)
3. [Script Transaction Make Controller](#script-transaction-make-controller)
4. [Stake Controller](#stake-controller)
5. [Transaction Controller](#transaction-controller)

---

## Address Controller
### Get Address Information
This endpoint retrieves information related to a Cardano address.

- **Path:** `/api/address/{address}`
- **Method:** `GET`
- **Summary:** Get information about a Cardano address.
- **Parameters:**
  - `address` (path parameter): Cardano address to query information for.
- **Responses:**
  - `200 OK`: Successfully retrieved address information.
  - `500 Internal Server Error`: Failed to retrieve address information due to an internal server error.

## Script Transaction Collect Controller
### Collect Script Transaction
This endpoint retrieves details related to a specific script transaction.

- **Path:** `/api/script/collect/{scriptId}`
- **Method:** `GET`
- **Summary:** Collect script-related transaction details.
- **Parameters:**
  - `scriptId (path parameter): Identifier for the script transaction.
- **Responses:**
  - `200 OK`: Successfully collected script transaction details.
  - `500 Internal Server Error`: Failed to collect script transaction details due to an internal server error.

## Script Transaction Make Controller
### Make Script Transaction
This endpoint initiates the creation of a script-related transaction.

- **Path:** `/api/script/make`
- **Method:** `GET`
- **Summary:** Make a script-related transaction.
- **Parameters:**
  - Body parameters: Details required to initiate the script transaction.
- **Responses:**
  - `200 OK`: Successfully initiated the script transaction.
  - `500 Internal Server Error`: Failed to initiate the script transaction due to an internal server error.

## Stake Controller
### Generate Stake Address Registration Certificate
This endpoint generates a stake address registration certificate.

- **Path:** `/api/stake/regcert`
- **Method:** `GET`
- **Summary:** Generate a stake address registration certificate.
- **Parameters:**
  - script (query parameter): Script content for stake address registration.
- **Responses:**
  - `200 OK`: Successfully generated the registration certificate.
  - `500 Internal Server Error`: Failed to generate the registration certificate due to an internal server error.

## Transaction Controller
### Build Transaction
This endpoint builds a Cardano transaction based on provided parameters.

- **Path:**  `/api/transaction/build/{senderAddress}/{receiverAddress}/{lovelace}/{txHash}/{txID}`
- **Method:** `GET`
- **Summary:** Build a Cardano transaction.
- **Parameters:**
   -senderAddress, receiverAddress, lovelace, txHash, txID (path parameters): Transaction details.
- **Responses:**
  - `200 OK`: Successfully built the transaction.
  - `500 Internal Server Error`: Failed to build the transaction due to an internal server error.

### Sign Transaction
This endpoint signs a Cardano transaction using the provided signing key and transaction body.

- **Path:** `/api/transaction/sign`
- **Method:** `GET`
- **Summary:** Sign a Cardano transaction.
- **Parameters:**
   -signKey (query parameter): Signing key for the transaction.
   -txbody (query parameter): Transaction body to sign.
- **Responses:**
  - `200 OK`: Successfully signed the transaction.
  - `500 Internal Server Error`: Failed to sign the transaction due to an internal server error.

### Submit Transaction
This endpoint submits a Cardano transaction to the blockchain.

- **Path:**  `/api/transaction/submit`
- **Method:** `GET`
- **Summary:** Submit a Cardano transaction.
- **Parameters:**
tx (query parameter): Transaction to submit.
- **Responses:**
  - `200 OK`:  Successfully submitted the transaction.
  - `500 Internal Server Error`: Failed to submit the transaction due to an internal server error.


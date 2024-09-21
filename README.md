# CardanoJ SDK
## Introduction
The **CardanoJ SDK** is a powerful toolkit for interacting with the Cardano blockchain, offering commands to manage local testnets, handle wallets, execute scripts, and create decentralized applications (DApps). It simplifies blockchain development by providing tools for building, testing, and deploying transactions and scripts on Cardano. Perfect for developers aiming to streamline their Cardano DApp projects.

**For a detailed explanation of how the CardanoJ SDK works, please watch the following YouTube video:**

[CardanoJ SDK Overview](https://youtu.be/ZpgTwbHTHQU)

## Table of Contents

   - [Local Testnet Management](#local-testnet-management)
   - [Wallet Interaction](#wallet-interaction)
   - [Script Interaction](#script-interaction)
   - [Creating a New DApp Project](#creating-a-new-dapp-project)
   - [Building and Testing DApp Projects](#building-and-testing-dapp-projects)

---
## Usage

### Local Testnet Management

#### Launching a Local Testnet
To launch a local Cardano testnet, use the following command:

```sh
cardanoj-run-testnet-magic2
```
### Wallet Interaction
#### Managing Wallets and Transactions
Here are some key commands for wallet interaction:

1. Build Address
```sh
cardanoj-wallet get-address-info
```

2. Get UTXO for a specific address
```sh
cardanoj-wallet get-utxo addr_test1vpee..
```

3. Build a transaction
```sh
cardanoj-wallet build-transaction --sender-address <address> --receiver-address <address> --lovelace 1000000 --tx-hash <hash> --tx-id <id>
```

4. Sign a transaction
```sh
cardanoj-wallet sign-transaction <tx> --signing-key <key>
```

5. Submit a transaction
```sh
cardanoj-wallet submit-transaction <signedTx>
```
### Script Interaction
##### Executing Scripts
Use the following commands to interact with scripts:
1. Build Script Address
```sh
cardanoj-script get-script-address <cbor>
```

2. Get Datum Hash
```sh
cardanoj-script get-datum-hash <datum>
```

3. Build Script Transaction
```sh
cardanoj-script get-script-transaction-details \
  --sender-address <senderAddress> \
  --receiver-address <receiverAddress> \
  --lovelace <amount> \
  --tx-hash <hash> \
  --tx-id <id> \
  --datum-value <datum>
```
### Creating a New DApp Project
To create a new DApp project, run the following command:
```sh
cardanoj-create-dapp <project-name>
```
### Building and Testing DApp Projects
1. Build a DApp Project
```sh
cardanoj-build-dapp
```
2. Test a DApp Project
```sh
cardanoj-test-dapp
```


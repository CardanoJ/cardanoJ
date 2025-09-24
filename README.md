# CardanoJ-DApp Setup and Deployment Guide 

# DApp-1

This guide provides comprehensive instructions for setting up, running, and deploying the **CardanoJ-DApp**. The project includes an API backend (Spring Boot built with Gradle), the CardanoJ Wallet extension, and a user-facing dApp.

---

## Project Structure

The **CardanoJ-DApp** project consists of the following components :

1. **CardanoJ-ApiBackend** : A Spring Boot server built with Gradle that provides API services.
2. **CardanoJ-Wallet-Extension** :
   - **node_server**: A Node.js server for interacting with the CardanoJ Wallet backend.
   - **user-dapp**: The frontend dApp interface.
   - **wallet_client**: A Chrome extension acting as the wallet interface.
3. **CardanoJ-DApp_1** : A React-based web application that serves as the user-facing dApp interface.

---

## Prerequisites

Ensure the following tools and dependencies are installed:

- **Node.js** (v18 or later)
- **npm** or **yarn** (for managing dependencies)
- **Google Chrome** (for loading and using the wallet extension)
- **Java Development Kit (JDK)** (v17 or later, required to run the Spring Boot .jar)
- **Gradle** (for building the Spring Boot .jar)

---

## Setup and Deployment Steps

### Step 1 : Running the Spring Boot API Backend (with Gradle)

1. **Navigate to the ApiBackend directory :**

```sh
cd ~/DApp_1/ApiBackend
```

2. **Run the Backend Application :**

```bash
./gradlew bootRun
```

### Step 2 : Running the Node Server

1. **Open a new terminal window.**

2. **Navigate to the `node_server` directory :**

```bash
cd ~/DApp_1/cardanoj-wallet-extension/node_server
```
3. **Install dependencies (if needed) :**

```bash
npm install
```

4. **Start the Node.js server :**
```bash
npm start
```
The Node.js server should now be running on http://localhost:3000/

### Step 3 : Building the Wallet Client

1. **Open a terminal window.**

2. **Navigate to the wallet_client directory :**

```bash
cd ~/DApp_1/cardanoj-wallet-extension/wallet_client
```
3. **Install dependencies (if needed) :**

```bash
npm install
```

4. **Build the wallet client :**
```bash
npm run build
```
This will create a dist folder inside the wallet_client directory, containing the Chrome extension build.

### Step 4 : Loading the Wallet Extension in Chrome

1. **Open Google Chrome.**

2. **Go to the Chrome extensions page :**

```url
   chrome://extensions/
```
3. **Enable Developer Mode by toggling the switch in the top-right corner.**

4. **Click on "Load unpacked".**
5. **Select the dist folder inside the wallet_client directory :**
```bash
~/DApp_1/cardanoj-wallet-extension/wallet_client/dist
```
The wallet extension should now be visible and enabled in your Chrome browser.

### Step 5 : Running the User dApp

1. **Open another terminal window.**

2. **Navigate to the `user-dapp` directory :**

```bash
cd ~/DApp_1/user-dapp
```
3. **Install dependencies (if needed) :**

```bash
npm install
```

4. **Start the user-facing dApp :**
```bash
npm run dev
```
The dApp should now be running locally at http://localhost:5173/

### Step 6 : Using the dApp

1 `Register`: After loading the wallet extension, register as a new user in the extension.

2 `Login`: Use your credentials to log into the wallet.

3 `Address Generation` After logging in, a new wallet address will be generated automatically.

4 `Send Transactions`:
- Open the user-dapp running at http://localhost:5173/
- Enter the recipient's address and the amount you want to send.
- Confirm and submit the transaction using the dApp interface.

### Troubleshooting

#### Module Not Found or Failed to Install
If you encounter a "module not found" error, ensure you have run `npm install` in the following directories :

- `node_server`
- `user-dapp`
- `wallet_client`

#### Port Conflicts
If the dApp or server fails to start, check if the default ports are already in use by other services :

- **3000** for the Node.js server
- **5173** for the dApp Application
- **8080** for the API Backend Application

---
---

# DApp-2

This guide provides comprehensive instructions for setting up, running, and deploying the **CardanoJ-DApp**. The project includes an API backend (Spring Boot built with Gradle), the Light Browser Wallets extension, and a user-facing dApp.

---

## Project Structure

The **CardanoJ-DApp** project consists of the following components :

1. **CardanoJ-ApiBackend** : A Spring Boot server built with Gradle that provides API services.
2. **Light-Browser-Wallet-Extension** :
      - Nami
      - Yoroi Nightly
3. **CardanoJ DApp_2** : A React-based web application that serves as the user-facing dApp interface.

---

## Prerequisites

Ensure the following tools and dependencies are installed:

- **Node.js** (v18 or later)
- **npm** or **yarn** (for managing dependencies)
- **Google Chrome** (for loading and using the wallet extension)
- **Java Development Kit (JDK)** (v17 or later, required to run the Spring Boot .jar)
- **Gradle** (for building the Spring Boot .jar)

---

## Setup and Deployment Steps

### Step 1 : Running the Spring Boot API Backend (with Gradle)

1. **Navigate to the ApiBackend directory :**

```sh
cd ~/DApp_2/ApiBackend
```

2. **Run the Backend Application :**

```bash
./gradlew bootRun
```

### Step 2 : Running the User dApp

1. **Open another terminal window.**

2. **Navigate to the `user-dapp` directory :**

```bash
cd ~/DApp_2/user-dapp
```
3. **Install dependencies (if needed) :**

```bash
npm install
```

4. **Start the user-facing dApp :**
```bash
npm run dev
```
The dApp should now be running locally at http://localhost:5173/

### Step 3 : Using the dApp

1 `Register :` Use the Nami or Yoroi wallet extensions to register your account.

2 `Wallet Availability :` The dApp will automatically check if a supported wallet is installed in your browser. If no wallet is detected, it will provide a link to install one.

3 `Select Wallet :` Within the dApp, choose your preferred wallet (Nami or Yoroi) to perform transactions.

4 `Send Transactions :`
- Open the user-dapp running at http://localhost:5173/
- Enter the recipient's address and the amount you want to send.
- Confirm and submit the transaction using the dApp interface.


### Troubleshooting
#### Module Not Found or Failed to Install
If you encounter a "module not found" error, ensure you have run `npm install` and installed other required dependencies in the following directory:

- `user-dapp`

#### Port Conflicts
If the dApp or server fails to start, check if the default ports are already in use by other services :

- **8080** for the API Backend Application
- **5173** for the dApp Application

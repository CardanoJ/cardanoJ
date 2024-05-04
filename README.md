# Cardano JNI Client with Rust Integration

This Java program interacts with a native Cardano library through JNI to perform various operations. The native library is implemented in Rust for enhanced performance or specific functionality.

## Address Generation

Generate a Cardano address based on a given mnemonic phrase, index, and network type (mainnet or testnet).

### Example:

```sh
String phrase = ""; // Define mnemonic phrase
int index = 0;
boolean isTestnet = false;

String result = JNAUtil.getBaseAddress(phrase, index, isTestnet);
System.out.println(result);
```

## Private Key Generation
Retrieve the private key corresponding to the given mnemonic phrase and index.

### Example:
```sh
String phrase = ""; // Define mnemonic phrase
int index = 0;

String privateKey = JNAUtil.getPrivateKeyFromMnemonic(phrase, index);
System.out.println("Private Key: " + privateKey);
```
## Signing
Sign a raw transaction hash using the private key obtained from the mnemonic phrase.

### Example:
```sh
// Generate raw transaction hex
String rawTxHex = RawTxnHex.rawTxnHexGeneration();

// Get private key from mnemonic
String mnemonic =  ""; // Define mnemonic phrase
String privateKey = JNAUtil.getPrivateKeyFromMnemonic(mnemonic, 0);

// Sign the transaction
String signedTxnHex = JNAUtil.sign(rawTxHex, privateKey);
System.out.println("Signed Txn Hex: " + signedTxnHex);
```

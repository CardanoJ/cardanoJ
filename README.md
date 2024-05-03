# Cardano JNI Client with Rust Integration

This Java program interacts with a native Cardano library through JNI to perform various operations. The native library implemented in Rust for enhanced performance or specific functionality.

## Address Generation

The program generates a Cardano address based on a given mnemonic phrase, index, and network type (mainnet or testnet).

### Example:

```sh
// Define mnemonic phrase
String[] mnemonicWords = { "insect", "sad", "deal", ... };
String phrase = String.join(" ", mnemonicWords);
int index = 0;
boolean isTestnet = false;

// Obtain base address
Pointer result = JNAInterface.INSTANCE.getBaseAddress(phrase, index, isTestnet);

// Print the main address
System.out.println("Main Address: " + result.getString(0));
```
## Private Key Generation
The program retrieves the private key corresponding to the given mnemonic phrase and index.

### Example:
```sh
// Define mnemonic phrase
String[] mnemonicWords = { "insect", "sad", "deal", ... };
String phrase = String.join(" ", mnemonicWords);
int index = 0;

// Obtain private key from mnemonic
Pointer privateKey = JNAInterface.INSTANCE.getPrivateKeyFromMnemonic(phrase, index);

// Print the private key
System.out.println("Private Key: " + privateKey.getString(0));
```
## Signing
The program signs a raw transaction hash using the private key obtained from the mnemonic phrase.

### Example:
```sh
// Obtain private key from mnemonic (as shown in the Private Key Generation section)

// Sign a raw transaction hash
Pointer sign = JNAInterface.INSTANCE.sign("01d8d23cb0f8954e1c3c8c487983294d6b31d7d09eb4e16169121b573c75e7e4", privateKey.getString(0));

// Print the signature
System.out.println("Signature: " + sign.getString(0));
```

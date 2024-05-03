# CardanoJ Blockfrost Integration

Cardano Blockfrost Backend implementation for CardanoJ. This project can be integrated into your Java project using Maven or Gradle.

## Account API Usage

### Create a New Account

```sh
// Create a Mainnet account
Account account = new Account();

// Create a Mainnet account specifying network
Account account = new Account(Networks.mainnet());

// Create a Testnet account
Account account = new Account(Networks.testnet());
```
- Get base address, enterprise address, mnemonic
```sh
String baseAddress = account.baseAddress();  //Base address at index=0

String enterpriseAddress = account.account.enterpriseAddress();  //Enterprise address at index = 0

String mnemonic = account.mnemonic();  //Get Mnemonic
```
- Get Account from Mnemonic
```sh
String mnemonic = "...";
Account account = new Account(mnemonic);  //Create a Mainnet account from Mnemonic

Account account = new Account(Networks.testnet(), mnemonic); //Create a Testnet account from Mnemonic
```
##### Create Backend Service
```sh
//For Blockfrost
BackendService backendService = new BFBackendService(Constants.BLOCKFROST_TESTNET_URL, <BF_PROJECT_ID>);    
                
FeeCalculationService feeCalculationService = backendService.getFeeCalculationService();
TransactionHelperService transactionHelperService = backendService.getTransactionHelperService();
TransactionService transactionService = backendService.getTransactionService();
BlockService blockService = backendService.getBlockService();
AssetService assetService = backendService.getAssetService();
UtxoService utxoService = backendService.getUtxoService();
MetadataService metadataService = backendService.getMetadataService();
EpochService epochService = backendService.getEpochService();
AddressService addressService = backendService.getAddressService();                
```
##### Simple ADA Payment using QuickTx Api
```sh
        Tx tx1 = new Tx()
                .payToAddress(receiver1Addr, Amount.ada(1.5))
                .payToAddress(receiver2Addr, Amount.ada(2.5))
                .attachMetadata(MessageMetadata.create().add("This is a test message 2"))
                .from(sender1Addr);

        Tx tx2 = new Tx()
                .payToAddress(receiver2Addr, Amount.ada(4.5))
                .from(sender2Addr);

        QuickTxBuilder quickTxBuilder = new QuickTxBuilder(backendService);
        Result<String> result = quickTxBuilder
                .compose(tx1, tx2)
                .feePayer(sender1Addr)
                .withSigner(SignerProviders.signerFrom(sender1))
                .withSigner(SignerProviders.signerFrom(sender2))
                .completeAndWait(System.out::println);
```
##### Simple ADA Payment using Composable Functions Api
```sh
        // Define expected Outputs
        Output output1 = Output.builder()
                .address(receiverAddress1)
                .assetName(LOVELACE)
                .qty(adaToLovelace(10))
                .build();

        Output output2 = Output.builder()
                .address(receiverAddress2)
                .assetName(LOVELACE)
                .qty(adaToLovelace(20))
                .build();
         // Create a CIP20 message metadata
        MessageMetadata metadata = MessageMetadata.create()
                .add("First transaction From Quotus");

        // Define TxBuilder
        TxBuilder txBuilder = output1.outputBuilder()
                .and(output2.outputBuilder())
                .buildInputs(createFromSender(senderAddress, senderAddress))
                .andThen(metadataProvider(metadata))
                .andThen(balanceTx(senderAddress, 1));
        
        UtxoSupplier utxoSupplier = new DefaultUtxoSupplier(backendService.getUtxoService());
        ProtocolParamsSupplier protocolParamsSupplier = new DefaultProtocolParamsSupplier(backendService.getEpochService());

        //Build and sign the transaction
         Transaction signedTransaction = TxBuilderContext.init(utxoSupplier, protocolParamsSupplier)
        .buildAndSign(txBuilder, signerFrom(senderAccount));

        //Submit the transaction
        Result<String> result = backendService.getTransactionService().submitTransaction(signedTransaction.serialize());
```
##### ScriptHash
```sh
Example: 1

ScriptPubkey scriptPubkey = new ScriptPubkey("ad7a7b87959173fc9eac9a85891cc93892f800dd45c0544128228884")
String policyId = scriptPubkey.getPolicyId();

Example: 2

ScriptPubkey scriptPubkey1 = ...;
SecretKey sk1 = ...;

ScriptPubkey scriptPubkey2 = ...;
SecretKey sk2 = ...;

ScriptPubkey scriptPubkey3 = ...;
SecretKey sk3 = ...;

ScriptAtLeast scriptAtLeast = new ScriptAtLeast(2)
                .addScript(scriptPubkey1)
                .addScript(scriptPubkey2)
                .addScript(scriptPubkey3);

String policyId = scriptAtLeast.getPolicyId();

```
##### Metadata
```sh
MetadataMap productDetailsMap = MetadataBuilder.createMap()
                .put("code", "PROD-800")
                .put("slno", "SL20000039484");

MetadataList tagList = MetadataBuilder.createList()
                .add("laptop")
                .add("computer");

Metadata metadata = MetadataBuilder.createMetadata()
                .put(new BigInteger("670001"), productDetailsMap)
                .put(new BigInteger("670001"), productDetailsMap);
```
##### UtxoSelectionStrategy in High Level Api
The utxo selection strategy can be changed by providing a custom implementation of UtxoSelectionStrategy interface. By default, the transaction builder apis use DefaultUtxoSelectionStrategyImpl which finds all required utxos sequentially. But it may not be efficient for some use cases.

You can use a custom or different implementation of UtxoSelectionStrategy to change the default utxo selection behaviour. Out-of-box, the library provides two additional implementations of UtxoSelectionStrategy

- LargestFirstUtxoSelectionStrategy
- RandomImproveUtxoSelectionStrategy

##### UtxoSupplier, ProtocolPramsSupplier
You can get UtxoSupplier and ProtocolParamsSupplier from the backend service. Alternatively, you can create your own UtxoSupplier, ProtocolParamsSupplier and use it in the transaction builder api.


package com.cardanoj.rust.jna;

import com.cardanoj.rust.backend.api.BackendService;
import com.cardanoj.rust.backend.api.DefaultProtocolParamsSupplier;
import com.cardanoj.rust.backend.api.DefaultUtxoSupplier;
import com.cardanoj.rust.blockfrost.common.Constants;
import com.cardanoj.rust.blockfrost.service.BFBackendService;
import com.cardanoj.rust.common.model.Networks;
import com.cardanoj.rust.coreapi.ProtocolParamsSupplier;
import com.cardanoj.rust.coreapi.UtxoSupplier;
import com.cardanoj.rust.coreapi.account.Account;
import com.cardanoj.rust.coreapi.model.Result;
import com.cardanoj.rust.crypto.cip20.MessageMetadata;
import com.cardanoj.rust.function.Output;
import com.cardanoj.rust.function.TxBuilder;
import com.cardanoj.rust.function.TxBuilderContext;
import com.cardanoj.rust.transaction.spec.Transaction;

import static com.cardanoj.rust.common.ADAConversionUtil.adaToLovelace;
import static com.cardanoj.rust.common.CardanoConstants.LOVELACE;
import static com.cardanoj.rust.function.helper.AuxDataProviders.metadataProvider;
import static com.cardanoj.rust.function.helper.BalanceTxBuilders.balanceTx;
import static com.cardanoj.rust.function.helper.InputBuilders.createFromSender;
import static com.cardanoj.rust.function.helper.SignerProviders.signerFrom;

public class SimpleAdaTransactionBF {

    public static Result<String> transfer() throws Exception {
        //Sender account
        String senderMnemonic = "brother verify rabbit join misery retire mother surprise game ignore main chase crowd captain miracle coast inflict half margin bid toy stairs right plastic";
        Account senderAccount = new Account(Networks.preview(), senderMnemonic);
        String senderAddress = senderAccount.baseAddress();
        System.out.println("Address : " + senderAddress);

        //Addresses to receive ada
        String receiverAddress1 = "addr_test1qqv5auuws3t8x2qjccc3u5g0tfcvwjnjvantxs83k6p8kmvjzgrfuhh95jay0p0he8kmxgsfh7s0m6jyjhnyw4azpwxs0ay5zm";
//        String receiverAddress2 = "addr_test1qzvy33rr24huuqv46ajex99hrcl0dauqcch7meznf4mdyd4sqwzjy5gaynruuwtdmwmdlnasa8t2g2t0fqmf8rhq3e6svxzum4";

        String projectId = "previewBQm3uVFvv7jwgNORqjNHQdw5vtrFK40f";
        BackendService backendService = new BFBackendService(Constants.BLOCKFROST_PREVIEW_URL, projectId);

        // Define expected Outputs
        Output output1 = Output.builder()
                .address(receiverAddress1)
                .assetName(LOVELACE)
                .qty(adaToLovelace(10))
                .build();

//        Output output2 = Output.builder()
//                .address(receiverAddress2)
//                .assetName(LOVELACE)
//                .qty(adaToLovelace(20))
//                .build();

        // Create a CIP20 message metadata
        MessageMetadata metadata = MessageMetadata.create()
                .add("TestTxn - 1");

        // Define TxBuilder
        TxBuilder txBuilder = output1.outputBuilder()
//                .and(output2.outputBuilder())
                .buildInputs(createFromSender(senderAddress, senderAddress))
                .andThen(metadataProvider(metadata))
                .andThen(balanceTx(senderAddress, 1));

        UtxoSupplier utxoSupplier = new DefaultUtxoSupplier(backendService.getUtxoService());
        ProtocolParamsSupplier protocolParamsSupplier = new DefaultProtocolParamsSupplier(backendService.getEpochService());

        //Build and sign the transaction
        Transaction signedTransaction = TxBuilderContext.init(utxoSupplier, protocolParamsSupplier)
                .buildAndSign(txBuilder, signerFrom(senderAccount));

        //Submit the transaction
        Result<String> txnId = backendService.getTransactionService().submitTransaction(signedTransaction.serialize());
//        System.out.println(txnId);
        System.out.println("https://preview.cardanoscan.io/transaction/"+txnId.getValue());

//        Map<String, String> result = new HashMap<>();
//        result.put("txnId", txnId.getValue());
//        result.put("receiverAddress", receiverAddress1);
//
//        return Result.success(result.toString());

        return txnId;
    }

//    public static void main(String[] args) throws Exception {
//        new SimpleAdaTransaction().transfer();
//    }
}
package com.cardanoj;

import com.cardanoj.coreapi.account.Account;
import com.cardanoj.coreapi.ProtocolParamsSupplier;
import com.cardanoj.coreapi.UtxoSupplier;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.backend.api.BackendService;
import com.cardanoj.backend.api.DefaultProtocolParamsSupplier;
import com.cardanoj.backend.api.DefaultUtxoSupplier;
import com.cardanoj.backendmodule.blockfrost.common.Constants;
import com.cardanoj.backendmodule.blockfrost.service.BFBackendService;
import com.cardanoj.crypto.cip20.MessageMetadata;
import com.cardanoj.common.model.Networks;
import com.cardanoj.function.Output;
import com.cardanoj.function.TxBuilder;
import com.cardanoj.function.TxBuilderContext;
import com.cardanoj.transaction.spec.Transaction;

import static com.cardanoj.common.ADAConversionUtil.adaToLovelace;
import static com.cardanoj.common.CardanoConstants.LOVELACE;
import static com.cardanoj.function.helper.AuxDataProviders.metadataProvider;
import static com.cardanoj.function.helper.BalanceTxBuilders.balanceTx;
import static com.cardanoj.function.helper.InputBuilders.createFromSender;
import static com.cardanoj.function.helper.SignerProviders.signerFrom;

public class SimpleTransfer {

    public void transfer() throws Exception {
        //addr_test1vr06yexumsxcf26uhdpyawpu9vffcxw0d9xr37vem3sfvwqwpggea
        //Sender account
        String senderMnemonic = "tongue right cloth hen inside innocent valve perfect fashion common delay glory pond tissue unknown device material elevator fire excite debate barely duck humble";
        Account senderAccount = new Account(Networks.preview(), senderMnemonic);
        String senderAddress = senderAccount.baseAddress();
        System.out.println("Addrress : " + senderAddress);

        //Addresses to receive ada
        String receiverAddress1 = "addr_test1qpjs693nk7makhcax3k7h0hkjyye2adwv3e300dkfwpqj8k2le4j5lg6gd773gdvs7jcnwdxvtztmxawwcdmvm0h870sardwde";
        String receiverAddress2 = "addr_test1qzvy33rr24huuqv46ajex99hrcl0dauqcch7meznf4mdyd4sqwzjy5gaynruuwtdmwmdlnasa8t2g2t0fqmf8rhq3e6svxzum4";

        // For Blockfrost
        String bf_projectId = "preview0abNGovnniEvGYuk8kAWGUN3gMeIM64O";
        BackendService backendService =
                new BFBackendService(Constants.BLOCKFROST_PREVIEW_URL, bf_projectId);

        // For Koios
        // BackendService backendService = new KoiosBackendService(KOIOS_TESTNET_URL);

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
        System.out.println(result);
        System.out.println("https://preview.cardanoscan.io/transaction/"+result.getValue());
    }

    public static void main(String[] args) throws Exception {
        new SimpleTransfer().transfer();
    }
}

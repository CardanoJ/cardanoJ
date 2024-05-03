package com.cardanoj.rust.jna;

import com.cardanoj.rust.exception.CborSerializationException;
import com.cardanoj.rust.transaction.spec.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RawTxnHex {
    public static String rawTxnHexGeneration() throws CborSerializationException {
        TransactionBody txnBody = new TransactionBody();

        TransactionInput txnInput = new TransactionInput();
        txnInput.setTransactionId("d1b1d0cba999691910184d4f401b5fcd69d78e00afaa112856fee04772abdb51");
        txnInput.setIndex(1);

        List<TransactionInput> inputList = new ArrayList<>();
        inputList.add(txnInput);
        txnBody.setInputs(inputList);

        TransactionOutput txnOutput =  new TransactionOutput();
        txnOutput.setAddress("addr_test1qrnzwcuxuheze4x2raqfjyncxxrux794wgz2c8lex2my50mxw56jfn7nl88j5dfz3f42y02hahss973geqrugesq0m7qe8ft2g"); // Sender
        txnOutput.setValue(new Value(new BigInteger("50000"), null));

        TransactionOutput changeOutput =  new TransactionOutput();
        changeOutput.setAddress("addr_test1qqv5auuws3t8x2qjccc3u5g0tfcvwjnjvantxs83k6p8kmvjzgrfuhh95jay0p0he8kmxgsfh7s0m6jyjhnyw4azpwxs0ay5zm"); // Receiver
        changeOutput.setValue(new Value(new BigInteger("9892670"), null));

        List<TransactionOutput> outputs = new ArrayList<>();
        outputs.add(txnOutput);
        outputs.add(changeOutput);

        txnBody.setOutputs(outputs);
        txnBody.setFee(new BigInteger("36795"));
        txnBody.setTtl(26194586);

        Transaction transaction = new Transaction();
        transaction.setBody(txnBody);
        String hexStr = transaction.serializeToHex();
        System.out.println(hexStr);

        return hexStr;
    }
}

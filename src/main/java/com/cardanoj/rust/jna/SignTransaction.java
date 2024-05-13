package com.cardanoj.rust.jna;

import com.cardanoj.rust.common.model.Networks;
import com.cardanoj.rust.coreapi.account.Account;
import com.cardanoj.rust.exception.CborSerializationException;
import com.cardanoj.rust.util.HexUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SignTransaction {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // Fetch the rawTxnHex value from RawTxnHex class
            String rawTxnInHex = RawTransactionHex.rawTxnHexGeneration();
            System.out.println("Raw Txn Hex: " + rawTxnInHex);

            // Generate sender's account from Mnemonic
            String mnemonic = "brother verify rabbit join misery retire mother surprise game ignore main chase crowd captain miracle coast inflict half margin bid toy stairs right plastic";
            // System.out.println("Enter Mnemonic: ");
            // String mnemonic = sc.nextLine();
            Account signingAccount = new Account(Networks.testnet(), mnemonic);

            // Get Bech32 private key from account
            String privateKey = signingAccount.getBech32PrivateKey();

            // Sign the transaction
            String signedTxnHex = JNAUtil.sign(rawTxnInHex, privateKey);
            System.out.println("Signed Txn Hex: " + signedTxnHex);

            byte[] signedTxnBytes = HexUtil.decodeHexString(signedTxnHex);
            if(signedTxnBytes.length > 100) {
//                System.out.println("Enter the file name for saving signed transaction bytes: ");
//                String fileName = sc.nextLine();
//                fileName += ".raw";
//                String directoryPath = "src/main/resources/test/";
//                String filePath = directoryPath + fileName;

                String outputFile = "src/main/resources/test/signedTxnByte.raw";

//                try (FileWriter writer = new FileWriter(filePath)) {
                try (FileWriter writer = new FileWriter(outputFile)) {
                    for (byte b : signedTxnBytes) {
                        writer.write(Integer.toHexString(b & 0xff)); // Write byte as hexadecimal string
                    }
                    System.out.println("--> Signed Txn Bytes as .raw file generated successfully!"); // CBOR Hex
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("Invalid Signed Txn Bytes!");
            }
        } catch (CborSerializationException e) {
            e.printStackTrace();
        }
        sc.close();
    }
}

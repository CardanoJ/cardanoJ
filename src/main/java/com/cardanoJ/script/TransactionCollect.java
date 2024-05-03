package com.cardanoJ.script;

import java.io.*;
import java.nio.file.Files;

public class TransactionCollect {

    //  Query the protocol parameters
    private String queryProtocolParam(String cliPath, String resourcePath, String network, String socketPath){
        String protocolParam = resourcePath + "protocol-parameters.json";
        socketPath = "/home/tarachand/preview/node.socket";

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "protocol-parameters",
                    network, "2",
                    "--socket-path", socketPath,
                    "--out-file",protocolParam
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            File txFile = new File(protocolParam);
            if (txFile.exists()) {
                System.out.println("protocol parameters generated");
            } else {
                System.err.println("Error: Failed to generate protocol parameters.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    return protocolParam;
    }


    public String submitTransaction(String cliPath, String resourcePath, String network,String senderName){
        String tx = "";
//        String txPath = (resourcePath + senderName+".tx").toString();
        String txPath = (resourcePath + "collectgift"+".tx");
        String socketPath = "/home/tarachand/preview/node.socket";  //define your own Cardano Node path

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "submit", network, "2", "--tx-file",txPath,"--socket-path",socketPath
            );
            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitcode = process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            if (exitcode == 0){
                System.out.println("Executed Successfully");

                //Geting Transaction ID
                ProcessBuilder processBuilderTXID = new ProcessBuilder(
                        cliPath,"transaction","txid","--tx-file",txPath
                );
                System.out.println("Commands: " + processBuilderTXID.command());
                processBuilderTXID.redirectErrorStream(true);
                Process processTXID = processBuilderTXID.start();
                int exitcodeTXID = processTXID.waitFor();
                BufferedReader readerTXID = new BufferedReader(new InputStreamReader(processTXID.getInputStream()));
                String lineTXID;
                while ((lineTXID = readerTXID.readLine()) != null) {
                    System.out.println("Transaction ID: " + lineTXID);
                    System.out.println("Cardanoscan: https://preview.cardanoscan.io/transaction/" + lineTXID);
                    tx = lineTXID;
                }
                if (exitcodeTXID == 0){
                    System.out.println("Transaction ID Generated SuccessFully");
                }
            }else {
                System.err.println("Error while generating transaction ID");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return tx;
    }

    //    # Sign the transaction
    public void signTransaction(String cliPath, String resourcePath, String network, String reciverName ){
        String bodyPath = resourcePath + "collectgift"+".txbody";
        String txPath = resourcePath + "collectgift"+".tx";
        String signKeyPath = "src/main/resources/assets/"+reciverName+".skey";
        String socketPath = "/home/tarachand/preview/node.socket";  // define your own cardano Node path
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "sign",
                    "--tx-body-file",bodyPath,
                    "--signing-key-file",signKeyPath,
                    network, "2",
                    "--out-file",txPath
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            File txFile = new File(txPath);
            if (txFile.exists()) {
                System.out.println("Signing TX file generated");
            } else {
                System.err.println("Error: Failed to generate signing TX.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //    # Build the transaction
    public String buildTransaction(String cliPath, String resourcePath, String address, String receiver, String network, int lovelace,String senderName, String datumValue,String socketPath) {
        String paymentScriptFileAddress = queryProtocolParam(cliPath,resourcePath,network,socketPath);
        String txINN = getTransactionDetails(cliPath, resourcePath, address, network);
        String tot = receiver + "+" + lovelace+" lovelace";
        String bodyPath = resourcePath + "collectgift"+".txbody";
        socketPath = "/home/tarachand/preview/node.socket";    //define your own cardano Node path
        String paymentScriptFile = "src/main/resources/assets/gift.plutus";
        String pp = queryProtocolParam(cliPath,resourcePath,network,socketPath);
        String collateral = "9550578843772c5ac311be85e90e9d673039dfc356e2fe8d79c4f5d6be5aaa7e#0";

        String scriptAddress = "";
        try (BufferedReader br = new BufferedReader(new FileReader(paymentScriptFileAddress))) {
            String line;
            while ((line = br.readLine()) != null) {
                scriptAddress = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        scriptAddress = scriptAddress + "+" + lovelace+" lovelace";


        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build-raw",
//                    "--socket-path", socketPath,
                    "--babbage-era",
                    "--tx-in", txINN,
                    "--tx-in-script-file", paymentScriptFile,
                    "--tx-in-inline-datum-present",
//                    "--tx-out", scriptAddress,
                    "--tx-in-redeemer-file", "src/main/resources/assets/units.json",
//                    "--tx-out-inline-datum-value", datumValue,
                    "--tx-in-collateral", collateral,
//                    "--change-address", receiver,
                    "--tx-out", receiver + "+" + lovelace,
                    "--protocol-params-file", pp,
                    "--tx-in-execution-units (INT, INT)",
                    "--out-file", bodyPath
            );
            System.out.println("Build Transaction command: "+processBuilder.command());

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            String processOutput = output.toString();

            // Print the process output
            System.out.print("Process output:");
            System.out.println(processOutput);

            reader.close();


            File bodyFile = new File(bodyPath);
            if (bodyFile.exists()) {
                return bodyPath;
            } else {
                System.err.println("Error: Failed to generate transaction body.");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getTransactionDetails(String cliPath, String resourcePath, String address, String network) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--address", address,
                    network.contains("testnet") ? "--testnet-magic" : network,
                    "2",
                    "--socket-path", "/home/tarachand/preview/node.socket" // define your own cardano Node path
            );
            System.out.println("getTransactionDetails: "+ processBuilder.command());
            processBuilder.redirectOutput(new File(resourcePath + "getTransactionDetails.txt"));

            Process process = processBuilder.start();
            process.waitFor();

            return getTransaction(resourcePath + "getTransactionDetails.txt");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getTransaction(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            reader.readLine();
            String line;

            long maxLovelace = 0;
            String maxTransactionHash = null;
            String maxTransactionIx = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                long lovelace = parseLovelace(parts[2]);

                if (lovelace > maxLovelace){
                    maxLovelace = lovelace;
                    maxTransactionHash = parts[0];
                    maxTransactionIx = parts[1];
                }
            }
            return maxTransactionHash + "#" + maxTransactionIx;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static long parseLovelace(String amount) {
        String[] tokens = amount.split("\\s+");
        return Long.parseLong(tokens[0]);
    }



}
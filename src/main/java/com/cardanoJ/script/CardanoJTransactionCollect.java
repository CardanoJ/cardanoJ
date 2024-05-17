package com.cardanoJ.script;

import java.io.*;

public class CardanoJTransactionCollect {

    private String scriptLovelace = "";

    //  Query the protocol parameters
    public String queryProtocolParam(String cliPath, String resourcePath, String network, String networkId, String socketPath){
        String protocolParam = resourcePath + "protocol-parameters.json";

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "protocol-parameters",
                    network, networkId,
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


    public String submitTransaction(String cliPath, String network, String networkId,String signedTX, String socketPath){
        String tx = "";

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "submit", network, networkId, "--tx-file",signedTX, "--socket-path",socketPath
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
                        cliPath,"transaction","txid","--tx-file",signedTX
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
    public String signTransaction(String cliPath, String resourcePath, String network, String networkId, String txBuild, String receiverAddress){

        String txPath = resourcePath + receiverAddress + ".signed";
        String signKeyPath = "src/main/resources/assets/fees.skey"; //Collateral Signing Key
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "sign",
                    "--tx-body-file",txBuild,
                    "--signing-key-file",signKeyPath,
                    network, networkId,
                    "--out-file",txPath
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            File txFile = new File(txPath);
            if (txFile.exists()) {
                System.out.println("Signing TX file generated");
                return txPath;
            } else {
                System.err.println("Error: Failed to generate signing TX.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    //    # Build the transaction
    public String buildTransaction(String cliPath, String resourcePath, String scriptAddress,  String collateral, String scriptUTXO, String datumValue, String redeemerValue, String scriptFile, String feeAddress, String receiverAddress, String network, String networkId, String socketPath) {

        collateral = getTransactionDetails(cliPath, resourcePath, feeAddress, network, networkId);
        String scriptUtxoId = fetchScriptUTXO(cliPath,scriptAddress,network,networkId,socketPath,scriptUTXO);
        String receiver = receiverAddress +"+"+ scriptLovelace; // --Fix it its not right
        String calculateDatumHash = datumHashFromValue(cliPath,resourcePath, datumValue);
        String txBuild = resourcePath + receiverAddress + ".build";

        System.out.println("collateral : " + collateral);


        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--tx-in", collateral,
                    "--tx-in", scriptUtxoId,
                    "--tx-in-datum-value", datumValue,
                    "--tx-in-redeemer-value", redeemerValue,
                    "--tx-in-script-file", scriptFile,
                    "--tx-in-collateral", collateral,
                    "--change-address", feeAddress,
                    "--tx-out", receiver,
                    "--tx-out-datum-hash", calculateDatumHash,
                    "--out-file", txBuild,
                    network, networkId,
                    "--babbage-era", "--socket-path", socketPath
            );
            System.out.println("Build Transaction command: " + processBuilder.command());
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


            File bodyFile = new File(txBuild);
            if (bodyFile.exists()) {
                return txBuild;
            } else {
                System.err.println("Error: Failed to generate transaction body.");
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }


    private String getTransactionDetails(String cliPath, String resourcePath, String address, String network, String networkId) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--address", address,
                    network, networkId,
                    "--socket-path", "/home/tarachand/preview/node.socket" // define your own cardano Node path
            );
            System.out.println("getTransactionDetails: "+ processBuilder.command());
            processBuilder.redirectOutput(new File(resourcePath + address + "TransactionDetails.txt"));

            Process process = processBuilder.start();
            process.waitFor();

            return getTransaction(resourcePath + address + "TransactionDetails.txt");
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
                maxTransactionHash = parts[0];
                maxTransactionIx = parts[1];
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



    public String datumHashFromValue(String cliPath, String resourcePath,String datumValue){
        String datumHash = "";
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction",
                    "hash-script-data",
                    "--script-data-value", datumValue
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            datumHash = reader.readLine(); // Assuming the datum hash is returned as the first line of output

            System.out.println("Datum Hash: " + datumHash);
            process.waitFor();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datumHash;
    }

    private String fetchScriptUTXO(String cliPath, String scriptAddress, String network, String networkId, String socketPath, String utxo) {
        String transactionHash = "";
        String transactionId = "";
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--address", scriptAddress,
                    network, networkId,
                    "--socket-path", socketPath
            );

            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;


                while ((line = reader.readLine()) != null) {
                    String[] parts = line.trim().split("\\s+");

                    if (utxo.equals(parts[0])) {
                        transactionHash = parts[0];
                        transactionId = parts[1];
                        scriptLovelace = parts[2];

                        break;
                    }
                }
                System.out.println("FetchUTXO : " + transactionHash + "#" + transactionId);
                return transactionHash + "#" + transactionId;
            }catch (Exception e){
                e.printStackTrace();
            }


        }catch (Exception e){
            e.printStackTrace();
        }

        return transactionHash + "#" + transactionId;
    }



}
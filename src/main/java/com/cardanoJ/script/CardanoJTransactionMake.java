package com.cardanoJ.script;

import java.io.*;


public class CardanoJTransactionMake {

    //  Build Script address
    public String addressBuild(String cliPath, String resourcePath, String network){
        String paymentScriptFile = "src/main/resources/assets/AlwaysSucceeds.plutus";
        String paymentScriptFileAddress = resourcePath + "AlwaysSucceeds.addr";

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "address", "build",
                    "--payment-script-file",paymentScriptFile,
                    network, "2",
                    "--out-file",paymentScriptFileAddress
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            File txFile = new File(paymentScriptFileAddress);
            if (txFile.exists()) {
                System.out.println("Payment Script Address generated Successfully");
                BufferedReader reader = new BufferedReader(new FileReader(txFile));
                return reader.readLine();
            } else {
                System.err.println("Error: Failed to generate Payment Script Address.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;

    }


    public String datumHashFromValue(String cliPath, String resourcePath, String network, String datumValue){
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

    public String submitTransaction(String cliPath,String network, String networkId, String txSigned){
        String tx = "";
        String socketPath = "/home/tarachand/preview/node.socket";  //define your own Cardano Node path

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath,
                    "transaction",
                    "submit",
                    "--tx-file", txSigned,
                    network, networkId,
                    "--socket-path", socketPath
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
                        cliPath,
                        "transaction", "txid",
                        "--tx-file",txSigned
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
    public String signTransaction(String cliPath, String resourcePath, String network, String networkId, String senderName, String scriptAddress){
        String bodyPath = resourcePath + scriptAddress + ".build";
        String txPath = resourcePath + scriptAddress+".signed";
        String signKeyPath = "src/main/resources/assets/"+senderName+".skey";
        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "sign",
                    "--tx-body-file",bodyPath,
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
            } else {
                System.err.println("Error: Failed to generate signing TX.");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return txPath;
    }


//    # Build the transaction
    public String buildTransaction(String cliPath, String resourcePath, String senderAddress, String scriptAddress,String network, String networkID, int lovelace, String senderName, String datumHash) {
        String txINN = getTransactionDetails(cliPath, resourcePath, senderAddress, networkID);
        String tot = scriptAddress + "+" + lovelace;
        String bodyPath = "src/main/resources/assets/" + scriptAddress + ".build";
        String socketPath = "/home/tarachand/preview/node.socket";    //define your own cardano Node path



        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--tx-in", txINN,
                    "--tx-out", tot,
                    "--tx-out-datum-hash", datumHash,
                    "--change-address", senderAddress,
                    "--testnet-magic", networkID,
                    "--out-file", bodyPath,
                    "--babbage-era",
                    "--socket-path", socketPath
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

    private String getTransactionDetails(String cliPath, String resourcePath, String address, String networkId) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--address", address,
                    "--testnet-magic", networkId,
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
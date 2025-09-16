package com.cardanoJ.transaction;

import java.io.*;

public class CardanoJTransaction {
    public String submitTransaction(String cliPath, String socketPath, String resourcePath, String network, String networkId, String senderName) {
        String tx = "";
        String txPath = (resourcePath + senderName + "_signed.tx");

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "conway", "transaction", "submit",
                    "--tx-file", txPath,
                    network.contains("testnet") ? "--testnet-magic" : "--mainnet", networkId,
                    "--socket-path", socketPath
            );
            System.out.println("command: " + processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitcode = process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            if (exitcode == 0) {
                System.out.println("Executed Successfully");

                //Geting Transaction ID
                ProcessBuilder processBuilderTXID = new ProcessBuilder(
                        cliPath, "conway", "transaction", "txid", "--tx-file", txPath
                );
                System.out.println("Commands: " + processBuilderTXID.command());
                processBuilderTXID.redirectErrorStream(true);
                Process processTXID = processBuilderTXID.start();
                int exitcodeTXID = processTXID.waitFor();
                BufferedReader readerTXID = new BufferedReader(new InputStreamReader(processTXID.getInputStream()));
                String lineTXID;
                while ((lineTXID = readerTXID.readLine()) != null) {
                    System.out.println("Transaction ID: " + lineTXID);
                    if(network.contains("testnet")) {
                        if(networkId.equals("1")) {
                            System.out.println("Cardanoscan: https://preprod.cardanoscan.io/transaction/" + lineTXID);
                        } else if(networkId.equals("2")) {
                            System.out.println("Cardanoscan: https://preview.cardanoscan.io/transaction/" + lineTXID);
                        }
                    }
                    tx = lineTXID;
                }
                if (exitcodeTXID == 0) {
                    System.out.println("Transaction ID Generated SuccessFully");
                }
            } else {
                System.err.println("Error while generating transaction ID");
                throw new RuntimeException("Error while generating transaction ID");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return tx;
    }

    public String signTransaction(String cliPath, String resourcePath, String network, String networkId, String name) {
        String bodyPath = resourcePath + name + "_build.txbody";
        String txPath = resourcePath + name + "_signed.tx";
        String signKeyPath = resourcePath + name + ".skey";
        File signKey = new File(signKeyPath);
        if (!signKey.exists()) {
            throw new IllegalArgumentException("Signing key file not found: " + signKeyPath);
        }
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "conway", "transaction", "sign",
//                    "--tx-body-file", bodyPath,
                    "--tx-file", bodyPath,
                    "--signing-key-file", signKeyPath,
                    network.contains("testnet") ? "--testnet-magic" : "--mainnet", networkId,
                    "--out-file", txPath
            );

            System.out.println("command: " + processBuilder.command());
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

    public String buildTransaction(String cliPath, String socketPath, String resourcePath, String senderAddress, String receiverAddress, String network, String networkId, long lovelace, String senderName, String datumValue) {
        String txINN = getTransactionDetails(cliPath, socketPath, resourcePath, senderAddress, network, networkId);
        System.out.println("txINN: " + txINN);
        if (txINN == null || txINN.equals("null#null")) {
            throw new RuntimeException("No UTxO found for address. Fund it before building transactions.");
        }
        String tot = receiverAddress + "+" + lovelace + " lovelace";
        String bodyPath = resourcePath + senderName + "_build.txbody";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "conway", "transaction", "build",
                    "--socket-path", socketPath,
//                    "--babbage-era",
                    network.contains("testnet") ? "--testnet-magic" : "--mainnet", networkId,
                    "--tx-in", txINN,
                    "--tx-out", tot,
//                    "--tx-out-inline-datum-file", "src/main/resources/assets/units.json",
                    "--tx-out-inline-datum-value", datumValue,
                    "--change-address", senderAddress,
                    "--out-file", bodyPath
            );
            System.out.println("command: " + processBuilder.command());

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

    public String getTransactionDetails(String cliPath, String socketPath, String resourcePath, String address, String network, String networkId) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--address", address,
                    network.contains("testnet") ? "--testnet-magic" : "--mainnet", networkId,
                    "--socket-path", socketPath,
                    "--output-text"
            );
            System.out.println("getTransactionDetails: " + processBuilder.command());
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

                if (lovelace > maxLovelace) {
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

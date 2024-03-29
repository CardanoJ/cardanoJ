package org.cardanoJ.transaction;

import java.io.*;

public class Transaction {
    public String submitTransaction(String cliPath, String resourcePath, String network,String senderName){
        String tx = "";
        String txPath = (resourcePath + senderName+".tx").toString();
        String socketPath = "/home/quotus/preview/node.socket";  //define your own Cardano Node path

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
    public void signTransaction(String cliPath, String resourcePath, String network, String name ){
        String bodyPath = resourcePath + name+".txbody";
        String txPath = resourcePath + name+".tx";
        String signKeyPath = "src/main/resources/assets/"+name+".skey";
        String socketPath = "/home/quotus/preview/node.socket";  // define your own cardano Node path
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

    public String buildTransaction(String cliPath, String resourcePath, String address, String receiver, String network, int lovelace,String senderName) {
        String txINN = getTransactionDetails(cliPath, resourcePath, address, network);
        String tot = receiver + "+" + lovelace+" lovelace";
        String bodyPath = resourcePath + senderName+".txbody";
        String socketPath = "/home/quotus/preview/node.socket";    //define your own cardano Node path


        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--socket-path", socketPath,
                    "--babbage-era", network, "2",
                    "--tx-in", txINN,
                    "--tx-out", tot,
                    "--tx-out-inline-datum-file", resourcePath + "unit.json",
                    "--change-address", address,
                    "--out-file", bodyPath
            );
            System.out.println("command: "+processBuilder.command());

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
                    "--socket-path", "/home/quotus/preview/node.socket" // define your own cardano Node path
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
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                return parts[0] + "#" + parts[1];
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

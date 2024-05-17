package com.cardanoJ.stake;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class CardanoJRegisterAndDelegate {
    public String stakeAddress(String cliPath) {
        String address = "src/main/resources/assets/user1scriptstake.addr";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "stake-address", "build",
                    "--testnet-magic", "2",
                    "--stake-script-file", "src/main/resources/assets/staking.plutus",
                    "--out-file", address
            );
            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return address;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String stakeAddressBuild(String cliPath) {
        String address = "src/main/resources/assets/user1script.addr";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "address", "build",
                    "--testnet-magic", "2",
                    "--payment-verification-key-file", "src/main/resources/assets/test.vkey",
                    "--stake-script-file", "src/main/resources/assets/staking.plutus",
                    "--out-file", address
            );
            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return address;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String stakeAddressRegCert(String cliPath) {
        String regCert = "src/main/resources/assets/registration.cert";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "stake-address", "registration-certificate",
                    "--stake-script-file", "src/main/resources/assets/staking.plutus",
                    "--out-file", regCert
            );
            System.out.println("Command: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();
            return regCert;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String stakeAddressDelCert(String cliPath, String first) {
        String delCert = "src/main/resources/assets/delegation.cert";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "stake-address", "delegation-certificate",
                    "--stake-script-file", "src/main/resources/assets/staking.plutus",
                    "--stake-pool-id", first,
                    "--out-file", delCert
            );
            System.out.println("Command: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();
            return delCert;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public String queryProtocolParam(String cliPath, String socketPath){
        String protocolParam = "src/main/resources/assets/protocol-parameters.json";

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "protocol-parameters",
                    "--testnet-magic", "2",
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

    public String buildTransaction(String cliPath, String txIn, String socketPath) {
        String regCert = "src/main/resources/assets/registration.cert";
        String delCert = "src/main/resources/assets/delegation.cert";
        String body = "src/main/resources/assets/tx.txbody";
        String scriptFile = "src/main/resources/assets/staking.plutus";
        String redeemerFile = "src/main/resources/assets/units.json";
        String pp = "src/main/resources/assets/protocol-parameters.json";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--babbage-era",
                    "--testnet-magic", "2",
                    "--change-address", "addr_test1vzc22rfmfpgshyp60n4ev0s3j5svz3r9q5mzutvcaznu5gssaw6g7",
                    "--out-file", body,
                    "--tx-in", txIn, //b1027ff1545eccfe2bbf4489337ed5b0083b9f3800d5c67af020d59dd1ce#1
                    "--tx-in-collateral",txIn,
                    "--certificate-file",regCert,
                    "--certificate-file",delCert,
                    "--certificate-script-file", scriptFile,
                    "--certificate-redeemer-file", redeemerFile,
                    "--socket-path", socketPath

            );

            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();

            return body;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String signTransaction(String cliPath, String socketPath) {
        String body = "src/main/resources/assets/tx.txbody";
        String signingKeyFile = "src/main/resources/assets/test.skey";
        String txSigned = "src/main/resources/assets/tx.signed";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "sign",
                    "--testnet-magic", "2",
                    "--tx-body-file", body,
                    "--out-file",txSigned,
                    "--signing-key-file", signingKeyFile
            );

            System.out.println("Signed: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();

            return txSigned;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String submitTransaction(String cliPath, String socketPath){
        String tx = "";
        String txPath = "src/main/resources/assets/tx.signed";

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "submit", "--testnet-magic", "2", "--tx-file",txPath,"--socket-path",socketPath
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


    private static String getResourcePath() {
        return CardanoJRegisterAndDelegate.class.getClassLoader().getResource("").getPath();
    }



}

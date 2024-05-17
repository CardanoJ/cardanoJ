package com.cardanoJ.stake;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CardanoJWithdrawUser {

    public String buildTransaction(String cliPath, String txIn, String socketPath) {
        String body = "src/main/resources/assets/withdrawTx.txbody";
        String scriptFile = "src/main/resources/assets/staking.plutus";
        String redeemerFile = "src/main/resources/assets/units.json";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--babbage-era",
                    "--testnet-magic", "2",
                    "--change-address", "addr_test1yzc22rfmfpgshyp60n4ev0s3j5svz3r9q5mzutvcaznu5gs8phtwuxk0fw226lwaujsmy0v3hxr7z3uajs5r5hmddx4sekdxvx",
                    "--out-file", body,
                    "--tx-out", "addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2+500000000",
                    "--tx-in", txIn, //b1027ff1545eccfe2bbf4489337ed5b0083b9f3800d5c67af020d59dd1ce#1
                    "--tx-in-collateral",txIn,
                    "--withdrawal", "stake_test17qrsm4hwrt85h99d0hw7fgdj8kgmnplpg7weg2p6takkn2ck8prnj+900000000",
                    "--withdrawal-script-file", scriptFile,
                    "--withdrawal-redeemer-file", redeemerFile,
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
        String body = "src/main/resources/assets/withdrawTx.txbody";
        String signingKeyFile = "src/main/resources/assets/test.skey";
        String txSigned = "src/main/resources/assets/withdrawTx.signed";
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
        String txPath = "src/main/resources/assets/withdrawTx.signed";

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
        return CardanoJWithdrawUser.class.getClassLoader().getResource("").getPath();
    }



}

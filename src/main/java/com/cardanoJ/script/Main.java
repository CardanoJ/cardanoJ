package com.cardanoJ.script;


import com.cardanoJ.util.CalculateFee;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static String cliPath;
    public static String os;
    public static String resourcePath;
    public static String network;
    public static String networkId;
    public static String socketPath;



    private static void setCliPath(){
        // Initialize cliPath and os
        os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows path
            cliPath = "src/main/resources/bin/cardano-address.exe";
        } else {
            // Unix/Linux/MacOS command
            cliPath = "src/main/resources/bin/cardano-cli"; // Assuming the executable for Unix/Linux doesn't have '.exe'
            givingPermissionToCAcli();
        }
        resourcePath = getResourcePath();

    }
    private static String getResourcePath() {
        return Main.class.getClassLoader().getResource("").getPath();
    }

    private static void givingPermissionToCAcli(){
        ProcessBuilder processBuilder = new ProcessBuilder("chmod", "+x", cliPath);
        try{
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("Exited with code : " + exitCode);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {

        setCliPath();
        network = "--testnet-magic";
        networkId = "2";
        socketPath = "/home/tarachand/preview/node.socket";
        String datumValue = "6666";
        int lovelace = 100000000;

        TransactionMake tm = new TransactionMake();
        CalculateFee cf = new CalculateFee();
        TransactionCollect tc = new TransactionCollect();
        String scriptAddress = tm.addressBuild(cliPath,resourcePath,network);
        String datumHash = tm.datumHashFromValue(cliPath,resourcePath,network,datumValue);
        String txBuild = tm.buildTransaction(cliPath,resourcePath, "addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2", scriptAddress, network,networkId, 100000000,"sender", datumHash);
        String fee =  cf.calculateMinimumFee(cliPath,network, networkId, txBuild,tc.queryProtocolParam(cliPath,resourcePath,network,networkId,socketPath),"1","2","0");
        String txSigned = tm.signTransaction(cliPath,resourcePath,network,networkId, "sender", scriptAddress);
//        String txID = tm.submitTransaction(cliPath,network,networkId,txSigned);


        //Contract to receiver

        String build = tc.buildTransaction(cliPath,resourcePath,scriptAddress,"","79e5a6171362590a211e9cf91555c7004d26979c99e369f95d1cb11fdfe0d7b0","6666","42","src/main/resources/assets/AlwaysSucceeds.plutus","addr_test1vq97lwwsv32lty8u0n6vzlf0f3ah5rhpf43gjcjccp0l8gck54upq","addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk",network,networkId,socketPath);

        String signed = tc.signTransaction(cliPath,resourcePath,network,networkId,build,"addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk");

        String tXiD = tc.submitTransaction(cliPath,network,networkId,signed,socketPath);

        String txID = "null";
        System.out.println("TXBUILD : " + txBuild + " \nTXSIGNED : " + txSigned + " \ntxID : " + txID);


        System.out.println("Build : " + build);
        System.out.println("Signed : " + signed);
        System.out.println("TxID : " + tXiD);

    }
}

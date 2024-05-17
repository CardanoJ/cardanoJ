package com.cardanoJ.transaction;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class CardanoJBuildTransaction {
    public static String cliPath;
    public static String caddrPath;
    public static String os;
    public static String resourcePath;
    public void query() {
        String network = "";
        long result = 0;
        setCliPath();
        resourcePath = getResourcePath();
        CardanoJQueryAddress q = new CardanoJQueryAddress();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.println("Choose the Network. \n1. Testnet-magic \n2. Mainnet \n3. Exit");
        int a = scanner.nextInt();
        switch (a) {
            case 1:
                network = "--testnet-magic";
                result = q.queryAddress(cliPath, address, network);
                break;
            case 2:
                network = "--mainnet";
                result = q.queryAddress(cliPath, address, network);
//                result = q.queryAddress(cliPath, resourcePath, address, network);
                break;
            case 3:
                exit(0);
            default:
                System.out.println("You Have to choose the network");

        }
        System.out.println("Total : " + result + " Lovelace ( " + (result/1000000) + " ADA)");
    }

    public void transactionSession(){
        String network = "";
        CardanoJTransaction tr = new CardanoJTransaction();
        String result = "";
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter sender Address: ");
        String senderAddress = scanner.nextLine();
        System.out.print("Enter sender Name: ");
        String senderName = scanner.nextLine();
        System.out.print("Enter Receiver Address: ");
        String receiverAddress = scanner.nextLine();
        System.out.print("Enter the Lovelace (3 ADA = 3_000_000lovelace): ");
        int lovelace = scanner.nextInt();

        CardanoJCreateDatum dat = new CardanoJCreateDatum();
        String datum = dat.create(senderAddress,receiverAddress,lovelace,resourcePath);

        System.out.println("Choose the Network. \n1. Testnet-magic \n2. Mainnet \n3. Exit");
        int a = scanner.nextInt();
        switch (a){
            case 1:
                network = "--testnet-magic";
                tr.buildTransaction(cliPath,resourcePath,senderAddress,receiverAddress,network,lovelace,senderName,datum);
                tr.signTransaction(cliPath,resourcePath,network,senderName);
                result = tr.submitTransaction(cliPath,resourcePath,network,senderName);
                break;
            case 2:
                network = "--mainnet";
                tr.buildTransaction(cliPath,resourcePath,senderAddress,receiverAddress,network,lovelace,senderName,datum);
                tr.signTransaction(cliPath,resourcePath,network,senderName);
                result = tr.submitTransaction(cliPath,resourcePath,network,senderName);
                break;
            case 3:
                exit(0);
            default:
                System.out.println("You Have to choose the network");

        }

    }
    public void transact() {
        // Initialize cliPath and os
        os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows path
            cliPath = "src/main/resources/bin/cardano-cli.exe";
        } else {
            // Unix/Linux/MacOS command
            cliPath = "src/main/resources/bin/cardano-cli";
            givingPermissionToCAcli();
        }
        resourcePath = getResourcePath();

        System.out.println();

        CardanoJBuildTransaction cardanoJBuildTransaction = new CardanoJBuildTransaction();
        cardanoJBuildTransaction.transactionSession();

    }

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
    }
    private static String getResourcePath() {
        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
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
}

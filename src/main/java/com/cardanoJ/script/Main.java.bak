package com.cardanoJ.script;

import com.cardanoJ.transaction.CreateDatum;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static String cliPath;
    public static String os;
    public static String resourcePath;

    public void transactionSession(){
        String socketPath = "/home/tarachand/preview/node.socket";    //define your own cardano Node path
        String receiverName = "bob";

        String network = "";
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

        CreateDatum dat = new CreateDatum();
        String datum = dat.create(senderAddress,receiverAddress,lovelace,resourcePath);
//        TransactionMake tm = new TransactionMake();
        TransactionCollect tc = new TransactionCollect();
        System.out.println("Choose the Network. \n1. Testnet-magic \n2. Mainnet \n3. Exit");
        int a = scanner.nextInt();
        switch (a){
            case 1:
                network = "--testnet-magic";
//                tm.buildTransaction(cliPath,resourcePath,senderAddress,receiverAddress,network,lovelace,senderName,datum);
                tc.buildTransaction(cliPath,resourcePath,senderAddress,receiverAddress,network,lovelace,senderName,datum,socketPath);
//                tm.signTransaction(cliPath,resourcePath,network,senderName);
                tc.signTransaction(cliPath,resourcePath,network,receiverName);
//                result = tm.submitTransaction(cliPath,resourcePath,network,senderName);
                result = tc.submitTransaction(cliPath,resourcePath,network,senderName);
                break;
            case 2:
                network = "--mainnet";
//                tm.buildTransaction(cliPath,resourcePath,senderAddress,receiverAddress,network,lovelace,senderName,datum);
//                tm.signTransaction(cliPath,resourcePath,network,senderName);
//                result = tm.submitTransaction(cliPath,resourcePath,network,senderName);
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
            cliPath = "src/main/resources/bin/cardano-address.exe";
        } else {
            // Unix/Linux/MacOS command
            cliPath = "src/main/resources/bin/cardano-cli"; // Assuming the executable for Unix/Linux doesn't have '.exe'
            givingPermissionToCAcli();
        }
        resourcePath = getResourcePath();

        System.out.println();

        Main m = new Main();
        m.transactionSession();
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
        return TransactionMake.class.getClassLoader().getResource("").getPath();
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
        Main m = new Main();
        m.transact();
    }
}

package org.cardanoJ.address;

import java.io.IOException;
import java.util.Scanner;

public class BuildAddress {
    public static String cliPath;
    public static String os;
    public static String resourcePath;

    public void addressGen() {
        Scanner scanner = new Scanner(System.in);

        // Get user's name
        System.out.print("Enter name: ");
        String userName = scanner.nextLine();

        // Path to the user's .vkey file
        String vkeyFilePath = "src/main/resources/assets/" + userName + ".vkey";    // set path accordingly

        // Path to the user's .skey file
        String skeyFilePath = "src/main/resources/assets/" + userName + ".skey";    // set path accordingly

        // Path to store the address file (.addr file)
        String addrFilePath = "src/main/resources/test/" + userName + ".addr";      // set path accordingly

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

        generateAddress(vkeyFilePath, skeyFilePath, addrFilePath);

        scanner.close();
    }

    private void generateAddress(String vkey, String skey, String addrFilePath) {
        try {
            ProcessBuilder keyGenProcessBuilder = new ProcessBuilder(
                    cliPath,
                    "address",
                    "key-gen",
                    "--verification-key-file",
                    vkey,
                    "--signing-key-file",
                    skey
            );
            Process keyGenProcess = keyGenProcessBuilder.start();
            keyGenProcess.waitFor();

            // Build address
            ProcessBuilder addressBuildProcessBuilder = new ProcessBuilder(
                    cliPath,
                    "address",
                    "build",
                    "--payment-verification-key-file",
                    vkey,
                    "--testnet-magic",
                    "2",
                    "--out-file",
                    addrFilePath
            );
            Process addressBuildProcess = addressBuildProcessBuilder.start();
            addressBuildProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getResourcePath() {
        return BuildAddress.class.getClassLoader().getResource("").getPath();
    }

    private void givingPermissionToCAcli() {
        ProcessBuilder processBuilder = new ProcessBuilder("chmod", "+x", cliPath);
        try {
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            System.out.println("--> Address File Generated Successfully.");
            System.out.println("Exited with code : " + exitCode);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
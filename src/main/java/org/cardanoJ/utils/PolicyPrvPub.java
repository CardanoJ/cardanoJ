package org.cardanoJ.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PolicyPrvPub {
    private String publicPolicy = "";
    private String privatePolicy = "";

    public String getPublicPolicy() {
        return publicPolicy;
    }

    public void setPubPolicy(String cliPath, String rootKey, String resourcePath, String os) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(resourcePath));

            if (os.contains("win")) {
                // For Windows
                processBuilder.command(cliPath, "key", "child", "1855H/1815H/0H");
            } else {
                // Unix/Linux/MacOS command
                processBuilder.command("key", "child", "1855H/1815H/0H");
            }

            // Redirect input from rootKey file
            processBuilder.redirectInput(new File(rootKey));
            File intermediateKeyFile = File.createTempFile("temp_key", ".xvk");
            intermediateKeyFile.deleteOnExit();
            processBuilder.redirectOutput(intermediateKeyFile);
            // Start the process to generate the intermediate key
            Process intermediateProcess = processBuilder.start();
            int intermediateExitCode = intermediateProcess.waitFor();

            if (intermediateExitCode == 0) {
                // Intermediate key generated successfully, proceed to generate public key
                ProcessBuilder publicKeyProcessBuilder = new ProcessBuilder();
                publicKeyProcessBuilder.directory(new File(resourcePath));

                if (os.contains("win")) {
                    // For Windows
                    publicKeyProcessBuilder.command(cliPath, "key", "public", "--without-chain-code");
                } else {
                    // Unix/Linux/MacOS command
                    publicKeyProcessBuilder.command(cliPath, "key", "public", "--without-chain-code");
                }

                // Redirect input from the intermediate key file
                publicKeyProcessBuilder.redirectInput(intermediateKeyFile);

                // Redirect output to policy.vk file
                File publicKeyFile = new File(resourcePath + "policy.vk");
                publicKeyProcessBuilder.redirectOutput(publicKeyFile);

                // Start the process to generate the public key
                Process publicKeyProcess = publicKeyProcessBuilder.start();
                int publicKeyExitCode = publicKeyProcess.waitFor();

                if (publicKeyExitCode == 0) {
                    System.out.println("Policy public key generated successfully.");

                    // Read the content of the public key file
                    StringBuilder publicKeyBuilder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new FileReader(publicKeyFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            publicKeyBuilder.append(line).append("\n");
                        }
                    }

                    // Set the public key content
                    publicPolicy = publicKeyBuilder.toString().trim();
                } else {
                    System.out.println("Error occurred while generating policy public key.");
                }
            } else {
                System.out.println("Error occurred while generating intermediate key.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getPrvPolicy() {
        return privatePolicy;
    }

    public void setPrvPolicy(String cliPath, String rootKey, String resourcePath, String os) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();

            if (os.contains("win")) {
                // For Windows
                processBuilder.command(cliPath, "key", "child", "1855H/1815H/0H");
            } else {
                // Unix/Linux/MacOS command
                processBuilder.command(cliPath, "key", "child", "1855H/1815H/0H");
            }

            processBuilder.redirectInput(new File(rootKey));
            // Create a temporary file for storing the policy private key
            File privateKeyFile = new File(resourcePath + "policy.xsk");
            // Redirect output to the temporary file
            processBuilder.redirectOutput(privateKeyFile);

            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Policy private key generated successfully.");

                // Read the content of the policy private key file
                StringBuilder privateKeyBuilder = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new FileReader(privateKeyFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        privateKeyBuilder.append(line).append("\n");
                    }
                }

                // Set the private key content
                privatePolicy = privateKeyBuilder.toString().trim();
            } else {
                System.out.println("Error occurred while generating policy private key.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

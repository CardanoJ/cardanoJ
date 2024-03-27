package com.org.cardanocli.backend.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PaymentKey {
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String cliPath, String rootKey, String resourcePath, String os) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(resourcePath));

            if (os.contains("win")) {
                // For Windows
                processBuilder.command(cliPath, "key", "child", "1852H/1815H/0H/0/0");
            } else {
                // Unix/Linux/MacOS command
                processBuilder.command(cliPath, "key", "child", "1852H/1815H/0H/0/0");
            }

            // Redirect input from rootKey file
            processBuilder.redirectInput(new File(rootKey));
            File intermediateKeyFile = File.createTempFile("key_temp", ".xvk");
            intermediateKeyFile.deleteOnExit();
            processBuilder.redirectOutput(intermediateKeyFile);
            // Start the process to generate the intermediate key
            Process intermediateProcess = processBuilder.start();
            int intermediateExitCode = intermediateProcess.waitFor();

            if (intermediateExitCode == 0) {
                // Intermediate key generated successfully, proceed to generate public key
                ProcessBuilder paymentProcessBuilder = new ProcessBuilder();
                paymentProcessBuilder.directory(new File(resourcePath));

                if (os.contains("win")) {
                    // For Windows
                    paymentProcessBuilder.command(cliPath, "key", "public", "--with-chain-code");
                } else {
                    // Unix/Linux/MacOS command
                    paymentProcessBuilder.command(cliPath, "key", "public", "--with-chain-code");
                }

                // Redirect input from the intermediate key file
                paymentProcessBuilder.redirectInput(intermediateKeyFile);

                // Redirect output to policy.xvk file
                File paymentKeyFile = new File(resourcePath + "addr.xvk");
                paymentProcessBuilder.redirectOutput(paymentKeyFile);

                // Start the process to generate the public key
                Process publicKeyProcess = paymentProcessBuilder.start();
                int publicKeyExitCode = publicKeyProcess.waitFor();

                if (publicKeyExitCode == 0) {
                    System.out.println("payment Verification key generated successfully.");

                    // Read the content of the public key file
                    StringBuilder paymentKeyBuilder = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new FileReader(paymentKeyFile))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            paymentKeyBuilder.append(line).append("\n");
                        }
                    }

                    // Set the public key content
                    key = paymentKeyBuilder.toString().trim();
                } else {
                    System.out.println("Error occurred while generating payment Verification key.");
                }
            } else {
                System.out.println("Error occurred while generating Intermediate verification key.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

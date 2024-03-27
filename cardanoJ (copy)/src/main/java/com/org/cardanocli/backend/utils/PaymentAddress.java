package com.org.cardanocli.backend.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PaymentAddress {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String cliPath, String paymentKey, String networkType, String resourcePath, String os) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(resourcePath));

            if (os.contains("win")) {
                processBuilder.command(cliPath, "key", "hash");
            } else {
                processBuilder.command(cliPath, "key", "hash");
            }

            processBuilder.redirectInput(new File(paymentKey));
            File intermediateKeyFile = File.createTempFile("add_temp", ".vkh");
            intermediateKeyFile.deleteOnExit();
            processBuilder.redirectOutput(intermediateKeyFile);
            Process intermediateProcess = processBuilder.start();
            int intermediateExitCode = intermediateProcess.waitFor();

            if (intermediateExitCode == 0) {
                generatePaymentAddress(cliPath, networkType, resourcePath, intermediateKeyFile, os);
            } else {
                System.out.println("Error occurred while generating intermediate address key.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generatePaymentAddress(String cliPath, String networkType, String resourcePath, File intermediateKeyFile,String os) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.directory(new File(resourcePath));

            if (os.contains("win")) {
                processBuilder.command(cliPath, "address", "payment", "--network-tag", networkType);
            } else {
                processBuilder.command(cliPath, "address", "payment", "--network-tag", networkType);
            }

            processBuilder.redirectInput(intermediateKeyFile);
            File paymentKeyFile = new File(resourcePath + "payment.address");
            processBuilder.redirectOutput(paymentKeyFile);
            Process paymentProcess = processBuilder.start();
            int publicKeyExitCode = paymentProcess.waitFor();

            if (publicKeyExitCode == 0) {
                System.out.println("Payment address generated successfully.");
                readPaymentAddress(paymentKeyFile);
            } else {
                System.out.println("Error occurred while generating payment address.");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readPaymentAddress(File paymentKeyFile) throws IOException {
        StringBuilder paymentKeyBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(paymentKeyFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                paymentKeyBuilder.append(line).append("\n");
            }
        }
        address = paymentKeyBuilder.toString().trim();
    }
}

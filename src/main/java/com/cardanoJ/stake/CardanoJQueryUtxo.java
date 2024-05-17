package com.cardanoJ.stake;

import com.cardanoJ.transaction.CardanoJConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CardanoJQueryUtxo {

    public long queryAddress(String cliPath, String address, String network) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--address", address,
                    network.contains("testnet") ? "--testnet-magic" : network,
                    "2",
                    "--socket-path", CardanoJConstant.SOCKET_PATH // define your own cardano Node path
            );
            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            long maxLovelace = query(reader);

            process.waitFor();
            return maxLovelace;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private long query(BufferedReader reader) {
        try {
            reader.readLine(); // Skip the first two lines
            reader.readLine();
            String line;
            long maxLovelace = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                long lovelace = parseLovelace(parts[2]);
                maxLovelace += lovelace; // Accumulate lovelace amounts
            }
            return maxLovelace;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static long parseLovelace(String amount) {
        String[] tokens = amount.split("\\s+");
        return Long.parseLong(tokens[0]);
    }}

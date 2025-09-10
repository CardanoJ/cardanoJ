package com.cardanoJ.transaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Utility class to query UTXOs for a given Cardano address.
 */
public class CardanoJUtxoQuery {

    /**
     * Queries the UTXOs for a given Cardano address and returns them as a list of maps.
     *
     * @param cliPath    path to cardano-cli
     * @param socketPath path to the node socket
     * @param address    Cardano address
     * @param network    network type ("testnet" or "mainnet")
     * @param networkId  network identifier (magic number or empty string for mainnet)
     * @return list of maps representing UTxOs
     */
    public List<Map<String, Object>> queryUtxos(
            String cliPath,
            String socketPath,
            String address,
            String network,
            String networkId
    ) {
        List<Map<String, Object>> utxoList = new ArrayList<>();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--address", address,
                    network.contains("testnet") ? "--testnet-magic" : "--mainnet", networkId,
                    networkId,
                    "--socket-path", socketPath
            );

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                // Skip headers
                reader.readLine();
                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 4) {
                        try {
                            Map<String, Object> utxo = new LinkedHashMap<>();
                            utxo.put("txHash", parts[0]);
                            utxo.put("txIx", Integer.parseInt(parts[1]));
                            utxo.put("amount", parts[2]);

                            // Join everything after the amount into additionalInfo
                            StringBuilder additionalInfo = new StringBuilder();
                            for (int i = 3; i < parts.length; i++) {
                                additionalInfo.append(parts[i]).append(" ");
                            }
                            utxo.put("additionalInfo", additionalInfo.toString().trim());

                            utxoList.add(utxo);
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping line due to invalid format: " + line);
                        }
                    }
                }
            }

            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return utxoList;
    }
}

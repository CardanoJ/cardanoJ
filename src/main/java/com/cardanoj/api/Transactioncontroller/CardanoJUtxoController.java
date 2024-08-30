package com.cardanoj.api.Transactioncontroller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.CardanoJTransactionDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

/**
 * REST controller for querying UTXOs for a given Cardano address.
 * <p>
 * This controller provides an endpoint to retrieve UTXO details by executing
 * a Cardano CLI command and parsing the output.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJUtxoController {

    private static final Logger logger = LoggerFactory.getLogger(CardanoJUtxoController.class);
    private static final int WAIT_TIMEOUT_SECONDS = 2; // Timeout for process wait

    /**
     * Endpoint to query UTXOs for a given address.
     * <p>
     * This method executes a Cardano CLI command to retrieve UTXO details for a specific address
     * and parses the command output into a list of transaction details.
     * </p>
     *
     * @param address the Cardano address to query
     * @return a list of transaction details
     */
    @GetMapping("/queryutxo/{address}")
    public List<CardanoJTransactionDetails> getOutput(@PathVariable String address) {
        String output = executeCommand(address); // Get transaction details from the command line
        return parseOutput(output);
    }

    /**
     * Parses the CLI command output into a list of transaction details.
     * <p>
     * This method processes each line of the output, skipping headers and empty lines,
     * and constructs `CardanoJTransactionDetails` objects.
     * </p>
     *
     * @param output the raw output from the CLI command
     * @return a list of parsed transaction details
     */
    private List<CardanoJTransactionDetails> parseOutput(String output) {
        List<CardanoJTransactionDetails> transactionDetailsList = new ArrayList<>();
        String[] lines = output.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty() || line.contains("TxHash") || line.contains("TxIx")) {
                continue;
            }

            String[] parts = line.trim().split("\\s+");
            if (parts.length >= 4) {
                try {
                    StringBuilder additionalInfo = new StringBuilder();
                    for (int i = 5; i < parts.length; i++) {
                        additionalInfo.append(parts[i]).append(" ");
                    }

                    int txID = Integer.parseInt(parts[1]);
                    double amount = Double.parseDouble(parts[2]);

                    CardanoJTransactionDetails details = new CardanoJTransactionDetails(
                            parts[0], txID, String.format("%.0f", amount), additionalInfo.toString().trim()
                    );

                    transactionDetailsList.add(details);
                } catch (NumberFormatException e) {
                    logger.error("Skipping line due to invalid format: " + line, e);
                }
            } else {
                logger.error("Skipping line due to invalid format: " + line);
            }
        }

        return transactionDetailsList;
    }

    /**
     * Executes a Cardano CLI command to query UTXOs for the specified address.
     * <p>
     * This method runs the command using `ProcessBuilder` and collects the output.
     * </p>
     *
     * @param address the Cardano address to query
     * @return the output from the CLI command
     */
    private String executeCommand(String address) {
        StringBuilder outputBuilder = new StringBuilder();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "query", "utxo",
                    "--socket-path", socketPath,
                    "--address", address,
                    TESTNET
            );

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    outputBuilder.append(line).append("\n");
                }
            }

            process.waitFor(WAIT_TIMEOUT_SECONDS, java.util.concurrent.TimeUnit.SECONDS);

        } catch (Exception e) {
            logger.error("Error executing command", e);
            throw new RuntimeException(e);
        }

        return outputBuilder.toString();
    }
}

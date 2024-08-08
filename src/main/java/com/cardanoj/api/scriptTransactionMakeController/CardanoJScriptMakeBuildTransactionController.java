package com.cardanoj.api.scriptTransactionMakeController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling requests related to building Cardano transactions
 * using script addresses and various parameters.
 * <p>
 * This controller provides an endpoint to build a transaction based on provided
 * parameters such as sender address, script address, lovelace amount, datum hash,
 * transaction hash, and transaction ID.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJScriptMakeBuildTransactionController {

    /**
     * Endpoint to build a Cardano transaction with the specified parameters.
     * <p>
     * The parameters include sender address, script address, amount of lovelace,
     * datum hash, transaction hash, and transaction ID. This method constructs the
     * transaction using the Cardano CLI and returns the transaction body.
     * </p>
     *
     * @param senderAddress the address of the sender
     * @param scriptAddress the address of the script
     * @param lovelace      the amount of lovelace to be sent
     * @param datumHash     the datum hash associated with the transaction
     * @param txHash        the transaction hash
     * @param txID          the transaction ID
     * @return the transaction body as a string
     */
    @GetMapping("/script/{senderAddress}/{scriptAddress}/{lovelace}/{datumHash}/{txHash}/{txID}")
    public String getTransaction(@PathVariable String senderAddress, @PathVariable String scriptAddress,
            @PathVariable String lovelace, @PathVariable String datumHash, @PathVariable String txHash,
            @PathVariable String txID) {
        return buildTransaction(senderAddress, scriptAddress, lovelace, datumHash, txHash, txID);
    }

    /**
     * Builds a Cardano transaction using the Cardano CLI with the provided parameters.
     * <p>
     * This method executes the CLI command to build the transaction and then reads
     * the generated transaction body from a file. The file is created in the resource
     * directory and contains the transaction body in JSON format.
     * </p>
     *
     * @param senderAddress the address of the sender
     * @param scriptAddress the address of the script
     * @param lovelace      the amount of lovelace to be sent
     * @param datumHash     the datum hash associated with the transaction
     * @param txHash        the transaction hash
     * @param txID          the transaction ID
     * @return the transaction body as a string, or null if the process fails
     */
    public String buildTransaction(String senderAddress, String scriptAddress, String lovelace, String datumHash,
            String txHash, String txID) {
        String tot = scriptAddress + "+" + lovelace;
        String resourcePath = getResourcePath();
        String txHashID = txHash + "#" + txID;
        String bodyPath = resourcePath + scriptAddress + ".build";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--tx-in", txHashID,
                    "--tx-out", tot,
                    "--tx-out-datum-hash", datumHash,
                    "--change-address", senderAddress,
                    TESTNET, "2",
                    "--out-file", bodyPath,
                    "--babbage-era",
                    "--socket-path", socketPath
            );
            System.out.println("Build Transaction command: " + processBuilder.command());

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            String processOutput = output.toString();

            // Print the process output
            System.out.print("Process output:");
            System.out.println(processOutput);

            reader.close();

            File bodyFile = new File(bodyPath);
            if (bodyFile.exists()) {
                StringBuilder content = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(bodyFile))) {
                    String lines;
                    while ((lines = br.readLine()) != null) {
                        content.append(lines).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return content.toString();
            } else {
                System.err.println("Error: Failed to generate transaction body.");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves the resource path for storing temporary files.
     * <p>
     * This method gets the path to the directory where temporary files are stored,
     * using the class loader to get the path to the resources directory.
     * </p>
     *
     * @return the resource path as a string
     */
    private static String getResourcePath() {
        return CardanoJScriptMakeBuildTransactionController.class.getClassLoader().getResource("").getPath();
    }
}

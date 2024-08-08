package com.cardanoj.api.Transactioncontroller;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.CardanoJRandomNameGenerator;

/**
 * REST controller for submitting Cardano transactions.
 * <p>
 * This controller provides an endpoint for submitting a Cardano transaction and retrieving
 * its transaction ID. It interacts with the Cardano CLI to perform the transaction submission
 * and transaction ID retrieval operations.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJSubmitTransactionController {

    @Autowired
    private CardanoJRandomNameGenerator randomName;

    /**
     * Endpoint to submit a Cardano transaction and retrieve its transaction ID.
     * <p>
     * This method decodes the URL-encoded transaction body, saves it to a file,
     * and uses the Cardano CLI to submit the transaction. It then retrieves the transaction ID
     * and returns it as a JSON response.
     * </p>
     *
     * @param tx the transaction body (URL-encoded)
     * @return a JSON response containing the transaction ID
     * @throws UnsupportedEncodingException if URL decoding fails
     */
    @GetMapping("/submit")
    public String submitTransaction(@RequestParam String tx) throws UnsupportedEncodingException {
        String decodedTxBody = URLDecoder.decode(tx, "UTF-8");
        String txhash = submit(decodedTxBody);
        return "{\"txhash\":\"" + txhash + "\"}";
    }

    /**
     * Submits a Cardano transaction and retrieves the transaction ID.
     * <p>
     * This method saves the transaction body to a file, constructs and executes Cardano CLI commands
     * to submit the transaction and retrieve its ID. It returns the transaction ID if successful.
     * </p>
     *
     * @param tx the transaction body
     * @return the transaction ID, or an empty string if submission fails
     */
    public String submit(String tx) {
        String lineTXID = "";
        String resourcePath = getResourcePath();
        String txPath = resourcePath + randomName.generate() + ".tx";
        saveToFile(tx, txPath);

        try {
            // Submit the transaction
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "submit", TESTNET, "2", "--tx-file", txPath, "--socket-path", socketPath
            );
            System.out.println("Command: " + processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitcode = process.waitFor();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            if (exitcode == 0) {
                System.out.println("Transaction submitted successfully");

                // Retrieve the transaction ID
                ProcessBuilder processBuilderTXID = new ProcessBuilder(
                        cliPath, "transaction", "txid", "--tx-file", txPath
                );
                System.out.println("Command: " + processBuilderTXID.command());
                processBuilderTXID.redirectErrorStream(true);
                Process processTXID = processBuilderTXID.start();
                int exitcodeTXID = processTXID.waitFor();
                try (BufferedReader readerTXID = new BufferedReader(new InputStreamReader(processTXID.getInputStream()))) {
                    while ((lineTXID = readerTXID.readLine()) != null) {
                        System.out.println("Transaction ID: " + lineTXID);
                        System.out.println("Cardanoscan: https://preview.cardanoscan.io/transaction/" + lineTXID);
                        break;
                    }
                }

                if (exitcodeTXID == 0) {
                    System.out.println("Transaction ID generated successfully");
                    return lineTXID;
                } else {
                    System.err.println("Error while generating transaction ID");
                }
            } else {
                System.err.println("Error while submitting transaction");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while submitting the transaction", e);
        }

        return lineTXID;
    }

    /**
     * Retrieves the path to a writable directory for storing temporary files.
     * <p>
     * This method gets the path to the directory where temporary files can be stored.
     * </p>
     *
     * @return the resource path as a string
     */
    private static String getResourcePath() {
        return CardanoJSubmitTransactionController.class.getClassLoader().getResource("").getPath();
    }

    /**
     * Saves the given transaction data to a file.
     * <p>
     * This method writes the specified transaction data to a file with the given file path.
     * </p>
     *
     * @param jsonData the transaction data to be saved
     * @param fileName the path to the file where data will be saved
     */
    private void saveToFile(String jsonData, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(jsonData);
            System.out.println("Saved transaction data to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

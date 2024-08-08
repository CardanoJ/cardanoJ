package com.cardanoj.api.Transactioncontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.CardanoJRandomNameGenerator;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * REST controller for handling requests related to signing Cardano transactions.
 * <p>
 * This controller provides an endpoint for signing a Cardano transaction using
 * the provided signing key and transaction body. It interacts with the Cardano
 * CLI to perform the signing operation and returns the signed transaction.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJSignTransactionController {

    @Autowired
    private CardanoJRandomNameGenerator randomName;

    /**
     * Endpoint to sign a Cardano transaction with the provided signing key and transaction body.
     * <p>
     * This method decodes the URL-encoded signing key and transaction body, saves them to files,
     * and uses the Cardano CLI to sign the transaction. It returns the content of the signed transaction file.
     * </p>
     *
     * @param signKey the signing key (URL-encoded)
     * @param txbody  the transaction body (URL-encoded)
     * @return the signed transaction as a string
     * @throws UnsupportedEncodingException if URL decoding fails
     */
    @GetMapping("/sign")
    public String getSign(@RequestParam String signKey, @RequestParam String txbody)
            throws UnsupportedEncodingException {

        // Decode URL-encoded parameters
        String decodedTxBody = URLDecoder.decode(txbody, "UTF-8");
        String decodedSignKey = URLDecoder.decode(signKey, "UTF-8");

        System.out.println(decodedTxBody);
        System.out.println(decodedSignKey);

        // Sign the transaction and return the result
        return signTransaction(decodedSignKey, decodedTxBody);
    }

    /**
     * Signs a Cardano transaction using the provided signing key and transaction body.
     * <p>
     * This method saves the decoded transaction body and signing key to files, constructs
     * and executes a Cardano CLI command to sign the transaction, and reads the signed transaction
     * from a file. The content of the signed transaction file is returned.
     * </p>
     *
     * @param signKey the signing key
     * @param txbody  the transaction body
     * @return the signed transaction as a string, or throws a RuntimeException if an error occurs
     */
    public String signTransaction(String signKey, String txbody) {
        String resourcePath = getResourcePath();

        // Generate unique file paths for the transaction body and signing key
        String bodyPath = resourcePath + randomName.generate() + ".txbody";
        String signKeyPath = resourcePath + randomName.generate() + ".skey";
        String txPath = resourcePath + randomName.generate() + ".tx";

        System.out.println(signKey);
        System.out.println(txbody);

        // Save the decoded JSON data to files
        saveToFile(txbody, bodyPath);
        saveToFile(signKey, signKeyPath);

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "sign",
                    "--tx-body-file", bodyPath,
                    "--signing-key-file", signKeyPath,
                    TESTNET, "2",
                    "--out-file", txPath
            );

            System.out.println("Sign Transaction command: " + processBuilder.command());

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            // Check if the signed transaction file was created
            File txFile = new File(txPath);
            if (txFile.exists()) {
                System.out.println("Signing TX file generated");
            } else {
                System.err.println("Error: Failed to generate signing TX.");
            }

            // Read and return the content of the signed transaction file
            return new String(Files.readAllBytes(Paths.get(txPath)));

        } catch (Exception e) {
            throw new RuntimeException(e);
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
        return CardanoJSignTransactionController.class.getClassLoader().getResource("").getPath();
    }

    /**
     * Saves the given JSON data to a file.
     * <p>
     * This method writes the specified JSON data to a file with the given file name.
     * </p>
     *
     * @param jsonData the JSON data to be saved
     * @param fileName the name of the file to save the data to
     */
    private void saveToFile(String jsonData, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(jsonData);
            System.out.println("Saved decoded " + fileName + " to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

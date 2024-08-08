package com.cardanoj.api.Transactioncontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling transaction-related API requests.
 * <p>
 * This class provides an endpoint to build a Cardano transaction by executing a command-line tool
 * and returns the content of the generated transaction body file.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJBuildTransactionController {

    /**
     * Endpoint to build a Cardano transaction.
     * <p>
     * This method receives transaction details through path variables, executes a command-line tool
     * to build the transaction, and returns the content of the generated transaction body file.
     * </p>
     *
     * @param senderAddress The address of the sender.
     * @param receiverAddress The address of the receiver.
     * @param lovelace The amount of lovelace to transfer.
     * @param txHash The transaction hash.
     * @param txID The transaction ID.
     * @return The content of the generated transaction body file.
     */
    @GetMapping("/build/{senderAddress}/{receiverAddress}/{lovelace}/{txHash}/{txID}")
    public String getTransaction(@PathVariable String senderAddress, @PathVariable String receiverAddress,
                                 @PathVariable String lovelace, @PathVariable String txHash, @PathVariable String txID) {
        return executeTransaction(senderAddress, receiverAddress, lovelace, txHash, txID);
    }

    /**
     * Executes the transaction build process using the command-line tool.
     * <p>
     * This method constructs a command to build the transaction, executes it, and reads the output.
     * It then returns the content of the generated transaction body file.
     * </p>
     *
     * @param senderAddress The address of the sender.
     * @param receiverAddress The address of the receiver.
     * @param lovelace The amount of lovelace to transfer.
     * @param txHash The transaction hash.
     * @param txID The transaction ID.
     * @return The content of the generated transaction body file, or null if an error occurs.
     */
    public String executeTransaction(String senderAddress, String receiverAddress, String lovelace, String txHash,
                                     String txID) {

        Random random = new Random();
        int randomNumber = random.nextInt(Integer.MAX_VALUE);

        String txHashID = txHash + "#" + txID;
        String tot = receiverAddress + "+" + lovelace + " lovelace";
        String resourcePath = getResourcePath();
        String bodyPath = resourcePath + "body_" + randomNumber + ".txbody";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--socket-path", socketPath,
                    TESTNET.toString(), "2",
                    "--tx-in", txHashID,
                    "--tx-out", tot,
                    "--change-address", senderAddress,
                    "--out-file", bodyPath
            );
            System.out.println("command: " + processBuilder.command());
            System.out.println("path: " + bodyPath.toString());

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

            String fileContent = new String(Files.readAllBytes(Paths.get(bodyPath)));

            return fileContent;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the resource path for the application.
     * <p>
     * This method retrieves the path where the application resources are located.
     * </p>
     *
     * @return The resource path as a string.
     */
    private static String getResourcePath() {
        return CardanoJBuildTransactionController.class.getClassLoader().getResource("").getPath();
    }
}

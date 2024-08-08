package com.cardanoj.api.stakeController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

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
 * REST controller for handling requests related to building Cardano stake addresses.
 * <p>
 * This controller provides an endpoint for generating a Cardano stake address using
 * the provided payment key and staking script. It interacts with the Cardano CLI to
 * perform the address generation operation and returns the generated address or error message.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJStakeAddressBuildController {

    @Autowired
    private CardanoJRandomNameGenerator randomName;

    /**
     * Endpoint to build a Cardano stake address using the provided payment key and staking script.
     * <p>
     * This method decodes the URL-encoded payment key and staking script, saves them to files,
     * and uses the Cardano CLI to generate a stake address. It returns the address in JSON format.
     * </p>
     *
     * @param paymentKey the payment key (URL-encoded)
     * @param script     the staking script (URL-encoded)
     * @return a JSON string containing the generated stake address or an error message
     * @throws UnsupportedEncodingException if URL decoding fails
     */
    @GetMapping("/stakebuild")
    public String getAddressBuild(@RequestParam String paymentKey, @RequestParam String script)
            throws UnsupportedEncodingException {

        // Decode URL-encoded parameters
        String decodedPaymentKey = URLDecoder.decode(paymentKey, "UTF-8");
        String decodedScript = URLDecoder.decode(script, "UTF-8");

        // Save the decoded data to files
        String paymentKeyFilePath = saveToFile(decodedPaymentKey, randomName.generate() + ".paymentKey");
        String scriptFilePath = saveToFile(decodedScript, "staking.plutus");

        // Build the stake address and return the result
        String address = stakeAddressBuild(paymentKeyFilePath, scriptFilePath);
        return "{\"address\":\"" + address + "\"}";
    }

    /**
     * Builds a Cardano stake address using the provided payment key file and staking script file.
     * <p>
     * This method constructs and executes a Cardano CLI command to generate the stake address,
     * reads the output from the CLI, and returns the address or an error message.
     * </p>
     *
     * @param paymentKeyPath the path to the payment key file
     * @param scriptPath     the path to the staking script file
     * @return the generated stake address as a string, or an error message in JSON format
     */
    public String stakeAddressBuild(String paymentKeyPath, String scriptPath) {
        String resourcePath = getResourcePath();
        String addressPath = resourcePath + "user1scriptstake.addr";

        try {
            // Construct the Cardano CLI command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "address", "build",
                    TESTNET, "2",
                    "--payment-verification-key-file", paymentKeyPath,
                    "--stake-script-file", scriptPath,
                    "--out-file", addressPath
            );
            System.out.println("Command: " + processBuilder.command());

            Process process = processBuilder.start();

            // Wait for the process to complete
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // Read and return the error message if the process failed
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorMsg = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorMsg.append(errorLine).append("\n");
                }
                System.err.println("Error: " + errorMsg.toString());
                return "{\"error\":\"Process failed with exit code " + exitCode + ": " + errorMsg.toString() + "\"}";
            }

            // Check if the address file was created and return its content
            File addressFile = new File(addressPath);
            if (addressFile.exists()) {
                System.out.println("Stake address generated successfully");
                return new String(Files.readAllBytes(Paths.get(addressPath)));
            } else {
                System.err.println("Error: Failed to generate stake address.");
                return "{\"error\":\"Failed to generate stake address.\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"An error occurred while building the stake address.\"}";
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
        return CardanoJStakeAddressBuildController.class.getClassLoader().getResource("").getPath();
    }

    /**
     * Saves the given data to a file.
     * <p>
     * This method writes the specified data to a file with the given file name in the resource directory.
     * </p>
     *
     * @param data     the data to be saved
     * @param fileName the name of the file to save the data to
     * @return the path to the saved file, or null if an error occurs
     */
    private String saveToFile(String data, String fileName) {
        String resourcePath = getResourcePath();
        String filePath = resourcePath + fileName;

        // Ensure the directory exists
        File file = new File(resourcePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(data);
            System.out.println("Saved data to file: " + filePath);
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

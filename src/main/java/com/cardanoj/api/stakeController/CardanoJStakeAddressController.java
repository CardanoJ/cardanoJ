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
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling requests related to Cardano stake address generation.
 * <p>
 * This controller provides an endpoint for generating a Cardano stake address using
 * a provided staking script. It interacts with the Cardano CLI to perform the address
 * generation operation and returns the result as a JSON response.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJStakeAddressController {

    /**
     * Endpoint to build a Cardano stake address using the provided staking script.
     * <p>
     * This method decodes the URL-encoded staking script, saves it to a file,
     * and uses the Cardano CLI to generate the stake address. It returns the address
     * or an error message in JSON format.
     * </p>
     *
     * @param script the staking script (URL-encoded)
     * @return a ResponseEntity containing the generated stake address or an error message
     */
    @GetMapping("/stakeAddress")
    public ResponseEntity<String> getAddress(@RequestParam String script) {
        try {
            // Decode the URL-encoded staking script
            String decodedScript = URLDecoder.decode(script, "UTF-8");
            String resourcePath = getWritableResourcePath();
            String scriptFilePath = resourcePath + "staking.plutus";

            // Save the decoded staking script to a file
            saveToFile(decodedScript, scriptFilePath);

            // Generate the stake address
            String address = stakeAddress(scriptFilePath);
            if (address.contains("error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(address);
            } else {
                return ResponseEntity.ok().body("{\"address\":\"" + address + "\"}");
            }
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Invalid encoding.\"}");
        }
    }

    /**
     * Generates a Cardano stake address using the provided staking script file.
     * <p>
     * This method constructs and executes a Cardano CLI command to generate the stake address,
     * reads the output from the CLI, and returns the address or an error message.
     * </p>
     *
     * @param scriptFilePath the path to the staking script file
     * @return the generated stake address as a string, or an error message in JSON format
     */
    public String stakeAddress(String scriptFilePath) {
        String resourcePath = getWritableResourcePath();
        String addressPath = resourcePath + "user1scriptstake.addr";

        try {
            // Construct the Cardano CLI command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "stake-address", "build",
                    TESTNET, "2",
                    "--stake-script-file", scriptFilePath,
                    "--out-file", addressPath
            );
            System.out.println("Command: " + processBuilder.command());

            Process process = processBuilder.start();

            // Read error stream and wait for process to complete
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");
            }
            process.waitFor();

            // Check the exit value and return the appropriate response
            if (process.exitValue() != 0) {
                System.err.println("Error: " + errorOutput.toString());
                return "{\"error\":\"Failed to generate stake address. Details: " + errorOutput.toString() + "\"}";
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
            return "{\"error\":\"An error occurred while generating the stake address.\"}";
        }
    }

    /**
     * Retrieves the path to a writable directory for storing temporary files.
     * <p>
     * This method gets the path to the system's temporary directory and ensures
     * it ends with the appropriate file separator.
     * </p>
     *
     * @return the writable resource path as a string
     */
    private static String getWritableResourcePath() {
        // Define a writable directory for storing temporary files
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir.endsWith(File.separator) ? tempDir : tempDir + File.separator;
    }

    /**
     * Saves the given data to a file.
     * <p>
     * This method writes the specified data to a file with the given file path,
     * ensuring that the directory structure exists.
     * </p>
     *
     * @param data     the data to be saved
     * @param filePath the path to the file where data will be saved
     */
    private void saveToFile(String data, String filePath) {
        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException("Failed to create directory: " + parent);
            }

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(data);
                System.out.println("Saved decoded data to file: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save file", e);
        }
    }
}

package com.cardanoj.api.stakeController;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling requests related to Cardano delegation certificate generation.
 * <p>
 * This controller provides an endpoint for generating a Cardano delegation certificate using
 * a provided staking script and pool ID. It interacts with the Cardano CLI to perform the
 * certificate generation operation and returns the result as a JSON response.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJStakeAddressDelCertController {

    /**
     * Endpoint to build a Cardano delegation certificate using the provided staking script and pool ID.
     * <p>
     * This method decodes the URL-encoded staking script, saves it to a file,
     * and uses the Cardano CLI to generate the delegation certificate. It returns the certificate
     * or an error message in JSON format.
     * </p>
     *
     * @param script the staking script (URL-encoded)
     * @param poolId the pool ID for the delegation certificate
     * @return a JSON response containing the generated delegation certificate or an error message
     * @throws UnsupportedEncodingException if the URL decoding fails
     */
    @GetMapping("/delcert")
    public String getStakeAddressDelCert(@RequestParam String script, @RequestParam String poolId) throws UnsupportedEncodingException {
        String decodedScript = URLDecoder.decode(script, "UTF-8");
        String resourcePath = getWritableResourcePath();
        String scriptFilePath = resourcePath + "staking.plutus";

        // Save the decoded script to a file
        saveToFile(decodedScript, scriptFilePath);

        // Generate the delegation certificate
        String delCert = stakeAddressDelCert(scriptFilePath, poolId);
        return "{\"delCert\":\"" + delCert + "\"}";
    }

    /**
     * Generates a Cardano delegation certificate using the provided staking script file and pool ID.
     * <p>
     * This method constructs and executes a Cardano CLI command to generate the delegation certificate,
     * reads the output from the CLI, and returns the certificate or an error message.
     * </p>
     *
     * @param scriptFilePath the path to the staking script file
     * @param poolId the pool ID for the delegation certificate
     * @return the generated delegation certificate as a string, or an error message in JSON format
     */
    public String stakeAddressDelCert(String scriptFilePath, String poolId) {
        String resourcePath = getWritableResourcePath();
        String delCertPath = resourcePath + "delegation.cert";

        try {
            // Construct the Cardano CLI command
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "stake-address", "delegation-certificate",
                    "--stake-script-file", scriptFilePath,
                    "--stake-pool-id", poolId,
                    "--out-file", delCertPath
            );
            System.out.println("Command: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();

            // Read output and error streams
            String output = new String(process.getInputStream().readAllBytes());
            String error = new String(process.getErrorStream().readAllBytes());

            System.out.println("Process output: " + output);
            System.err.println("Process error: " + error);

            // Check process exit value
            if (process.exitValue() != 0) {
                return "{\"error\":\"Failed to generate delegation certificate. Process failed with exit code: " + process.exitValue() + "\"}";
            }

            // Check if the delegation certificate file was created and return its content
            File delCertFile = new File(delCertPath);
            if (delCertFile.exists()) {
                return new String(Files.readAllBytes(Paths.get(delCertPath)));
            } else {
                return "{\"error\":\"Failed to generate delegation certificate.\"}";
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "{\"error\":\"An error occurred while generating the delegation certificate.\"}";
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
     * @param data the data to be saved
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

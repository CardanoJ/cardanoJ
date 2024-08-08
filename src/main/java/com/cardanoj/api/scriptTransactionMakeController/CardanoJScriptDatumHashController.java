package com.cardanoj.api.scriptTransactionMakeController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling requests related to Cardano script datum hashes.
 * <p>
 * This controller provides an endpoint to compute the hash of a Cardano script datum
 * based on the provided datum value.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJScriptDatumHashController {

    /**
     * Endpoint to compute the hash of a Cardano script datum.
     * <p>
     * The datum value is passed as a URL parameter, formatted as a JSON string,
     * and then used to compute its hash using the Cardano CLI.
     * </p>
     *
     * @param datumValue the datum value as a URL-encoded string
     * @return a {@link ResponseEntity} containing a map with the datum hash
     */
    @GetMapping("/datum")
    public ResponseEntity<Map<String, String>> getHash(@RequestParam String datumValue) {
        String formattedDatumValue = "\"" + datumValue + "\""; // Ensure the value is a valid JSON string
        String datumHash = datumHashFromValue(formattedDatumValue);
        
        Map<String, String> response = new HashMap<>();
        response.put("datumHash", datumHash);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Computes the hash of a Cardano script datum using the Cardano CLI.
     * <p>
     * Executes the CLI command to hash the provided datum value and returns the hash.
     * </p>
     *
     * @param datumValue the datum value formatted as a JSON string
     * @return the computed datum hash
     */
    public String datumHashFromValue(String datumValue) {
        String datumHash = "";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction",
                    "hash-script-data",
                    "--script-data-value", datumValue
            );

            System.out.println("Command: " + processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            datumHash = reader.readLine(); // Assuming the datum hash is returned as the first line of output

            System.out.println("Datum Hash: " + datumHash);
            process.waitFor();

        } catch (Exception e) {
            throw new RuntimeException("Failed to compute datum hash", e);
        }
        return datumHash;
    }
}

package com.cardanoj.api.scriptTransactionMakeController;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.CardanoJRandomNameGenerator;

/**
 * REST controller for handling requests related to Cardano script addresses.
 * <p>
 * This controller provides an endpoint to generate a Cardano address based on a provided CBOR script.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CardanoJScriptAddressController {

    @Autowired
    private CardanoJRandomNameGenerator randomName;

    /**
     * Endpoint to generate a Cardano address from a CBOR script.
     * <p>
     * The CBOR data is decoded, saved to a file, and then used to build a Cardano address.
     * </p>
     *
     * @param cbor the CBOR-encoded script as a URL-encoded string
     * @return a {@link ResponseEntity} containing the generated address or an error message
     */
    @GetMapping("/script")
    public ResponseEntity<String> getAddress(@RequestParam String cbor) {
        try {
            String decodedCbor = URLDecoder.decode(cbor, "UTF-8");
            String resourcePath = getWritableResourcePath();

            String paymentScriptFile = resourcePath + "AlwaysSucceeds.plutus";
            saveToFile(decodedCbor, paymentScriptFile);

            String address = addressBuild(paymentScriptFile);
            if (address.contains("error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(address);
            } else {
                return ResponseEntity.ok().body("{\"address\":\"" + address + "\"}");
            }
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Invalid CBOR encoding.\"}");
        }
    }

    /**
     * Builds a Cardano address using the provided payment script file.
     * <p>
     * The address is generated using the Cardano CLI and saved to a file.
     * </p>
     *
     * @param paymentScriptFile the path to the payment script file
     * @return the generated address or an error message
     */
    public String addressBuild(String paymentScriptFile) {
        String resourcePath = getWritableResourcePath();
        String paymentScriptFileAddress = resourcePath + randomName.generate() + ".addr";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "address", "build",
                    "--payment-script-file", paymentScriptFile,
                    TESTNET, "2", "--out-file", paymentScriptFileAddress
            );

            System.out.println("Command: " + processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Capture and log the output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            System.out.println("Output: " + output.toString());
            System.out.println("Exit Code: " + exitCode);

            File addressFile = new File(paymentScriptFileAddress);
            if (addressFile.exists()) {
                System.out.println("Payment Script Address generated successfully");
                return new String(Files.readAllBytes(Paths.get(paymentScriptFileAddress)));
            } else {
                System.err.println("Error: Failed to generate Payment Script Address.");
                return "{\"error\":\"Failed to generate Payment Script Address.\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    /**
     * Gets the path to a writable directory for storing temporary files.
     * 
     * @return the path to the writable directory
     */
    private static String getWritableResourcePath() {
        // Define a writable directory for storing temporary files
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir.endsWith(File.separator) ? tempDir : tempDir + File.separator;
    }

    /**
     * Saves the provided data to a file at the specified path.
     * 
     * @param data the data to write to the file
     * @param filePath the path to the file
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

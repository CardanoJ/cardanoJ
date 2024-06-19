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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Stake Controller")

public class CardanoJStakeAddressDelCertController {

    @GetMapping("/delcert")
    @Operation(summary = "Generate a stake address delegation certificate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated the delegation certificate",
                    content = @Content(schema = @Schema(implementation = String.class))),
            // @ApiResponse(responseCode = "400", description = "Invalid CBOR encoding",
            //         content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    public String getstakeAddressDelCert(@RequestParam @Parameter(description = "Script content") String script,@RequestParam @Parameter(description = "Pool ID") String poolId) throws UnsupportedEncodingException {
        String decodedScript = URLDecoder.decode(script, "UTF-8");
        String resourcePath = getWritableResourcePath();
        String scriptFilePath = resourcePath + "staking.plutus";

        // Save the decoded script to a file
        saveToFile(decodedScript, scriptFilePath);

        String delCert = stakeAddressDelCert(scriptFilePath,poolId);
        String jsonResponse = "{\"delCert\":\"" + delCert + "\"}";
        return jsonResponse;
    }




    public String stakeAddressDelCert(String scriptFilePath,String poolId) {
        String  resourcePath = getWritableResourcePath();
        String delCertPath = resourcePath +"delegation.cert";
        //String delCert = "src/main/resources/assets/delegation.cert";
        System.out.println(scriptFilePath);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "stake-address", "delegation-certificate",
                    "--stake-script-file", scriptFilePath,
                    "--stake-pool-id", poolId,
                    "--out-file", delCertPath
            );
            System.out.println("Command: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();
            String output = new String(process.getInputStream().readAllBytes());
            String error = new String(process.getErrorStream().readAllBytes());

        System.out.println("Process output: " + output);
        System.err.println("Process error: " + error);

        if (process.exitValue() != 0) {
            System.err.println("Process failed with exit code: " + process.exitValue());
            return "{\"error\":\"Failed to generate delCert. Process failed with exit code: " + process.exitValue() + "\"}";
        }
            
            File delCertFile = new File(delCertPath);
            if (delCertFile.exists()) {
                System.out.println("Payment Script delCert generated successfully");
                return new String(Files.readAllBytes(Paths.get(delCertPath)));
            } else {
                System.err.println("Error: Failed to generate Payment Script delCert.");
                return "{\"error\":\"Failed to generate Payment Script delCert.\"}";
            }
         

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getWritableResourcePath() {
        // Define a writable directory for storing temporary files
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir.endsWith(File.separator) ? tempDir : tempDir + File.separator;
    }
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

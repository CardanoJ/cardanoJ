package com.cardanoj.api.stakeController;
import static com.cardanoj.api.util.CardanoJConstant.cliPath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

public class CardanoJstakeAddressRegCertController {

@GetMapping("/regcert")
@Operation(summary = "Generate a stake address registration certificate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated the registration certificate",
                    content = @Content(schema = @Schema(implementation = String.class))),
            // @ApiResponse(responseCode = "400", description = "Invalid CBOR encoding",
            //         content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
     public ResponseEntity<String> getRegCert(@RequestParam @Parameter(description = "Script content") String script) {
        try {
            String decodedScriptFile = URLDecoder.decode(script, "UTF-8");
            String resourcePath = getWritableResourcePath();

            String scriptFilePath = resourcePath + "staking.plutus";
            saveToFile(decodedScriptFile, scriptFilePath);

            String regCert = stakeAddressRegCert(scriptFilePath);
            if (regCert.contains("error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(regCert);
            } else {
                //return ResponseEntity.ok().body("{\"regCert\":\"" + regCert + "\"}");
                return ResponseEntity.ok().body(regCert);
            }
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Invalid CBOR encoding.\"}");
        }
    }




    public String stakeAddressRegCert(String scriptFilePath) {
        String resourcePath = getWritableResourcePath();
        String regCertPath = resourcePath+"registration.cert";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                cliPath, "stake-address", "registration-certificate",
                    "--stake-script-file", scriptFilePath,
                    "--out-file", regCertPath
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
            return "{\"error\":\"Failed to generate regCert. Process failed with exit code: " + process.exitValue() + "\"}";
        }
          File regCertFile = new File(regCertPath);
            if (regCertFile.exists()) {
                System.out.println("regCert generated successfully");
                return new String(Files.readAllBytes(Paths.get(regCertPath)));
            } else {
                System.err.println("Error: Failed to generate Payment regCert.");
                return "{\"error\":\"Failed to generate regCert.\"}";
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

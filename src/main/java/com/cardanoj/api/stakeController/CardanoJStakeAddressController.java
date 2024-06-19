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

public class CardanoJStakeAddressController {
    @GetMapping("/stakeAddress")
    @Operation(summary = "Generate a stake address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated the stake address",
                    content = @Content(schema = @Schema(implementation = String.class))),
            // @ApiResponse(responseCode = "400", description = "Invalid CBOR encoding",
            //         content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
     public ResponseEntity<String> getAddress(@RequestParam @Parameter(description = "Script content") String script) {
        try {
            String decodedScriptFile = URLDecoder.decode(script, "UTF-8");
            String resourcePath = getWritableResourcePath();

            String scriptFilePath = resourcePath + "staking.plutus";
            saveToFile(decodedScriptFile, scriptFilePath);

            String address = stakeAddress(scriptFilePath);
            if (address.contains("error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(address);
            } else {
                return ResponseEntity.ok().body("{\"address\":\"" + address + "\"}");
            }
        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Invalid CBOR encoding.\"}");
        }
    }


    public String stakeAddress(String scriptFilePath) {
        String resourcePath = getWritableResourcePath();
        //if required we can generated random addr
        String addressPath = resourcePath  + "user1scriptstake.addr";
    
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "stake-address", "build",
                    TESTNET, "2",
                    "--stake-script-file", scriptFilePath,
                    "--out-file", addressPath
            );
            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            //modified
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
           String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            errorOutput.append(errorLine).append("\n");
        }
            process.waitFor(); 
            //modified
            if (process.exitValue() != 0) {
                System.err.println("Error: " + errorOutput.toString());
                return "{\"error\":\"Failed to generate Payment Script Address. Details: " + errorOutput.toString() + "\"}";
            }
    

           // BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            

            File addressFile = new File(addressPath);
            if (addressFile.exists()) {
                System.out.println("Payment Script Address generated successfully");
                return new String(Files.readAllBytes(Paths.get(addressPath)));
            } else {
                System.err.println("Error: Failed to generate Payment Script Address.");
                return "{\"error\":\"Failed to generate Payment Script Address.\"}";
            }
            //return address;

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

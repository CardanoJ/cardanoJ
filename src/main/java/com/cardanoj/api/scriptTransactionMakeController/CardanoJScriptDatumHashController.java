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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Script Transaction Make Controller")

public class CardanoJScriptDatumHashController {
    @GetMapping("/datum")
    @Operation(summary = "Get Datum Hash", description = "Generate a hash for the provided datum value")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    // @ApiResponse(responseCode = "400", description = "Invalid request format", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))

    public ResponseEntity<Map<String, String>> getHash(@Parameter(description = "Datum value", required = true) @RequestParam String datumValue) {
        String formattedDatumValue = "\"" + datumValue + "\""; // Ensure the value is a valid JSON string
        String datumHash = datumHashFromValue(formattedDatumValue);
        
        Map<String, String> response = new HashMap<>();
        response.put("datumHash", datumHash);
        
        return ResponseEntity.ok(response);
    }
    


    public String datumHashFromValue(String datumValue){
        String datumHash = "";


        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction",
                    "hash-script-data",
                    "--script-data-value", datumValue
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            datumHash = reader.readLine(); // Assuming the datum hash is returned as the first line of output

            System.out.println("Datum Hash: " + datumHash);
            process.waitFor();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datumHash;
    }


}

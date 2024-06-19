package com.cardanoj.api.stakeController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api")
@Tag(name = "Stake Controller")
@Hidden
public class CardanojCalculateFeeController {
    @GetMapping("/fee")
    @Operation(summary = "Calculate the minimum fee for a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully calculated the minimum fee", 
                         content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", 
                         content = @Content(schema = @Schema(implementation = String.class)))
    })
    public String getCalculateMinimumFee(
        @RequestParam @Schema(description = "Transaction build file content") String txBuild,
        @RequestParam @Schema(description = "Protocol parameter file content") String protocolParam,
        @RequestParam @Schema(description = "Number of transaction inputs") String txInCount,
        @RequestParam @Schema(description = "Number of transaction outputs") String txOutCount,
        @RequestParam @Schema(description = "Number of Byron witness count") String byronWitnessCount
)throws UnsupportedEncodingException{
       
        
            String resourcePath = getWritableResourcePath();
            String decodedtxBuildFile = URLDecoder.decode(txBuild, "UTF-8");
          protocolParam = resourcePath + "protocol-parameters.json";
            String decodedtprotocolParam = URLDecoder.decode(protocolParam, "UTF-8");
           
          
            saveToFile(decodedtxBuildFile, decodedtprotocolParam);
           
            String reader = calculateMinimumFee(decodedtxBuildFile,decodedtprotocolParam,txInCount,txOutCount,byronWitnessCount);
            String jsonResponse = "{\"reader\":\"" + reader + "\"}";
            return jsonResponse;
        
    }
    


    public String calculateMinimumFee(String txBuild, String protocolParam, String txInCount, String txOutCount, String byronWitnessCount){

        try{
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "calculate-min-fee",
                    "--tx-body-file", txBuild,
                    TESTNET, "2",
                    "--protocol-params-file", protocolParam,
                    "--tx-in-count", txInCount, //"1",
                    "--tx-out-count", txOutCount, //"2",
                    "--witness-count",byronWitnessCount // "0"
            );

            System.out.println("command: "+processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return reader.readLine();

        } catch (Exception e) {
            throw new RuntimeException(e);
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

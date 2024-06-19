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

public class CardanoJstakeAddressBuildController {
    @Autowired
    CardanoJRandomNameGenerator randomName;
 
    @GetMapping("/stakebuild")
    @Operation(summary = "Build a stake address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully built the stake address", 
                         content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", 
                         content = @Content(schema = @Schema(implementation = String.class)))
    })
      public String getAddressBuild(
            @RequestParam @Parameter(description = "Payment key content") String paymentKey,
            @RequestParam @Parameter(description = "Script content") String script)throws UnsupportedEncodingException {
                String decodedpaymentKey = URLDecoder.decode(paymentKey, "UTF-8");
                String decodedscript = URLDecoder.decode(script, "UTF-8");
               
        
                String paymentKeyFilePath = saveToFile(decodedpaymentKey, randomName.generate() + ".paymentKey");
                String scriptFilePath = saveToFile(decodedscript, "staking.plutus");
        
                String address = stakeAddressBuild(paymentKeyFilePath, scriptFilePath);
                String jsonResponse = "{\"address\":\"" + address + "\"}";
             
                return jsonResponse;
    }



    public String stakeAddressBuild(String paymentKeyPath, String scriptPath) {
        String resourcePath = getResourcePath();
        String addressPath = resourcePath  + "user1scriptstake.addr";
        
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "address", "build",
                    TESTNET, "2",
                    "--payment-verification-key-file", paymentKeyPath,
                    "--stake-script-file",scriptPath,
                    "--out-file", addressPath
            );
            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
       
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorMsg = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorMsg.append(errorLine).append("\n");
                }
                System.err.println("Error: " + errorMsg.toString());
                return "{\"error\":\"Process failed with exit code " + exitCode + ": " + errorMsg.toString() + "\"}";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            File addressFile = new File(addressPath);
            if (addressFile.exists()) {
                System.out.println("Payment Script Address generated successfully");
                return new String(Files.readAllBytes(Paths.get(addressPath)));
            } else {
                System.err.println("Error: Failed to generate Payment Script Address.");
                return "{\"error\":\"Failed to generate Payment Script Address.\"}";
            }
           

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getResourcePath() {
        return CardanoJstakeAddressBuildController.class.getClassLoader().getResource("").getPath();
    }
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

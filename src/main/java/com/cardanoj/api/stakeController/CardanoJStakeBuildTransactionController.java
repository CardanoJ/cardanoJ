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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.dto.BuildTransactionRequest;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Stake Controller", description = "Operations related to manage stake addresses and certificates.")
@Hidden
public class CardanoJStakeBuildTransactionController {
    @PostMapping("/build")
    @Operation(summary = "Build a stake transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully built the transaction"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    public String getBuildTransaction(@RequestBody BuildTransactionRequest request) {
        try {
            String regCertFilePath = saveToFile(request.getRegCert(), "registration.cert");
            String delCertFilePath = saveToFile(request.getDelCert(), "delegation.cert");
            String scriptFilePath = saveToFile(request.getScriptFile(), "staking.plutus");
            String redeemerFilePath = saveToFile(request.getRedeemerFile(), "units.json");
            String protocolparamFilePath = saveToFile(request.getProtocolparam(), "protocol-parameters.json");

            String body = buildTransaction(
                regCertFilePath, 
                delCertFilePath, 
                scriptFilePath, 
                redeemerFilePath, 
                protocolparamFilePath, 
                request.getAddress(), 
                request.getTxHash(), 
                request.getTxId(), 
                request.getCollateralTxHash(), 
                request.getCollateralTxId()
            );

            return "{\"body\":\"" + body + "\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    public String buildTransaction(String regCert, String delCert, String scriptFile, String redeemerFile, String protocolparam, String address, String txHash, String txId, String collateralTxHash, String collateralTxId) {
        String resourcePath = getResourcePath();
        String body = resourcePath + "tx.txbody";
        String txIn = txHash + "#" + txId;
        String collateral = collateralTxHash + "#" + collateralTxId;

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                cliPath, "transaction", "build",
                "--babbage-era",
                "--testnet-magic", "2",
                "--change-address", address,
                "--out-file", body,
                "--tx-in", txIn,
                "--tx-in-collateral", collateral,
                "--certificate-file", regCert,
                "--certificate-file", delCert,
                "--certificate-script-file", scriptFile,
                "--certificate-redeemer-file", redeemerFile,
                "--protocol-params-file", protocolparam,
                "--socket-path", socketPath
            );

            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            captureOutput(process); // Capture and log the output
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                File txbodyFile = new File(body);
                if (txbodyFile.exists()) {
                    System.out.println("Payment transaction body generated successfully");
                    return new String(Files.readAllBytes(txbodyFile.toPath()));
                } else {
                    System.err.println("Error: Failed to generate transaction body.");
                    return "{\"error\":\"Failed to generate transaction body.\"}";
                }
            } else {
                System.err.println("Process exited with code " + exitCode);
                return "{\"error\":\"Process exited with code " + exitCode + "\"}";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
    }

    private static String getResourcePath() {
        return CardanoJStakeBuildTransactionController.class.getClassLoader().getResource("").getPath();
    }

    private String saveToFile(String data, String fileName) throws IOException {
        String resourcePath = getResourcePath();
        String filePath = resourcePath + fileName;
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(data);
            System.out.println("Saved decoded " + fileName + " to file: " + fileName);
        }
        return filePath;
    }

    private void captureOutput(Process process) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            while ((line = errorReader.readLine()) != null) {
                System.err.println(line);
            }
        }
    }

}
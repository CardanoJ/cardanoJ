package com.cardanoj.api.scriptTransactionMakeController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardanoJScriptMakeBuildTransactionController {
    @GetMapping("/script/{senderAddress}/{scriptAddress}/{lovelace}/{datumHash}/{txHash}/{txID}")
    public String getTransaction(@PathVariable String senderAddress, @PathVariable String scriptAddress,
            @PathVariable String lovelace, @PathVariable String datumHash, @PathVariable String txHash,
            @PathVariable String txID) {
        return buildTransaction(senderAddress, scriptAddress, lovelace, datumHash, txHash, txID);
    }

    public String buildTransaction(String senderAddress, String scriptAddress, String lovelace, String datumHash,
            String txHash, String txID) {
        // String txINN = getTransactionDetails(cliPath, resourcePath, senderAddress,
        // networkID);
        String tot = scriptAddress + "+" + lovelace;
        String resourcePath = getResourcePath();
        String txHashID = txHash + "#" + txID;
        String bodyPath = resourcePath + scriptAddress + ".build";
        // define your own cardano Node path

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--tx-in", txHashID,
                    "--tx-out", tot,
                    "--tx-out-datum-hash", datumHash,
                    "--change-address", senderAddress,
                    TESTNET, "2",
                    "--out-file", bodyPath,
                    "--babbage-era",
                    "--socket-path", socketPath);
            System.out.println("Build Transaction command: " + processBuilder.command());

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            // Read the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            String processOutput = output.toString();

            // Print the process output
            System.out.print("Process output:");
            System.out.println(processOutput);

            reader.close();

            File bodyFile = new File(bodyPath);
            if (bodyFile.exists()) {
                StringBuilder content = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new FileReader(bodyFile))) {
                    String lines;
                    while ((lines = br.readLine()) != null) {
                        content.append(lines).append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
                return content.toString();
            } else {
                System.err.println("Error: Failed to generate transaction body.");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getResourcePath() {
        return CardanoJScriptMakeBuildTransactionController.class.getClassLoader().getResource("").getPath();
    }

}

package com.cardanoj.api.scriptTransactionCollectController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

@RestController
@RequestMapping("/api")
public class CardanoJCollectBuildTransactionController {
    private String scriptLovelace = " ";

   
    @GetMapping("/collect")
    public String getBuildAddress(@RequestParam String  collateral,@RequestParam String scriptUtxoId,
    @RequestParam String datumValue,@RequestParam String redeemerValue,@RequestParam String scriptFile, 
    @RequestParam String feeAddress, String receiverAddress,String datumHash) throws UnsupportedEncodingException{
    
     String decodedScript = URLDecoder.decode(scriptFile, "UTF-8");
            String resourcePath = getWritableResourcePath();

            String paymentScriptFile = resourcePath + "ScriptFile.plutus";
            saveToFile(decodedScript, paymentScriptFile);

    
      return buildTransaction(collateral, scriptUtxoId, datumValue, redeemerValue, scriptFile, feeAddress, receiverAddress, datumHash, paymentScriptFile);
    }
  






    public String buildTransaction(String collateral, String scriptUtxoId,String scriptAddress, String datumValue,   String redeemerValue, String scriptFile, String feeAddress, String receiverAddress, String datumHash) {
        String resourcePath = getResourcePath();
          
        
        String receiver = receiverAddress +"+"+scriptLovelace ; // --Fix it its not right
    
        String txBuild = resourcePath + receiverAddress + ".build";


        try{
           
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transactxBuildtion", "build",
                    "--tx-in", collateral,
                    "--tx-in", scriptUtxoId,
                    "--tx-in-datum-value", datumValue,
                    "--tx-in-redeemer-value", redeemerValue,
                    "--tx-in-script-file", scriptFile,
                    "--tx-in-collateral", collateral,
                    "--change-address", feeAddress,
                    "--tx-out", receiver,
                    "--tx-out-datum-hash", datumHash,
                    "--out-file", txBuild,
                    TESTNET, "2",
                    "--babbage-era", "--socket-path", socketPath
            );
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


            File bodyFile = new File(txBuild);
             if (bodyFile.exists()) {
                return new String(Files.readAllBytes(Paths.get(txBuild)));
            } else {
                System.err.println("Error: Failed to generate transaction body.");
                return null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
    private static String getResourcePath() {
        return CardanoJCollectBuildTransactionController.class.getClassLoader().getResource("").getPath();
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
    private static String getWritableResourcePath() {
        // Define a writable directory for storing temporary files
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir.endsWith(File.separator) ? tempDir : tempDir + File.separator;
    }
}

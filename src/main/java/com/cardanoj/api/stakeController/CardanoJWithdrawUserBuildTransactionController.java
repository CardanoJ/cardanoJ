package com.cardanoj.api.stakeController;
import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import static com.cardanoj.api.util.CardanoJConstant.socketPath;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CardanoJWithdrawUserBuildTransactionController {
    @GetMapping("/buildtx")
    public String getWithdrawlbuildTransaction(@RequestParam String script,@RequestParam String reedemfile,@RequestParam String address,
    @RequestParam String txOut,@RequestParam String lovelace,@RequestParam String txIn,
    @RequestParam String collateral, @RequestParam String withdrawlAddress,@RequestParam String withdrawlLovelace)  throws UnsupportedEncodingException{
      String decodedscriptFile= URLDecoder.decode(script, "UTF-8");
    String decodedredeemerFile= URLDecoder.decode(reedemfile, "UTF-8");
    String deodedcollateral = URLDecoder.decode(collateral, "UTF-8");
    String body = buildTransaction(decodedscriptFile,decodedredeemerFile,address,txOut,lovelace,txIn,deodedcollateral,withdrawlAddress,withdrawlLovelace);
		// ObjectMapper objectMapper = new ObjectMapper();
		String jsonResponse = "{\"body\":\"" + body + "\"}";
        return jsonResponse;
    }


    public String buildTransaction(String scriptFile,String redeemerFile,String address,String txOut,String lovelace, String txIn,String collateral,String withdrawlAddress,String withdrawlLovelace) {
        String resourcePath = getResourcePath();
        String body = resourcePath+"withdrawTx.txbody";
        String scriptFilePath = resourcePath+"staking.plutus";
        String redeemerFilePath = resourcePath+"units.json";
     saveToFile(scriptFile, scriptFilePath);
     saveToFile(redeemerFile, redeemerFilePath);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "build",
                    "--babbage-era",
                    TESTNET, "2",
                    "--change-address", "addr_test1yzc22rfmfpgshyp60n4ev0s3j5svz3r9q5mzutvcaznu5gs8phtwuxk0fw226lwaujsmy0v3hxr7z3uajs5r5hmddx4sekdxvx",
                    "--out-file", body,
                    "--tx-out", "addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2+500000000",
                    "--tx-in", txIn, //b1027ff1545eccfe2bbf4489337ed5b0083b9f3800d5c67af020d59dd1ce#1
                    "--tx-in-collateral",txIn,
                    "--withdrawal", "stake_test17qrsm4hwrt85h99d0hw7fgdj8kgmnplpg7weg2p6takkn2ck8prnj+900000000",
                    "--withdrawal-script-file", scriptFile,
                    "--withdrawal-redeemer-file", redeemerFile,
                    "--socket-path", socketPath

            );

            System.out.println("Query: " + processBuilder.command());

            Process process = processBuilder.start();
            process.waitFor();
          File txbodyFile = new File(body);
            if (txbodyFile.exists()) {
                System.out.println("Payment transaction body generated successfully");
                return new String(Files.readAllBytes(Paths.get(body)));
            } else {
                System.err.println("Error: Failed to generate transaction body.");
                return "{\"error\":\"Failed to generate transaction body.\"}";
            }
            

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private static String getResourcePath() {
        return  CardanoJWithdrawUserBuildTransactionController.class.getClassLoader().getResource("").getPath();
    }
 private void saveToFile(String jsonData, String fileName) {
        String resourcePath = getResourcePath();
        // String filePath = resourcePath + fileName;
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(jsonData);
            System.out.println("Saved decoded " + fileName + " to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

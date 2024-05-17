package com.cardanoj.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.CardanoJRandomNameGenerator;

import static com.cardanoj.api.util.CardanoJConstant.cliPath;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.cardanoj.api.util.CardanoJConstant.TESTNET;

@RestController
@RequestMapping("/api")
public class CardanoJSignTransactionController {
    @Autowired
    CardanoJRandomNameGenerator randomName;

    @GetMapping("/sign")
    public String getSign(@RequestParam String signKey, @RequestParam String txbody)
            throws UnsupportedEncodingException {

        String decodedTxBody = URLDecoder.decode(txbody, "UTF-8");
        String decodedsignKey = URLDecoder.decode(signKey, "UTF-8");

        System.out.println(decodedTxBody);
        System.out.println(decodedsignKey);

        return signTransaction(decodedsignKey, decodedTxBody);

    }

    public String signTransaction(String signKey, String txbody) {
        String resourcePath = getResourcePath();

        String bodyPath = resourcePath + randomName.generate() + ".txbody";
        String signKeyPath = resourcePath + randomName.generate() + ".skey";

        System.out.println(signKey);
        System.out.println(txbody);

        // Save the decoded JSON data to files
        // saveToFile(txbody, randomName.generate()+".txbody");
        // saveToFile(signKey, randomName.generate()+".skey");
        //

        // Save the decoded JSON data to files
        saveToFile(txbody, bodyPath);
        saveToFile(signKey, signKeyPath);

        String txPath = resourcePath + randomName.generate() + ".tx";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    cliPath, "transaction", "sign",
                    "--tx-body-file", bodyPath,
                    "--signing-key-file", signKeyPath,
                    TESTNET, "2",
                    "--out-file", txPath);

            System.out.println("command: " + processBuilder.command());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            process.waitFor();

            File txFile = new File(txPath);
            if (txFile.exists()) {
                System.out.println("Signing TX file generated");
            } else {
                System.err.println(
                        "Error:failed to generate signing TX.");
            }

            String fileContent = new String(Files.readAllBytes(Paths.get(txPath)));

            return fileContent;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResourcePath() {
        return CardanoJSignTransactionController.class.getClassLoader().getResource("").getPath();
    }

    // Method to save the decoded JSON data to a file
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
package com.cardanoj.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cardanoj.api.util.RandomNameGenerator;

import static com.cardanoj.api.util.CJConstant.cliPath;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import static com.cardanoj.api.util.CJConstant.TESTNET;

@RestController
@RequestMapping("/api")
public class SignTransactionController {
    @Autowired
    RandomNameGenerator randomName;

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
                        "Error: F%7B%0A%20%20%20%20%22type%22%3A%20%22PaymentSigningKeyShelley_ed25519%22%2C%0A%20%20%20%20%22description%22%3A%20%22Payment%20Signing%20Key%22%2C%0A%20%20%20%20%22cborHex%22%3A%20%225820eaadd017a202d990e3918022623cc3a2d861a4efb5641c7863d160ef9d40c7dc%22%0A%7D&txbody=%7B%0A%20%20%20%20%22type%22%3A%20%22Unwitnessed%20Tx%20BabbageEra%22%2C%0A%20%20%20%20%22description%22%3A%20%22Ledger%20Cddl%20Format%22%2C%0A%20%20%20%20%22cborHex%22%3A%20%2284a30081825820e691702007043e13f86b9d5d21aebf55ed2d9b1074cb3f33ffb32214f0ac34f200018282581d6083377fad68e45c474545153423db475a58e799e89c5cdda5cf1528f31a0098968082581d6073910a62b3d834ae39bf3606a3223b4bb52c6eff34d76d4c8e785f521b000000025370c6d7021a000286a9a0f5f6%22%0A%7D%0Aailed to generate signing TX.");
            }

            String fileContent = new String(Files.readAllBytes(Paths.get(txPath)));

            return fileContent;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResourcePath() {
        return SignTransactionController.class.getClassLoader().getResource("").getPath();
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
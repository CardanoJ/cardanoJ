package com.cardanoj.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

import com.cardanoj.api.dto.CardanojAddressData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CardanoJAddressService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${cardano.cli.path}")
    private String cliPath;

    public CardanojAddressData createAndReadAddressData(String name) {
        String vkeyFilePath = "src/main/resources/assets/" + name + ".vkey";
        String skeyFilePath = "src/main/resources/assets/" + name + ".skey";
        String addrFilePath = "src/main/resources/assets/" + name + ".addr";

        generateAddress(vkeyFilePath, skeyFilePath, addrFilePath);

        CardanojAddressData addressData = readAddressData(vkeyFilePath, skeyFilePath, addrFilePath);

        deleteFiles(vkeyFilePath, skeyFilePath, addrFilePath);

        return addressData;
    }

    private void generateAddress(String vkey, String skey, String addrFilePath) {
        try {
            ProcessBuilder keyGenProcessBuilder = new ProcessBuilder(
                    cliPath, "address", "key-gen",
                    "--verification-key-file", vkey,
                    "--signing-key-file", skey
            );
            Process keyGenProcess = keyGenProcessBuilder.start();
            keyGenProcess.waitFor();

            ProcessBuilder addressBuildProcessBuilder = new ProcessBuilder(
                    cliPath, "address", "build",
                    "--payment-verification-key-file", vkey,
                    "--testnet-magic", "2",
                    "--out-file", addrFilePath
            );
            Process addressBuildProcess = addressBuildProcessBuilder.start();
            addressBuildProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private CardanojAddressData readAddressData(String vkeyFilePath, String skeyFilePath, String addrFilePath) {
        try {
            String vkeyContent = new String(Files.readAllBytes(Paths.get(vkeyFilePath)));
            String skeyContent = new String(Files.readAllBytes(Paths.get(skeyFilePath)));
            String addressContent = new String(Files.readAllBytes(Paths.get(addrFilePath))).trim();

            JsonNode vkeyNode = objectMapper.readTree(vkeyContent);
            JsonNode skeyNode = objectMapper.readTree(skeyContent);

            return new CardanojAddressData(vkeyNode, skeyNode, addressContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void deleteFiles(String... filePaths) {
        for (String filePath : filePaths) {
            try {
                Files.deleteIfExists(Paths.get(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




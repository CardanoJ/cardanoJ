package com.cardanoj.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import com.cardanoj.api.dto.CardanoJAddressData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

/**
 * Service class for managing Cardano addresses.
 */
@Service
public class CardanoJAddressService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${cardano.cli.path}")
    private String cliPath;

    /**
     * Creates and reads address data for a given name.
     *
     * @param name the name for the address data
     * @return the created address data
     */
    public CardanoJAddressData createAndReadAddressData(String name) {
        String vkeyFilePath = "ApiCardanojApi/src/main/resources/assets/" + name + ".vkey";
        String skeyFilePath = "ApiCardanojApi/src/main/resources/assets/" + name + ".skey";
        String addrFilePath = "ApiCardanojApi/src/main/resources/assets/" + name + ".addr";

        generateAddress(vkeyFilePath, skeyFilePath, addrFilePath);

        CardanoJAddressData addressData = readAddressData(vkeyFilePath, skeyFilePath, addrFilePath);

        deleteFiles(vkeyFilePath, skeyFilePath, addrFilePath);

        return addressData;
    }

    /**
     * Generates a Cardano address using the given key file paths.
     *
     * @param vkey the path to the verification key file
     * @param skey the path to the signing key file
     * @param addrFilePath the path to the address file
     */
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

    /**
     * Reads address data from the given file paths.
     *
     * @param vkeyFilePath the path to the verification key file
     * @param skeyFilePath the path to the signing key file
     * @param addrFilePath the path to the address file
     * @return the address data
     */
    private CardanoJAddressData readAddressData(String vkeyFilePath, String skeyFilePath, String addrFilePath) {
        try {
            String vkeyContent = new String(Files.readAllBytes(Paths.get(vkeyFilePath)));
            String skeyContent = new String(Files.readAllBytes(Paths.get(skeyFilePath)));
            String addressContent = new String(Files.readAllBytes(Paths.get(addrFilePath))).trim();

            JsonNode vkeyNode = objectMapper.readTree(vkeyContent);
            JsonNode skeyNode = objectMapper.readTree(skeyContent);

            return new CardanoJAddressData(vkeyNode, skeyNode, addressContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes the specified files.
     *
     * @param filePaths the paths to the files to be deleted
     */
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

package com.cardanoJ.address;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.cardanoJ.transaction.CardanoJConstant.TESTNET_MAGIC;
import static com.cardanoJ.transaction.CardanoJConstant.TESTNET_MAGIC_NUMBER;

public class CardanoJBuildAddress {

    public String addressGen(String cliPath, String resourcePath, String name, String network, String networkId) {
        String vkeyFilePath = resourcePath + name + ".vkey";

        String skeyFilePath = resourcePath + name + ".skey";

        String addrFilePath = resourcePath + name + ".addr";

        generateAddress(cliPath, resourcePath, vkeyFilePath, skeyFilePath, addrFilePath, network, networkId);

        return addrFilePath;

    }

    private void generateAddress(String cliPath, String resourcePath, String vkey, String skey, String addrFilePath, String network, String networkId) {
        try {
            Path dirPath = Paths.get(
                    resourcePath.endsWith(File.separator) ? resourcePath : resourcePath + File.separator
            );

            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            ProcessBuilder keyGenProcessBuilder = new ProcessBuilder(
                    cliPath, "address", "key-gen",
                    "--verification-key-file", vkey,
                    "--signing-key-file", skey
            );
            Process keyGenProcess = keyGenProcessBuilder.start();
            keyGenProcess.waitFor();

            // Build address
            ProcessBuilder addressBuildProcessBuilder = new ProcessBuilder(
                    cliPath, "address", "build",
                    "--payment-verification-key-file", vkey,
                    network.contains("testnet") ? "--testnet-magic" : "--mainnet", networkId,
                    "--out-file", addrFilePath
            );
            Process addressBuildProcess = addressBuildProcessBuilder.start();
            addressBuildProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
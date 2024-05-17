package com.cardanoJ.address;

import java.io.IOException;
public class CardanoJBuildAddress {

    public String addressGen(String cliPath, String name) {
        String vkeyFilePath = "src/main/resources/assets/" + name + ".vkey";

        String skeyFilePath = "src/main/resources/assets/" + name + ".skey";

        String addrFilePath = "src/main/resources/assets/" + name + ".addr";

        generateAddress(cliPath, vkeyFilePath, skeyFilePath, addrFilePath);

        return addrFilePath;

    }

    private void generateAddress(String cliPath, String vkey, String skey, String addrFilePath) {
        try {
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
                    "--testnet-magic", "2",
                    "--out-file", addrFilePath
            );
            Process addressBuildProcess = addressBuildProcessBuilder.start();
            addressBuildProcess.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
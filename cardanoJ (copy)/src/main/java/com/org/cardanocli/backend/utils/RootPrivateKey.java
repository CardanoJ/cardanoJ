package com.org.cardanocli.backend.utils;

import java.io.*;

    public class RootPrivateKey {
        private String rootPrivateKey;
    
        public String getRootPrivateKey() {
            return rootPrivateKey;
        }
    
        public void setRootPrivateKey(String cliPath, String recoveryPhraseFilePath, String resourcePath, String os) {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(cliPath, "key", "from-recovery-phrase", "Shelley");
            try {
                processBuilder.redirectInput(new File(recoveryPhraseFilePath));
                processBuilder.redirectOutput(new File(resourcePath + "root.xsk"));
    
                Process process = processBuilder.start();
                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("Root private key generated successfully.");
                    rootPrivateKey = resourcePath + "root.xsk";
                } else {
                    System.out.println("Error occurred while generating root private key.");
                }
    
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
package org.cardanoJ.utils;

import java.io.*;

public class SeedPhraseGenerate {
    private String phrase = "";

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String cliPath, String os, String resourcePath) {
        ProcessBuilder processBuilder = new ProcessBuilder(cliPath, "recovery-phrase", "generate", "--size", "24");

        try {
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder recoveryPhrase = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                recoveryPhrase.append(line).append(System.lineSeparator());
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code : " + exitCode);

            phrase = recoveryPhrase.toString().trim();
            saveToFile(phrase, resourcePath + "phrase.prv");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void saveToFile(String content, String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                boolean wasSuccessful = file.createNewFile();
                if (!wasSuccessful) {
                    System.out.println("Failed to Generate Seed Phrase.");
                    return;
                }
            }
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
                bw.write(content);
            }
            System.out.println("Seed Phrase Generated Successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

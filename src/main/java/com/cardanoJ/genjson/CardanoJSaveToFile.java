package com.cardanoJ.genjson;

import java.io.FileWriter;
import java.io.IOException;

public class CardanoJSaveToFile {
    public void writeJsonToFile(String json, String resourcePath) {
        String fileName = resourcePath + "unit.json";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(json);
            System.out.println("JSON data written to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing JSON data to file: " + e.getMessage());
        }
    }

}

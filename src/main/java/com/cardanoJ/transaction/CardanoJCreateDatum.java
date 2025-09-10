package com.cardanoJ.transaction;

import com.cardanoJ.genjson.*;
import com.google.gson.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CardanoJCreateDatum {
    public String create(String senderAddress,String receiverAddress,int lovelace, String resourcePath) {
        CardanoJJSONData cardanoJJSONData = new CardanoJJSONDataImpl();

        List<CardanoJItem> items1 = new ArrayList<>();
        items1.add(new CardanoJItem(senderAddress));
        cardanoJJSONData.addElement(new CardanoJJSONElement(0, null, items1));

        List<CardanoJItem> items2 = new ArrayList<>();
        items2.add(new CardanoJItem(receiverAddress));
        cardanoJJSONData.addElement(new CardanoJJSONElement(1, null, items2));

        List<CardanoJItem> items3 = new ArrayList<>();
        items3.add(new CardanoJItem(lovelace));
        cardanoJJSONData.addElement(new CardanoJJSONElement(2, null, items3));


        JsonObject jsonObject = JsonParser.parseString(cardanoJJSONData.toJSON()).getAsJsonObject();
        JsonArray elementsArray = jsonObject.getAsJsonArray("elements");

        // Convert the array of objects back to JSON
        String newJson = elementsArray.toString();
        System.out.println(newJson);

        // Save to file
        CardanoJSaveToFile save = new CardanoJSaveToFile();
        save.writeJsonToFile(newJson,resourcePath);

        return newJson;
    }

    public String createDatumV2(int intValue, String stringValue, String resourcePath) {
        JsonObject datum = new JsonObject();

        // set constructor
        datum.addProperty("constructor", 0);

        // create fields
        JsonArray fields = new JsonArray();

        // add int
        JsonObject intField = new JsonObject();
        intField.addProperty("int", intValue);
        fields.add(intField);

        // add string as bytes
        JsonObject bytesField = new JsonObject();
        // encode string to hex for Plutus bytes
        bytesField.addProperty("bytes", stringToHex(stringValue));
        fields.add(bytesField);

        datum.add("fields", fields);

        // write to file
        CardanoJSaveToFile save = new CardanoJSaveToFile();
        save.writeJsonToFile(datum.toString(), resourcePath);

        return datum.toString();
    }

    private String stringToHex(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

}

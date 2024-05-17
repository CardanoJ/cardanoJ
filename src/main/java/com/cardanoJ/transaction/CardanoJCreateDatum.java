package com.cardanoJ.transaction;

import com.cardanoJ.genjson.*;
import com.google.gson.*;

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

}

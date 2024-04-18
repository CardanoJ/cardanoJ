package org.cardanoJ.transaction;

import com.google.gson.*;
import org.cardanoJ.genjson.*;

import java.util.ArrayList;
import java.util.List;

public class CreateDatum {
    public String create(String senderAddress,String receiverAddress,int lovelace, String resourcePath) {
        JSONData jsonData = new JSONDataImpl();

        List<Item> items1 = new ArrayList<>();
        items1.add(new Item(senderAddress));
        jsonData.addElement(new JSONElement(0, null, items1));

        List<Item> items2 = new ArrayList<>();
        items2.add(new Item(receiverAddress));
        jsonData.addElement(new JSONElement(1, null, items2));

        List<Item> items3 = new ArrayList<>();
        items3.add(new Item(lovelace));
        jsonData.addElement(new JSONElement(2, null, items3));


        JsonObject jsonObject = JsonParser.parseString(jsonData.toJSON()).getAsJsonObject();
        JsonArray elementsArray = jsonObject.getAsJsonArray("elements");

        // Convert the array of objects back to JSON
        String newJson = elementsArray.toString();
        System.out.println(newJson);

        // Save to file
        SaveToFile  save = new SaveToFile();
        save.writeJsonToFile(newJson,resourcePath);

        return newJson;
    }

}

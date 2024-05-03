package com.cardanoJ.genjson;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class JSONDataImpl implements JSONData {
    private List<JSONElement> elements;

    public JSONDataImpl() {
        this.elements = new ArrayList<>();
    }

    @Override
    public void addElement(JSONElement element) {
        elements.add(element);
    }

    @Override
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(new JSONStructure(elements));
    }
}

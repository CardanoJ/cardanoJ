package com.cardanoJ.genjson;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class CardanoJJSONDataImpl implements CardanoJJSONData {
    private List<CardanoJJSONElement> elements;

    public CardanoJJSONDataImpl() {
        this.elements = new ArrayList<>();
    }

    @Override
    public void addElement(CardanoJJSONElement element) {
        elements.add(element);
    }

    @Override
    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(new CardanoJJSONStructure(elements));
    }
}

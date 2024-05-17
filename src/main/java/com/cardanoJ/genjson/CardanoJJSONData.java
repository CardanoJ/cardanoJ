package com.cardanoJ.genjson;

public interface CardanoJJSONData {
    void addElement(CardanoJJSONElement element);
    String toJSON();
}

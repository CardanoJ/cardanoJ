package com.cardanoJ.genjson;

import com.google.gson.annotations.SerializedName;

public class CardanoJItem {
    @SerializedName("int")
    private int intValue;

    @SerializedName("string")
    private String stringValue;


    public CardanoJItem(int intValue) {
        this.intValue = intValue;
    }

    public CardanoJItem(String stringValue) {
        this.stringValue = stringValue;
    }
}

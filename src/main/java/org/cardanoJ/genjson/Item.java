package org.cardanoJ.genjson;

import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("int")
    private int intValue;

    @SerializedName("string")
    private String stringValue;


    public Item(int intValue) {
        this.intValue = intValue;
    }

    public Item(String stringValue) {
        this.stringValue = stringValue;
    }
}

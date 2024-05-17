package com.cardanoJ.genjson;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CardanoJJSONElement {
    @SerializedName("constructor")
    private Integer constructor;

    @SerializedName("fields")
    private List<CardanoJField> cardanoJFields;

    @SerializedName("list")
    private List<CardanoJItem> list;

    public CardanoJJSONElement(Integer constructor, List<CardanoJField> cardanoJFields, List<CardanoJItem> list) {
        this.constructor = constructor;
        this.cardanoJFields = cardanoJFields;
        this.list = list;
    }
}

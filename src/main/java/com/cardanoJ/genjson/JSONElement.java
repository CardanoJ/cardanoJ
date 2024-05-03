package com.cardanoJ.genjson;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class JSONElement {
    @SerializedName("constructor")
    private Integer constructor;

    @SerializedName("fields")
    private List<Field> fields;

    @SerializedName("list")
    private List<Item> list;

    public JSONElement(Integer constructor, List<Field> fields, List<Item> list) {
        this.constructor = constructor;
        this.fields = fields;
        this.list = list;
    }
}

package org.cardanoJ.genjson;

public interface JSONData {
    void addElement(JSONElement element);
    String toJSON();
}

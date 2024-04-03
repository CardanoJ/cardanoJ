package com.cardanoj.plutus.spec.serializers;

import com.cardanoj.plutus.spec.ConstrPlutusData;
import com.cardanoj.plutus.spec.ListPlutusData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Collections;

import static com.cardanoj.plutus.spec.serializers.PlutusDataJsonKeys.CONSTRUCTOR;
import static com.cardanoj.plutus.spec.serializers.PlutusDataJsonKeys.FIELDS;

public class ConstrDataJsonSerializer extends StdSerializer<ConstrPlutusData> {

    public ConstrDataJsonSerializer() {
        this(null);
    }

    public ConstrDataJsonSerializer(Class<ConstrPlutusData> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(ConstrPlutusData value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField(CONSTRUCTOR, value.getAlternative());
        if (value.getData() != null) {
            ListPlutusData listPlutusData = value.getData();
            gen.writeObjectField(FIELDS, listPlutusData.getPlutusDataList());
        } else
            gen.writeObjectField(FIELDS, Collections.emptyList());
        gen.writeEndObject();
    }
}

package com.cardanoj.plutus.spec.serializers;

import com.cardanoj.plutus.spec.MapPlutusData;
import com.cardanoj.plutus.spec.PlutusData;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import static com.cardanoj.plutus.spec.serializers.PlutusDataJsonKeys.*;

public class MapDataJsonSerializer extends StdSerializer<MapPlutusData> {

    public MapDataJsonSerializer() {
        this(null);
    }

    public MapDataJsonSerializer(Class<MapPlutusData> clazz) {
        super(clazz);
    }

    @Override
    public void serialize(MapPlutusData mapData, JsonGenerator gen, SerializerProvider provider) throws IOException {
        Map<PlutusData, PlutusData> map = mapData.getMap();

        gen.writeStartObject();
        gen.writeFieldName(MAP);
        gen.writeStartArray();
        Iterator<Map.Entry<PlutusData, PlutusData>> itr = map.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<PlutusData, PlutusData> entry = itr.next();
            gen.writeStartObject();
            gen.writeObjectField(MAP_KEY, entry.getKey());
            gen.writeObjectField(MAP_VALUE, entry.getValue());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
}

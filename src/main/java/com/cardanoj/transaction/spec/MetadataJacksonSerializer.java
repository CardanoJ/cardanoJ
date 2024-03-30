package com.cardanoj.transaction.spec;

import com.cardanoj.metadata.Metadata;
import com.cardanoj.metadata.helper.MetadataToJsonNoSchemaConverter;
import com.cardanoj.util.HexUtil;
import com.cardanoj.util.JsonUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Custom serializer class to serialize metadata
 */
class MetadataJacksonSerializer extends StdSerializer<Metadata> {

    public MetadataJacksonSerializer() {
        this(null);
    }

    public MetadataJacksonSerializer(Class<Metadata> t) {
        super(t);
    }

    @Override
    public void serialize(Metadata metadata, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        //write json metadata
        try {
            String json = MetadataToJsonNoSchemaConverter.cborBytesToJson(metadata.serialize());
            JsonNode jsonNode = JsonUtil.parseJson(json);
            gen.writeFieldName("json_metadata");
            gen.writeObject(jsonNode);
        } catch (Exception e) {}

        //write cbor metadata
        try {
            String cborHex = HexUtil.encodeHexString(metadata.serialize());
            gen.writeFieldName("cbor_metadata");
            gen.writeString(cborHex);
        } catch (Exception e) {}

        gen.writeEndObject();
    }
}

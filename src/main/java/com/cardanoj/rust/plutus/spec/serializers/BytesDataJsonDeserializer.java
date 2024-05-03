package com.cardanoj.rust.plutus.spec.serializers;

import com.cardanoj.rust.plutus.spec.BytesPlutusData;
import com.cardanoj.rust.util.HexUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

import static com.cardanoj.rust.plutus.spec.serializers.PlutusDataJsonKeys.BYTES;

public class BytesDataJsonDeserializer extends StdDeserializer<BytesPlutusData> {

    public BytesDataJsonDeserializer() {
        this(null);
    }

    public BytesDataJsonDeserializer(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public BytesPlutusData deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String bytesHex = node.get(BYTES).asText();
        return BytesPlutusData.of(HexUtil.decodeHexString(bytesHex));
    }}

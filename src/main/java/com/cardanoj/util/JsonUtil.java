package com.cardanoj.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    protected static final ObjectMapper mapper;

    static {
        mapper = (new ObjectMapper()).enable(SerializationFeature.INDENT_OUTPUT);
    }

    public JsonUtil() {
    }

    public static String getPrettyJson(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                log.error("Json parsing error", var2);
                return obj.toString();
            }
        }
    }

    public static String getPrettyJson(String jsonStr) {
        if (jsonStr == null) {
            return null;
        } else {
            try {
                Object json = mapper.readValue(jsonStr, Object.class);
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            } catch (Exception var2) {
                return jsonStr;
            }
        }
    }

    public static JsonNode parseJson(String jsonContent) throws JsonProcessingException {
        return mapper.readTree(jsonContent);
    }
}

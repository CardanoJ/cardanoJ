package com.cardanoj.transaction.spec;

import com.cardanoj.crypto.KeyGenUtil;
import com.cardanoj.crypto.Keys;
import com.cardanoj.crypto.SecretKey;
import com.cardanoj.crypto.VerificationKey;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.transaction.spec.script.ScriptPubkey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolicyTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void PolicyScriptPubKeySerializationTest() throws CborSerializationException, JsonProcessingException {
        Keys keys = KeyGenUtil.generateKey();
        VerificationKey vkey = keys.getVkey();
        SecretKey skey = keys.getSkey();
        ScriptPubkey scriptPubkey = ScriptPubkey.create(vkey);
        Policy policy1 = new Policy(scriptPubkey, Arrays.asList(skey));
        String jsonString = objectMapper.writeValueAsString(policy1);
        Policy policy2 = objectMapper.readValue(jsonString,Policy.class);
        assertEquals(policy1,policy2);
    }

}

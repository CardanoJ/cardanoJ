package com.cardanoJ.transaction;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerateUnitJSONTest {

    @Test
    void genUnitJSON() throws NoSuchAlgorithmException {
        GenerateUnitJSON generateUnitJSON = new GenerateUnitJSON();
        assertEquals( "8ca66ee6b2fe4bb928a8e3cd2f508de4119c0895f22e011117e22cf9b13de7ef" ,generateUnitJSON.gen("Hello"));
    }
}

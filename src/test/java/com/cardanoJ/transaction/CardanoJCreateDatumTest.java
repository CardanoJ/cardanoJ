package com.cardanoJ.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardanoJCreateDatumTest {

    @Test
    void create() {
        CardanoJCreateDatum cardanoJCreateDatum = new CardanoJCreateDatum();
        assertEquals(
                "{\"constructor\":0,\"fields\":[{\"int\":42},{\"bytes\":\"616464725f7465737431767a706e776c616464726a39633336396735326e6767376d6761643933657565617a7739656864396575326a3375637234356e646b\"}]}",
                cardanoJCreateDatum.createDatumV2(42, "addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk",
                        getResourcePath()
                )
        );
    }

    private static String getResourcePath() {
        return "src/main/resources/assets/";
    }

}
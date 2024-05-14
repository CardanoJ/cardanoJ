package com.cardanoJ.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateDatumTest {

    @Test
    void create() {
        CreateDatum createDatum = new CreateDatum();
        assertEquals(
                "[{\"constructor\":0,\"list\":[{\"int\":0,\"string\":\"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2\"}]},{\"constructor\":1,\"list\":[{\"int\":0,\"string\":\"addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk\"}]},{\"constructor\":2,\"list\":[{\"int\":1000000}]}]",
                createDatum.create("addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk",
                        1000000,
                        getResourcePath()
                )
        );
    }

    private static String getResourcePath() {
        return BuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}
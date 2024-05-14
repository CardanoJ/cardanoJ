package com.cardanoJ.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.cardanoJ.transaction.Constant.*;

class BuildAddressTest {

    @Test
    void addressGen() {
        BuildAddress buildAddress = new BuildAddress();

        assertEquals("src/main/resources/assets/yourName.addr", buildAddress.addressGen(CLI_PATH,"yourName"));
    }
}
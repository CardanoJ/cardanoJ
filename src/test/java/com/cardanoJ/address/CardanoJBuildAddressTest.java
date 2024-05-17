package com.cardanoJ.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.cardanoJ.transaction.CardanoJConstant.*;

class CardanoJBuildAddressTest {

    @Test
    void addressGen() {
        CardanoJBuildAddress cardanoJBuildAddress = new CardanoJBuildAddress();

        assertEquals("src/main/resources/assets/yourName.addr", cardanoJBuildAddress.addressGen(CLI_PATH,"yourName"));
    }
}
package com.cardanoJ.transaction;

import static com.cardanoJ.transaction.CardanoJConstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CardanoJQueryAddressTest {

    @Test
    void query(){
        CardanoJQueryAddress cardanoJQueryAddress = new CardanoJQueryAddress();
        assertEquals (0, cardanoJQueryAddress.queryAddress(CLI_PATH, SOCKET_PATH, "addr_test1vpt3ysl6gvnc7ffwvrfa8mefssw7txen2kgg6fyep3024dgxrqy0t",TESTNET, TESTNET_MAGIC_NUMBER));

    }
}


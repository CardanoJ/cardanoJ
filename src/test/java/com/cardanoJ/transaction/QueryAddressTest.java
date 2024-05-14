package com.cardanoJ.transaction;

import static com.cardanoJ.transaction.Constant.CLI_PATH;
import static com.cardanoJ.transaction.Constant.TESTNET;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class QueryAddressTest {

    @Test
    void query(){
        QueryAddress queryAddress = new QueryAddress();
        assertEquals (0,queryAddress.queryAddress(CLI_PATH,"addr_test1vpt3ysl6gvnc7ffwvrfa8mefssw7txen2kgg6fyep3024dgxrqy0t",TESTNET));

    }
}


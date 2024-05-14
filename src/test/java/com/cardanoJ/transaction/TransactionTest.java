package com.cardanoJ.transaction;

import org.junit.jupiter.api.Test;

import static com.cardanoJ.transaction.Constant.TESTNET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.cardanoJ.transaction.Constant.CLI_PATH;

public class TransactionTest {

    String expectDatum = "";
    @Test
    void createDatum(){
        expectDatum = "[{\"constructor\":0,\"list\":[{\"int\":0,\"string\":\"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2\"}]},{\"constructor\":1,\"list\":[{\"int\":0,\"string\":\"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2\"}]},{\"constructor\":2,\"list\":[{\"int\":1000000}]}]";

        CreateDatum createDatum = new CreateDatum();
        assertEquals(expectDatum, createDatum.create("addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2",1000000,getResourcePath()));
    }

    @Test
    void buildTransaction(){
        Transaction transaction = new Transaction();
        assertEquals("src/main/resources/assets/sender.txbody",transaction.buildTransaction(CLI_PATH,getResourcePath(),"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","--testnet-magic",1000000,"sender","{\"constructor\":0}"));
    }

    @Test
    void signTransaction(){
        Transaction transaction = new Transaction();
        assertEquals("src/main/resources/assets/sender.tx", transaction.signTransaction(CLI_PATH,getResourcePath(),"--testnet-magic","sender"));
    }


    private static String getResourcePath() {
        return BuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}

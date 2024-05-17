package com.cardanoJ.transaction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.cardanoJ.transaction.CardanoJConstant.CLI_PATH;

public class CardanoJTransactionTest {

    String expectDatum = "";
    @Test
    void createDatum(){
        expectDatum = "[{\"constructor\":0,\"list\":[{\"int\":0,\"string\":\"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2\"}]},{\"constructor\":1,\"list\":[{\"int\":0,\"string\":\"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2\"}]},{\"constructor\":2,\"list\":[{\"int\":1000000}]}]";

        CardanoJCreateDatum cardanoJCreateDatum = new CardanoJCreateDatum();
        assertEquals(expectDatum, cardanoJCreateDatum.create("addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2",1000000,getResourcePath()));
    }

    @Test
    void buildTransaction(){
        CardanoJTransaction cardanoJTransaction = new CardanoJTransaction();
        assertEquals("src/main/resources/assets/sender.txbody", cardanoJTransaction.buildTransaction(CLI_PATH,getResourcePath(),"addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2","--testnet-magic",1000000,"sender","{\"constructor\":0}"));
    }

    @Test
    void signTransaction(){
        CardanoJTransaction cardanoJTransaction = new CardanoJTransaction();
        assertEquals("src/main/resources/assets/sender.tx", cardanoJTransaction.signTransaction(CLI_PATH,getResourcePath(),"--testnet-magic","sender"));
    }


    private static String getResourcePath() {
        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}

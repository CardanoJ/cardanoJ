package com.cardanoJ.transaction;

import org.junit.jupiter.api.Test;

import static com.cardanoJ.transaction.CardanoJConstant.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals("src/main/resources/assets/sender_build.txbody", cardanoJTransaction.buildTransaction(CLI_PATH, SOCKET_PATH, "src/main/resources/assets/","addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p","addr_test1qr864nwnz26egxxzwnuex7danrrrkjl4kl4neevssqu8zlwr2ghnx4q6fql7slk9h0wnw8423pd8frap2kycwxf0hjdqwg0gjq",TESTNET, TESTNET_MAGIC_NUMBER, 5000000,"sender","{\"constructor\":0}"));
    }

    @Test
    void signTransaction(){
        CardanoJTransaction cardanoJTransaction = new CardanoJTransaction();
        assertEquals("src/main/resources/assets/sender_signed.tx", cardanoJTransaction.signTransaction(CLI_PATH, "src/main/resources/assets/",TESTNET, TESTNET_MAGIC_NUMBER, "sender"));
    }


    private static String getResourcePath() {
        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}

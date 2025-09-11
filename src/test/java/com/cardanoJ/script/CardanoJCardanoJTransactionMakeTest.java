package com.cardanoJ.script;

import com.cardanoJ.transaction.CardanoJBuildTransaction;
import org.junit.jupiter.api.Test;

import static com.cardanoJ.transaction.CardanoJConstant.*;
import static org.junit.jupiter.api.Assertions.*;

class CardanoJCardanoJTransactionMakeTest {

    @Test
    void addressBuild() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla", cardanoJTransactionMake.addressBuild(CLI_PATH, "src/main/resources/assets/AlwaysSucceeds.plutus", getResourcePath(), "--testnet-magic", "1"));
    }

    @Test
    void datumHashFromValue() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("9e478573ab81ea7a8e31891ce0648b81229f408d596a3483e6f4f9b92d3cf710", cardanoJTransactionMake.datumHashFromValue(CLI_PATH, "6666"));
    }

    @Test
    void signTransaction() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("src/main/resources/assets/addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla.signed", cardanoJTransactionMake.signTransaction(CLI_PATH, "src/main/resources/assets/", "--testnet-magic", "1", "yourName", "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla"));
    }

    @Test
    void buildTransaction() {
        CardanoJTransactionMake cardanoJTransactionMake = new CardanoJTransactionMake();
        assertEquals("src/main/resources/assets/addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla.build", cardanoJTransactionMake.buildTransaction(CLI_PATH, SOCKET_PATH, getResourcePath(), "addr_test1vqczqf5f2p5uqu3nujtjeu4l2dps0ccu6xlt5cl2sytahvgujxqd8", "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla", "--testnet-magic", "1", 5000000, "src/main/resources/assets/datum.json"));
    }

    private static String getResourcePath() {
//        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
        return "src/main/resources/assets/";
    }

}
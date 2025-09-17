package com.cardanoJ.script;

import com.cardanoJ.transaction.CardanoJBuildTransaction;
import org.junit.jupiter.api.Test;

import static com.cardanoJ.transaction.CardanoJConstant.CLI_PATH;
import static com.cardanoJ.transaction.CardanoJConstant.SOCKET_PATH;
import static org.junit.jupiter.api.Assertions.*;

class CardanoJCardanoJTransactionCollectTest {

    @Test
    void queryProtocolParam() {
        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
        assertEquals(getResourcePath() + "protocol-parameters.json", cardanoJTransactionCollect.queryProtocolParam(CLI_PATH, getResourcePath(), "--testnet-magic", "1", SOCKET_PATH));
    }


    @Test
    void signTransaction() {
        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
        assertEquals(getResourcePath() + "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p" + "_unlock.signed", cardanoJTransactionCollect.signTransaction(CLI_PATH, getResourcePath(), "--testnet-magic", "1", getResourcePath() + "yourName.skey", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p"));
    }

    @Test
    void buildTransaction_withMockedUTxO() {
        CardanoJTransactionCollect collect = new CardanoJTransactionCollect() {
            @Override
            protected String getTransactionDetails(String cliPath, String socketPath, String resourcePath, String address, String network, String networkId) {
                // Simulate CLI response with one UTxO
                return "TxHash                                 TxIx        Amount\n" +
                        "--------------------------------------------------------------------------------------\n" +
                        "abcd1234                                0        5000000 lovelace + TxOutDatumNone";
            }
        };

        String expected = getResourcePath() + "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p_unlock.build";

        assertEquals(expected,
                collect.buildTransaction(
                        CLI_PATH,
                        getResourcePath(),
                        "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla",
                        "abcd1234",
                        "{}",
                        "6666",
                        "src/main/resources/assets/AlwaysSucceeds.plutus",
                        "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p",
                        "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p",
                        "--testnet-magic",
                        "1",
                        SOCKET_PATH
                )
        );
    }


//    @Test
//    void buildTransaction() {
//        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
//        assertEquals(getResourcePath() + "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p" + "_unlock.build", cardanoJTransactionCollect.buildTransaction(CLI_PATH, getResourcePath(), "addr_test1wqag3rt979nep9g2wtdwu8mr4gz6m4kjdpp5zp705km8wys6t2kla", "2e54774e98c57662090ab56a0482612d093625834cc705b5fb68855527514658", "{}", "6666", "src/main/resources/assets/AlwaysSucceeds.plutus", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", "addr_test1vqah3f7zvcjg0ms94ygmmj2n0x35t8gmwdxl2tlkz3jddqs2uqa2p", "--testnet-magic", "1", SOCKET_PATH));
//    }

    @Test
    void datumHashFromValue() {
        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
        assertEquals("9e478573ab81ea7a8e31891ce0648b81229f408d596a3483e6f4f9b92d3cf710", cardanoJTransactionCollect.datumHashFromValue(CLI_PATH, "6666"));
    }

    private static String getResourcePath() {
//        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
        return "src/main/resources/assets/";
    }

}
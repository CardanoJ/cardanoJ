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
        assertEquals(getResourcePath() + "protocol-parameters.json", cardanoJTransactionCollect.queryProtocolParam(CLI_PATH,getResourcePath(),"--testnet-magic","2",SOCKET_PATH));
    }



    @Test
    void signTransaction() {
        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
        assertEquals(getResourcePath() + "addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk" + ".signed", cardanoJTransactionCollect.signTransaction(CLI_PATH,getResourcePath(),"--testnet-magic","2",getResourcePath() + "addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk" + ".build","addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk"));
    }

    @Test
    void buildTransaction() {
        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
        assertEquals(getResourcePath() + "addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk" + ".build", cardanoJTransactionCollect.buildTransaction(CLI_PATH,getResourcePath(),"addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8","","bc97bf14c9fb57b4d05b2d1043e610b63d901894b1e761c6c2bcb5708f8dc7c4","6666","42","src/main/resources/assets/AlwaysSucceeds.plutus","addr_test1vq97lwwsv32lty8u0n6vzlf0f3ah5rhpf43gjcjccp0l8gck54upq","addr_test1vzpnwladdrj9c369g52ngg7mgad93eueazw9ehd9eu2j3ucr45ndk","--testnet-magic","2",SOCKET_PATH));
    }

    @Test
    void datumHashFromValue() {
        CardanoJTransactionCollect cardanoJTransactionCollect = new CardanoJTransactionCollect();
        assertEquals("9e478573ab81ea7a8e31891ce0648b81229f408d596a3483e6f4f9b92d3cf710", cardanoJTransactionCollect.datumHashFromValue(CLI_PATH,getResourcePath(),"6666"));
    }

    private static String getResourcePath() {
        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}
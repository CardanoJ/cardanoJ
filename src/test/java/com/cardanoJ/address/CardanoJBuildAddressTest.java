package com.cardanoJ.address;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.cardanoJ.transaction.CardanoJConstant.*;

class CardanoJBuildAddressTest {

//    @Test
//    void addressGen() {
//        CardanoJBuildAddress cardanoJBuildAddress = new CardanoJBuildAddress();
//
//        assertEquals("src/main/resources/assets/yourName.addr", cardanoJBuildAddress.addressGen(CLI_PATH,getResourcesPath(), "yourName", TESTNET, TESTNET_MAGIC_NUMBER));
//    }

    @Test
    void addressGen() {
        String walletName = "yourName";
        String basePath = getResourcesPath() + walletName;

        deleteIfExists(basePath + ".skey");
        deleteIfExists(basePath + ".vkey");
        deleteIfExists(basePath + ".addr");

        CardanoJBuildAddress cardanoJBuildAddress = new CardanoJBuildAddress();
        String addrPath = cardanoJBuildAddress.addressGen(
                CLI_PATH, getResourcesPath(), walletName, TESTNET, TESTNET_MAGIC_NUMBER
        );

        assertEquals("src/main/resources/assets/yourName.addr", addrPath);
    }

    private void deleteIfExists(String filePath) {
        try {
            java.nio.file.Files.deleteIfExists(java.nio.file.Paths.get(filePath));
        } catch (Exception e) {
            // ignore for tests
        }
    }

    private String getResourcesPath() {
        return "src/main/resources/assets/";
    }
}
package com.cardanoJ.util;

import com.cardanoJ.transaction.CardanoJBuildTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.cardanoJ.transaction.CardanoJConstant.*;

class CardanoJCalculateFeeTest {

    @Test
    void calculate(){
        CardanoJCalculateFee cardanoJCalculateFee = new CardanoJCalculateFee();
        assertEquals("169989 Lovelace", cardanoJCalculateFee.calculateMinimumFee(CLI_PATH,"--testnet-magic","2","src/main/resources/assets/addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8.build",getResourcePath() + "protocol-parameters.json","1","2","0"));
    }

    private static String getResourcePath() {
        return CardanoJBuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}
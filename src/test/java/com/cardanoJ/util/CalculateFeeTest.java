package com.cardanoJ.util;

import com.cardanoJ.transaction.BuildTransaction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static com.cardanoJ.transaction.Constant.*;

class CalculateFeeTest {

    @Test
    void calculate(){
        CalculateFee calculateFee = new CalculateFee();
        assertEquals("169989 Lovelace", calculateFee.calculateMinimumFee(CLI_PATH,"--testnet-magic","2","src/main/resources/assets/addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8.build",getResourcePath() + "protocol-parameters.json","1","2","0"));
    }

    private static String getResourcePath() {
        return BuildTransaction.class.getClassLoader().getResource("").getPath();
    }

}
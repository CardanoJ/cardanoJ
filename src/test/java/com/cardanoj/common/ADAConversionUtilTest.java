package com.cardanoj.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ADAConversionUtilTest {
    ADAConversionUtilTest() {
    }

    @Test
    void adaToLovelace() {
        BigInteger lovelace = ADAConversionUtil.adaToLovelace(new BigDecimal(12.5));
        Assertions.assertEquals(lovelace, BigInteger.valueOf(12500000L));
    }

    @Test
    void lovelaceToAda() {
        BigDecimal ada = ADAConversionUtil.lovelaceToAda(BigInteger.valueOf(2300000L));
        Assertions.assertEquals(ada, BigDecimal.valueOf(2.3));
    }

    @Test
    void adaToLovelace_whenDouble() {
        BigInteger lovelace = ADAConversionUtil.adaToLovelace(5.5);
        Assertions.assertEquals(lovelace, BigInteger.valueOf(5500000L));
    }
}

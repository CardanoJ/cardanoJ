package com.cardanoj.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OSUtilTest {
    OSUtilTest() {
    }

    @Test
    void isAndroid_whenNotSetInConfig() {
        boolean isAndroid = OSUtil.isAndroid();
        Assertions.assertFalse(isAndroid);
    }

    @Test
    void isAndroid_whenSetInJavaVMVendor() {
        String actualVendor = System.getProperty("java.vm.vendor");
        System.setProperty("java.vm.vendor", "The Android Project");
        boolean isAndroid = OSUtil.isAndroid();
        System.setProperty("java.vm.vendor", actualVendor);
        Assertions.assertTrue(isAndroid);
    }
}

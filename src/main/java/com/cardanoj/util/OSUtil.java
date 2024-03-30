package com.cardanoj.util;


public class OSUtil {
    private static boolean android = false;

    public OSUtil() {
    }

    public static void setAndroid(boolean flag) {
        android = flag;
    }

    public static boolean isAndroid() {
        if (android) {
            return true;
        } else {
            String javaVendor = System.getProperty("java.vm.vendor");
            return "The Android Project".equalsIgnoreCase(javaVendor);
        }
    }
}


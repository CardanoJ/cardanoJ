package com.cardanoj.rust.jna;

import com.cardanoj.rust.util.LibraryUtil;
import com.cardanoj.rust.util.NativeUtils;

import java.io.File;
import java.io.IOException;

public class JNANative {

    public static native String getBaseAddressFromMnemonic(String input, int index, boolean isTestnet);

    static {
        try {
            String libPath = System.getProperty("cardanoj_rust_lib");
            if(libPath != null && libPath.trim().length() != 0) {
                System.load(libPath + File.separator + LibraryUtil.getCardanoWrapperLib());
            } else {
                try {
                    NativeUtils.loadLibraryFromJar("/" + LibraryUtil.getCardanoWrapperLib());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String phrase = "" ;

        String result = JNANative.getBaseAddressFromMnemonic(phrase, 0, false);
        System.out.println(result);
    }
}

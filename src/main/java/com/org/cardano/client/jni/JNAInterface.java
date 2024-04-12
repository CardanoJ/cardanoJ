package com.org.cardano.client.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface JNAInterface extends Library {
    JNAInterface INSTANCE = Native.load("/home/tarachand/Videos/cardanoJ/libcardano_serialization_libs.so", JNAInterface.class);


    Pointer getBaseAddress(String phrase, int index, boolean isTestnet);

}

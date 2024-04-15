package com.org.cardano.client.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface JNAInterface extends Library {
<<<<<<< HEAD
    JNAInterface INSTANCE = Native.load("src/main/resources/bin/libcardano_serialization_libs.so", JNAInterface.class);


    Pointer getBaseAddress(String phrase, int index, boolean isTestnet);
    Pointer getBaseAddressByNetwork(String phrase, int index, Network.ByReference network);
    Pointer getPrivateKeyFromMnemonic(String phrase, int index);
    Pointer sign(String rawTxnInHex, String privateKey);
    Pointer signWithSecretKey(String rawTxnInHex, String secretKeyHex);
    boolean validateTransactionCBOR(String rawTxnInHex);
    Pointer signMsg(String msg, String privateKeyHex);

=======
    JNAInterface INSTANCE = Native.load("/home/tarachand/Videos/cardanoJ/libcardano_serialization_libs.so", JNAInterface.class);


    Pointer getBaseAddress(String phrase, int index, boolean isTestnet);
>>>>>>> 97bbb283147899781d0e7a3b02d31515c2124519

}

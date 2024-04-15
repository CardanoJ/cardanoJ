package com.org.cardano.client.jni;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface JNAInterface extends Library {
    JNAInterface INSTANCE = Native.load("src/main/resources/bin/libcardano_serialization_libs.so", JNAInterface.class);


    Pointer getBaseAddress(String phrase, int index, boolean isTestnet);
    Pointer getBaseAddressByNetwork(String phrase, int index, Network.ByReference network);
    Pointer getPrivateKeyFromMnemonic(String phrase, int index);
    Pointer sign(String rawTxnInHex, String privateKey);
    Pointer signWithSecretKey(String rawTxnInHex, String secretKeyHex);
    boolean validateTransactionCBOR(String rawTxnInHex);
    Pointer signMsg(String msg, String privateKeyHex);
}

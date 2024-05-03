package com.cardanoj.rust.jna;

import com.cardanoj.rust.common.model.Network;
import com.cardanoj.rust.jna.util.NetworkJNA;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public interface JNAInterface extends Library {

//    JNAInterface INTERFACE = (JNAInterface)
//            Native.load(LibraryUtil.getCardanoWrapperLib(),
//                    JNAInterface.class);

    JNAInterface INTERFACE = (JNAInterface) Native.load("src/main/resources/lib_cardanoj_rust.so", JNAInterface.class);


    public Pointer getBaseAddress(String phrase, int index, boolean isTestnet);
    public Pointer getBaseAddressByNetwork(String phrase, int index, NetworkJNA.ByReference network);
    public Pointer getEnterpriseAddress(String phrase, int index, boolean isTestnet);
    public Pointer getEnterpriseAddressByNetwork(String phrase, int index, Network network);
    public Pointer generateMnemonic();
    public Pointer getPrivateKeyFromMnemonic(String phrase, int index);
    public Pointer getPrivateKeyBytesFromMnemonic(String phrase, int index);
    public Pointer getPublicKeyBytesFromMnemonic(String phrase, int index);
    public Pointer bech32AddressToBytes(String bech32Address);
    public Pointer hexBytesToBech32Address(String hexAddress);
    Pointer base58AddressToBytes(String base58Address);
    Pointer hexBytesToBase58Address(String hexAddress);
    public Pointer sign(String rawTxnInHex, String privateKey);
    public Pointer signWithSecretKey(String rawTxnInHex, String secretKeyHex);
    public boolean validateTransactionCBOR(String rawTxnInHex);
    public Pointer signMsg(String msg, String privateKeyHex);

    public void dropCharPointer(Pointer ptr);   // Method to free memory


}

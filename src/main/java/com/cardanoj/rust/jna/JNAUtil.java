package com.cardanoj.rust.jna;

import com.cardanoj.rust.common.model.Network;
import com.cardanoj.rust.exception.AddressRuntimeException;
import com.cardanoj.rust.jna.util.NetworkJNA;
import com.cardanoj.rust.util.HexUtil;
import com.sun.jna.Pointer;

public class JNAUtil {

    static JNAInterface jnaInterface = JNAInterface.INTERFACE;

    public static String getBaseAddress(String phrase, int index, boolean isTestnet) {
        Pointer getBaseAddress = jnaInterface.getBaseAddress(phrase, index, isTestnet);
        jnaInterface.dropCharPointer(getBaseAddress);

        return getBaseAddress.getString(0);
    }

    public static String getBaseAddressByNetwork(String phrase, int index, NetworkJNA.ByReference network) {
        Pointer getBaseAddressByNetwork = jnaInterface.getBaseAddressByNetwork(phrase, index, network);
        jnaInterface.dropCharPointer(getBaseAddressByNetwork);

        return getBaseAddressByNetwork.getString(0);
    }

    public static String getEnterpriseAddress(String phrase, int index, boolean isTestnet) {
        Pointer getEnterpriseAddress = jnaInterface.getEnterpriseAddress(phrase, index, isTestnet);
        jnaInterface.dropCharPointer(getEnterpriseAddress);

        return getEnterpriseAddress.getString(0);
    }

    public static String getEnterpriseAddressByNetwork(String phrase, int index, Network network) {
        Pointer getEnterpriseAddressByNetwork = jnaInterface.getEnterpriseAddressByNetwork(phrase, index, network);
        jnaInterface.dropCharPointer(getEnterpriseAddressByNetwork);

        return getEnterpriseAddressByNetwork.getString(0);
    }

    public static String generateMnemonic() {
        Pointer generateMnemonic = jnaInterface.generateMnemonic();
        jnaInterface.dropCharPointer(generateMnemonic);

        return generateMnemonic.getString(0);
    }

    public static String getPrivateKeyFromMnemonic(String phrase, int index) {
        Pointer privateKey = jnaInterface.getPrivateKeyFromMnemonic(phrase, index);
        String result = privateKey.getString(0);

        try {
            if(result == null || result.isEmpty()) {
                throw new AddressRuntimeException("Unable to generate Private Key from Mnemonic!");
            } else {
                return result;
            }
        } finally {
            jnaInterface.dropCharPointer(privateKey);
        }
    }

    public static byte[] getPrivateKeyBytesFromMnemonic(String phrase, int index) {
        Pointer privateKeyBytes = jnaInterface.getPrivateKeyBytesFromMnemonic(phrase, index);
        String result = privateKeyBytes.getString(0);

        try {
            if(result == null || result.isEmpty()) {
                throw new AddressRuntimeException("Unable to generate Private Key Bytes from Mnemonic!");
            } else {
                return HexUtil.decodeHexString(result);
            }
        } finally {
            jnaInterface.dropCharPointer(privateKeyBytes);
        }
    }

    public static byte[] getPublicKeyBytesFromMnemonic(String phrase, int index) {
        Pointer publicKeyBytes = jnaInterface.getPublicKeyBytesFromMnemonic(phrase, index);
        String result = publicKeyBytes.getString(0);

        try {
            if(result == null || result.isEmpty()) {
                throw new AddressRuntimeException("Unable to generate Public Key Bytes from Mnemonic!");
            } else {
                return HexUtil.decodeHexString(result);
            }
        } finally {
            jnaInterface.dropCharPointer(publicKeyBytes);
        }
    }

    public static String bech32AddressToBytes(String bech32Address) {
        // Base address = Bech32Address
        Pointer bech32AddressToBytes = jnaInterface.bech32AddressToBytes(bech32Address);
        jnaInterface.dropCharPointer(bech32AddressToBytes);

        return bech32AddressToBytes.getString(0);
    }

    public static String hexBytesToBech32Address(String hexAddress) {
        Pointer hexBytesToBech32Address = jnaInterface.hexBytesToBech32Address(hexAddress);
        jnaInterface.dropCharPointer(hexBytesToBech32Address);

        return hexBytesToBech32Address.getString(0);
    }

    public static String base58AddressToBytes(String base58Address) {
        // Base58 Address = Test Byron Address
        Pointer base58AddressToBytes = jnaInterface.base58AddressToBytes(base58Address);
        jnaInterface.dropCharPointer(base58AddressToBytes);

        return base58AddressToBytes.getString(0);
    }

    public static String hexBytesToBase58Address(String hexAddress) {
        Pointer hexBytesToBase58Address = jnaInterface.hexBytesToBase58Address(hexAddress);
        jnaInterface.dropCharPointer(hexBytesToBase58Address);

        return hexBytesToBase58Address.getString(0);
    }

    public static String sign(String rawTxnInHex, String privateKey) {
        Pointer sign = jnaInterface.sign(rawTxnInHex, privateKey);
        jnaInterface.dropCharPointer(sign);

        return sign.getString(0);
    }

    public static String signWithSecretKey(String rawTxnInHex, String secretKeyHex) {
        Pointer signWithSecretKey = jnaInterface.signWithSecretKey(rawTxnInHex, secretKeyHex);
        jnaInterface.dropCharPointer(signWithSecretKey);

        return signWithSecretKey.getString(0);
    }

    public static boolean validateTransactionCBOR(String rawTxnInHex) {
        return jnaInterface.validateTransactionCBOR(rawTxnInHex);
    }

    public static String signMsg(String msgHex, String privateKeyHex) {
        Pointer signMsg = jnaInterface.signMsg(msgHex, privateKeyHex);
        String result = signMsg.getString(0);

        jnaInterface.dropCharPointer(signMsg);
        return result;
    }

    public static void freeMemory(Pointer ptr) {
        jnaInterface.dropCharPointer(ptr);
    }
}

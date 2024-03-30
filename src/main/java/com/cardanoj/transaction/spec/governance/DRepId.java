package com.cardanoj.transaction.spec.governance;

import com.cardanoj.crypto.Bech32;
import com.cardanoj.crypto.KeyGenUtil;
import com.cardanoj.crypto.VerificationKey;
import com.cardanoj.util.HexUtil;

public class DRepId {
    public static final String DREP_ID_PREFIX = "drep";
    public static final String DREP_ID_SCRIPT_PREFIX = "drep_script";

    public static String fromVerificationKey(VerificationKey verificationKey) {
        String keyHash = KeyGenUtil.getKeyHash(verificationKey);
        String drepId = Bech32.encode(HexUtil.decodeHexString(keyHash), DREP_ID_PREFIX);
        return drepId;
    }

    public static String fromVerificationKeyBytes(byte[] bytes) {
        String keyHash = KeyGenUtil.getKeyHash(bytes);
        String drepId = Bech32.encode(HexUtil.decodeHexString(keyHash), DREP_ID_PREFIX);
        return drepId;
    }

    public static String fromKeyHash(String keyHash) {
        String drepId = Bech32.encode(HexUtil.decodeHexString(keyHash), DREP_ID_PREFIX);
        return drepId;
    }

    public static String fromScriptHash(String scriptHash) {
        String drepId = Bech32.encode(HexUtil.decodeHexString(scriptHash), DREP_ID_SCRIPT_PREFIX);
        return drepId;
    }

    public static DRep toDrep(String drepId, DRepType drepType) {
        byte[] bytes = Bech32.decode(drepId).data;

        if (drepType == DRepType.ADDR_KEYHASH) {
            return DRep.addrKeyHash(HexUtil.encodeHexString(bytes));
        } else if (drepType == DRepType.SCRIPTHASH) {
            return DRep.scriptHash(HexUtil.encodeHexString(bytes));
        } else {
            throw new IllegalArgumentException("Invalid DrepType");
        }
    }
}

package com.cardanoj.plutus.util;


import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;
import com.cardanoj.common.cbor.CborSerializationUtil;
import com.cardanoj.exception.CborRuntimeException;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.plutus.blueprint.model.PlutusVersion;
import com.cardanoj.plutus.spec.PlutusScript;
import com.cardanoj.plutus.spec.PlutusV1Script;
import com.cardanoj.plutus.spec.PlutusV2Script;
import com.cardanoj.util.HexUtil;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.Optional;

public class PlutusUtil {

    /**
     * Check if the script code is double encoded or not.
     * @param scriptCode
     * @return boolean
     */
    public static boolean isDoubleEncoded(String scriptCode) {
        Objects.requireNonNull(scriptCode, "Script code is required");

        ByteString bs = (ByteString) CborSerializationUtil.deserialize(HexUtil.decodeHexString(scriptCode));

        //Try to decode again
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bs.getBytes());
            CborDecoder decoder = new CborDecoder(bais);
            DataItem di = decoder.decodeNext();
            return di instanceof ByteString;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get PlutusScript from script code. It checks if the script code is double encoded or not and returns the appropriate
     * PlutusScript instance based on the PlutusVersion
     * @param scriptCbor
     * @return PlutusScript
     */
    public static Optional<PlutusScript> getPlutusScript(String scriptHash, String scriptCbor) {
        boolean doubleEncoded = isDoubleEncoded(scriptCbor);

        String cborHex;
        if (doubleEncoded) {
            cborHex = scriptCbor;
        } else {
            try {
                ByteString bs = new ByteString(HexUtil.decodeHexString(scriptCbor));
                cborHex = HexUtil.encodeHexString(CborSerializationUtil.serialize(bs));
            } catch (CborException e) {
                throw new CborRuntimeException("Error serializing script code", e);
            }
        }

        String calculatedScriptHash;
        PlutusScript plutusScript;
        try {
            PlutusVersion[] versions = new PlutusVersion[] {PlutusVersion.v1, PlutusVersion.v2};
            for(PlutusVersion version: versions) {
                plutusScript = getPlutusScript(cborHex, version);
                calculatedScriptHash = HexUtil.encodeHexString(plutusScript.getScriptHash());
                if (scriptHash.equals(calculatedScriptHash)) {
                    return Optional.of(plutusScript);
                }
            }

            return Optional.empty();
        } catch (CborSerializationException e) {
            throw new CborRuntimeException(e);
        }
    }

    /**
     * Get PlutusScript from scritp cbor in hex and PlutusVersion
     * @param cborHex Script cbor in hex
     * @param plutusVersion PlutusVersion
     * @return PlutusScript
     */
    public static PlutusScript getPlutusScript(String cborHex, PlutusVersion plutusVersion) {
        switch (plutusVersion) {
            case v1:
                return PlutusV1Script.builder()
                        .cborHex(cborHex)
                        .build();
            case v2:
                return PlutusV2Script.builder()
                        .cborHex(cborHex)
                        .build();
            default:
                throw new IllegalArgumentException("Invalid plutus version found " + plutusVersion);
        }
    }
}

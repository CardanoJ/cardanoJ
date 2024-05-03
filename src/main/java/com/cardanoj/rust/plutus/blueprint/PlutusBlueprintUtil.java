package com.cardanoj.rust.plutus.blueprint;

import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.ByteString;
import com.cardanoj.rust.common.cbor.CborSerializationUtil;
import com.cardanoj.rust.plutus.blueprint.model.PlutusVersion;
import com.cardanoj.rust.plutus.spec.PlutusScript;
import com.cardanoj.rust.plutus.spec.PlutusV1Script;
import com.cardanoj.rust.plutus.spec.PlutusV2Script;
import com.cardanoj.rust.util.HexUtil;

/**
 * Plutus blueprint utility class
 */
public class PlutusBlueprintUtil {

    /**
     * Convert plutus blueprint's compiled code to PlutusScript
     * @param compiledCode - compiled code from plutus blueprint
     * @param plutusVersion - Plutus version
     * @return PlutusScript
     */
    public static PlutusScript getPlutusScriptFromCompiledCode(String compiledCode, PlutusVersion plutusVersion) {
        //Do double encoding for aiken compileCode
        ByteString bs = new ByteString(HexUtil.decodeHexString(compiledCode));
        try {
            String cborHex = HexUtil.encodeHexString(CborSerializationUtil.serialize(bs));
            if (plutusVersion.equals(PlutusVersion.v1)) {
                return PlutusV1Script.builder()
                        .cborHex(cborHex)
                        .build();
            } else if (plutusVersion.equals(PlutusVersion.v2)) {
                return PlutusV2Script.builder()
                        .cborHex(cborHex)
                        .build();
            } else
                throw new RuntimeException("Unsupported Plutus version" + plutusVersion);
        } catch (CborException e) {
            throw new RuntimeException(e);
        }
    }
}

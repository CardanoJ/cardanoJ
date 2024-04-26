package com.cardanoj.plutus.blueprint;

import com.cardanoj.plutus.blueprint.model.PlutusVersion;
import com.cardanoj.plutus.spec.PlutusScript;
import com.cardanoj.plutus.spec.PlutusV1Script;
import com.cardanoj.plutus.spec.PlutusV2Script;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlutusBlueprintUtilTest {

    @Test
    void getPlutusScriptFromCompiledCodeV2() {

        PlutusScript plutusScript = PlutusBlueprintUtil.getPlutusScriptFromCompiledCode("581801000032223253330043370e00290020a4c2c6eb40095cd1", PlutusVersion.v2);

        assertThat(plutusScript).isInstanceOf(PlutusV2Script.class);
        assertThat(plutusScript.getCborHex()).contains("581801000032223253330043370e00290020a4c2c6eb40095cd1");
        assertThat(plutusScript.getCborHex().length()).isGreaterThan("581801000032223253330043370e00290020a4c2c6eb40095cd1".length());
    }

    @Test
    void getPlutusScriptFromCompiledCodeV1() {

        PlutusScript plutusScript = PlutusBlueprintUtil.getPlutusScriptFromCompiledCode("581801000032223253330043370e00290020a4c2c6eb40095cd1", PlutusVersion.v1);

        assertThat(plutusScript).isInstanceOf(PlutusV1Script.class);
        assertThat(plutusScript.getCborHex()).contains("581801000032223253330043370e00290020a4c2c6eb40095cd1");
        assertThat(plutusScript.getCborHex().length()).isGreaterThan("581801000032223253330043370e00290020a4c2c6eb40095cd1".length());
    }
}

package com.cardanoj.transaction.spec;

import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.Map;
import com.cardanoj.common.cbor.CborSerializationUtil;
import com.cardanoj.exception.CborDeserializationException;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.plutus.spec.PlutusV1Script;
import com.cardanoj.plutus.spec.PlutusV2Script;
import com.cardanoj.util.HexUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionWitnessSetTest {

    @Test
    void serDeSerWithPlutusV2Script() throws CborSerializationException, CborDeserializationException, CborException {
        PlutusV1Script plutusV1Script1 = PlutusV1Script.builder()
                .type("PlutusScriptV1")
                .cborHex("4e3d01000033222220051200120011")
                .build();

        PlutusV2Script plutusV2Script1 = PlutusV2Script.builder()
                .type("PlutusScriptV2")
                .cborHex("4e4d01000033222220051200120011")
                .build();

        PlutusV2Script plutusV2Script2 = PlutusV2Script.builder()
                .type("PlutusScriptV2")
                .cborHex("4e5def000033222220051200120011")
                .build();

        TransactionWitnessSet transactionWitnessSet = TransactionWitnessSet.builder()
                .plutusV1Scripts(List.of(plutusV1Script1))
                .plutusV2Scripts(List.of(plutusV2Script1, plutusV2Script2))
                .build();

        Map serDI = transactionWitnessSet.serialize();

        TransactionWitnessSet deTransactionWitnessMap = TransactionWitnessSet.deserialize(serDI);

        assertThat(deTransactionWitnessMap.getPlutusV1Scripts()).hasSize(1);
        assertThat(deTransactionWitnessMap.getPlutusV1Scripts()).isEqualTo(transactionWitnessSet.getPlutusV1Scripts());
        assertThat(deTransactionWitnessMap.getPlutusV2Scripts()).isEqualTo(transactionWitnessSet.getPlutusV2Scripts());

        System.out.println(HexUtil.encodeHexString(CborSerializationUtil.serialize(serDI)));
    }

    @Test
    void deSer() throws CborDeserializationException {
        String serHex = "a203814e3d0100003322222005120012001106824e4d010000332222200512001200114e5def000033222220051200120011";

        TransactionWitnessSet witnessSet =
                TransactionWitnessSet.deserialize((Map) CborSerializationUtil.deserialize(HexUtil.decodeHexString(serHex)));

        assertThat(witnessSet.getPlutusV1Scripts()).hasSize(1);
        assertThat(witnessSet.getPlutusV2Scripts()).hasSize(2);
    }

}

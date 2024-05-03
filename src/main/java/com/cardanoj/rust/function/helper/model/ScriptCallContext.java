package com.cardanoj.rust.function.helper.model;

import com.cardanoj.rust.coreapi.model.Utxo;
import com.cardanoj.rust.plutus.spec.ExUnits;
import com.cardanoj.rust.plutus.spec.PlutusScript;
import com.cardanoj.rust.plutus.spec.RedeemerTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A class for Plutus script specific data used in transaction
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScriptCallContext<T, K> {
    private PlutusScript script;
    private Utxo utxo;
    private T datum;
    private K redeemer;

    @Builder.Default
    private RedeemerTag redeemerTag = RedeemerTag.Spend;

    private ExUnits exUnits;
}

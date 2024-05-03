package com.cardanoj.rust.coinselection.impl.model;

import com.cardanoj.rust.coreapi.model.Utxo;
import com.cardanoj.rust.transaction.spec.TransactionOutput;
import com.cardanoj.rust.transaction.spec.Value;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class SelectionResult {

    private final List<Utxo> selection;
    private final Set<TransactionOutput> outputs;
    private final List<Utxo> remaining;
    private final Value amount;
    private final Value change;
}

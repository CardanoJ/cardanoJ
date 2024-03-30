package com.cardanoj.coinselection.impl.model;

import com.cardanoj.coreapi.model.Utxo;
import com.cardanoj.transaction.spec.TransactionOutput;
import com.cardanoj.transaction.spec.Value;
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

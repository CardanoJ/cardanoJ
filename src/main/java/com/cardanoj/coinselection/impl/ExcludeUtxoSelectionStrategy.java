package com.cardanoj.coinselection.impl;

import com.cardanoj.coreapi.model.Amount;
import com.cardanoj.coreapi.model.Utxo;
import com.cardanoj.coinselection.UtxoSelectionStrategy;
import com.cardanoj.plutus.spec.PlutusData;
import com.cardanoj.transaction.spec.TransactionInput;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a wrapper {@link UtxoSelectionStrategy} implementation to exclude a list of Utxos from the selection process.
 * This is useful when you want to exclude a list of Utxos from the selection process. The actual selection is delegated to the
 * underlying {@link UtxoSelectionStrategy} implementation.
 */
public class ExcludeUtxoSelectionStrategy implements UtxoSelectionStrategy {
    private final UtxoSelectionStrategy utxoSelectionStrategy;
    private final Set<Utxo> excludeList;

    public ExcludeUtxoSelectionStrategy(UtxoSelectionStrategy utxoSelectionStrategy, Set<TransactionInput> inputsToExclude) {
        this.utxoSelectionStrategy = utxoSelectionStrategy;
        if (inputsToExclude != null && !inputsToExclude.isEmpty()) {
            excludeList = inputsToExclude.stream().map(input -> Utxo.builder()
                    .txHash(input.getTransactionId())
                    .outputIndex(input.getIndex())
                    .build()).collect(Collectors.toSet());
        } else {
            excludeList = Collections.emptySet();
        }
    }

    @Override
    public Set<Utxo> select(String address, List<Amount> outputAmounts, String datumHash, PlutusData inlineDatum, Set<Utxo> utxosToExclude,
                            int maxUtxoSelectionLimit) {
        Set<Utxo> finalUtxoToExclude;
        if (utxosToExclude != null) {
            finalUtxoToExclude = new HashSet<>(utxosToExclude);
            finalUtxoToExclude.addAll(this.excludeList);
        } else {
            finalUtxoToExclude = this.excludeList;
        }
        return utxoSelectionStrategy.select(address, outputAmounts, datumHash, inlineDatum, finalUtxoToExclude, maxUtxoSelectionLimit);
    }

    @Override
    public UtxoSelectionStrategy fallback() {
        return utxoSelectionStrategy.fallback();
    }

    @Override
    public void setIgnoreUtxosWithDatumHash(boolean ignoreUtxosWithDatumHash) {
        utxoSelectionStrategy.setIgnoreUtxosWithDatumHash(ignoreUtxosWithDatumHash);
    }
}

package com.cardanoj.function;

import com.cardanoj.transaction.spec.TransactionInput;
import com.cardanoj.transaction.spec.TransactionOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Represents a function to build inputs from required outputs
 * It takes a list of {@link TransactionOutput} as input to return a list of {@link TransactionInput} and any additional {@link TransactionOutput} as change
 */
@FunctionalInterface
public interface TxInputBuilder extends BiFunction<TxBuilderContext, List<TransactionOutput>, TxInputBuilder.Result> {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    class Result {
        List<TransactionInput> inputs;
        List<TransactionOutput> changes;
    }
}

package com.cardanoj.rust.backend.api;

import com.cardanoj.rust.coreapi.TransactionProcessor;
import com.cardanoj.rust.coreapi.exception.ApiException;
import com.cardanoj.rust.coreapi.model.EvaluationResult;
import com.cardanoj.rust.coreapi.model.Result;
import com.cardanoj.rust.coreapi.model.Utxo;

import java.util.List;
import java.util.Set;

/**
 * Default implementation of TransactionProcessor which uses Backend service's TransactionService
 */
public class DefaultTransactionProcessor implements TransactionProcessor {
    private final TransactionService transactionService;

    public DefaultTransactionProcessor(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public Result<String> submitTransaction(byte[] cborData) throws ApiException {
        return transactionService.submitTransaction(cborData);
    }

    @Override
    public Result<List<EvaluationResult>> evaluateTx(byte[] cborData, Set<Utxo> inputUtxos) throws ApiException {
        return transactionService.evaluateTx(cborData);
    }
}

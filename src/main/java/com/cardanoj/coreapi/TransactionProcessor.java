package com.cardanoj.coreapi;

import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;

/**
 * Implement this interface to provide transaction submission capability.
 */
public interface TransactionProcessor extends TransactionEvaluator {

    Result<String> submitTransaction(byte[] cborData) throws ApiException;
}

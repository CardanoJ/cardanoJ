package com.cardanoj.rust.coreapi;

import com.cardanoj.rust.coreapi.exception.ApiException;
import com.cardanoj.rust.coreapi.model.Result;

/**
 * Implement this interface to provide transaction submission capability.
 */
public interface TransactionProcessor extends TransactionEvaluator {

    Result<String> submitTransaction(byte[] cborData) throws ApiException;
}

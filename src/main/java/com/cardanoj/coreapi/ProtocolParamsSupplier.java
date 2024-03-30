package com.cardanoj.coreapi;

import com.cardanoj.coreapi.model.ProtocolParams;

/**
 * Implement this interface to provide ProtocolParams
 */
@FunctionalInterface
public interface ProtocolParamsSupplier {
    ProtocolParams getProtocolParams();
}

package com.cardanoj.rust.coreapi;

import com.cardanoj.rust.coreapi.model.ProtocolParams;

/**
 * Implement this interface to provide ProtocolParams
 */
@FunctionalInterface
public interface ProtocolParamsSupplier {
    ProtocolParams getProtocolParams();
}

package com.cardanoj.backend.api;

import com.cardanoj.coreapi.ProtocolParamsSupplier;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.exception.ApiRuntimeException;
import com.cardanoj.coreapi.model.ProtocolParams;
import com.cardanoj.coreapi.model.Result;

public class DefaultProtocolParamsSupplier implements ProtocolParamsSupplier {
    private final EpochService epochService;

    public DefaultProtocolParamsSupplier(EpochService epochService) {
        this.epochService = epochService;
    }

    @Override
    public ProtocolParams getProtocolParams() {
        try {
            Result<ProtocolParams> result = epochService.getProtocolParameters();
            if (result.isSuccessful())
                return result.getValue();
            else
                throw new ApiRuntimeException("Error fetching protocol params : " + result);
        } catch (ApiException apiException) {
            throw new ApiRuntimeException("Error fetching protocol params", apiException);
        }
    }
}

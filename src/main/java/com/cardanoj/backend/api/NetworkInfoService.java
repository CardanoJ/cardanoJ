package com.cardanoj.backend.api;

import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.backend.model.Genesis;

public interface NetworkInfoService {
    /**
     *
     * @return Genesis Info
     * @throws ApiException
     */
    Result<Genesis> getNetworkInfo() throws ApiException;
}

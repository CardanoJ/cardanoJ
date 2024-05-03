package com.cardanoj.rust.backend.api;

import com.cardanoj.rust.coreapi.exception.ApiException;
import com.cardanoj.rust.coreapi.model.Result;
import com.cardanoj.rust.backend.model.Genesis;

public interface NetworkInfoService {
    /**
     *
     * @return Genesis Info
     * @throws ApiException
     */
    Result<Genesis> getNetworkInfo() throws ApiException;
}

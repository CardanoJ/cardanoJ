package com.cardanoj.rust.backend.api;

import com.cardanoj.rust.coreapi.exception.ApiException;
import com.cardanoj.rust.coreapi.model.ProtocolParams;
import com.cardanoj.rust.coreapi.model.Result;
import com.cardanoj.rust.backend.model.EpochContent;

public interface EpochService {

    /**
     * Get latest epoch
     * @return
     * @throws ApiException
     */
    Result<EpochContent> getLatestEpoch() throws ApiException;

    /**
     * Get epoch content by number
     * @param epoch
     * @return
     * @throws ApiException
     */
    Result<EpochContent> getEpoch(Integer epoch) throws ApiException;

    /**
     * Get protocol parameters at epoch
     * @param epoch
     * @return
     * @throws ApiException
     */
    Result<ProtocolParams> getProtocolParameters(Integer epoch) throws ApiException;

    /**
     * Get current protocol parameters
     * @return
     * @throws ApiException
     */
    Result<ProtocolParams> getProtocolParameters() throws ApiException;
}

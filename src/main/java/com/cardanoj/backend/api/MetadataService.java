package com.cardanoj.backend.api;

import com.cardanoj.coreapi.common.OrderEnum;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.backend.model.metadata.MetadataCBORContent;
import com.cardanoj.backend.model.metadata.MetadataJSONContent;
import com.cardanoj.backend.model.metadata.MetadataLabel;

import java.math.BigInteger;
import java.util.List;

public interface MetadataService {

    /**
     * Get metadata for a transaction in JSON format
     * @param txnHash
     * @return
     * @throws ApiException
     */
    Result<List<MetadataJSONContent>> getJSONMetadataByTxnHash(String txnHash) throws ApiException;

    /**
     * Get metadata for a txn in CBOR format
     * @param txnHash
     * @return
     * @throws ApiException
     */
    Result<List<MetadataCBORContent>> getCBORMetadataByTxnHash(String txnHash) throws ApiException;

    /**
     * Get all metadata labels
     * @param count
     * @param page
     * @param order
     * @return
     * @throws ApiException
     */
    Result<List<MetadataLabel>> getMetadataLabels(int count, int page, OrderEnum order) throws ApiException;

    /**
     * Get list of {@link MetadataJSONContent} by label
     * @param label
     * @param count
     * @param page
     * @param order
     * @return
     * @throws ApiException
     */
    Result<List<MetadataJSONContent>> getJSONMetadataByLabel(BigInteger label, int count, int page, OrderEnum order) throws ApiException;

    /**
     * Get list of {@link MetadataCBORContent} by label
     * @param label
     * @param count
     * @param page
     * @param order
     * @return
     * @throws ApiException
     */
    Result<List<MetadataCBORContent>> getCBORMetadataByLabel(BigInteger label, int count, int page, OrderEnum order) throws ApiException;

}

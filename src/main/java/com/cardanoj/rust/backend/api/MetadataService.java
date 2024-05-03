package com.cardanoj.rust.backend.api;

import com.cardanoj.rust.coreapi.common.OrderEnum;
import com.cardanoj.rust.coreapi.exception.ApiException;
import com.cardanoj.rust.coreapi.model.Result;
import com.cardanoj.rust.backend.model.metadata.MetadataCBORContent;
import com.cardanoj.rust.backend.model.metadata.MetadataJSONContent;
import com.cardanoj.rust.backend.model.metadata.MetadataLabel;

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

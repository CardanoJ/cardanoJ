package com.cardanoj.backend.api;

import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.backend.model.Block;

import java.math.BigInteger;

public interface BlockService {

    /**
     * Get the Latest Block
     *
     * @return Get latest block
     */
    Result<Block> getLatestBlock() throws ApiException;

    /**
     * Get Block by Block Hash
     *
     * @param blockHash
     * @return Get block details by block hash
     */
    Result<Block> getBlockByHash(String blockHash) throws ApiException;

    /**
     * Get Block by Block Number
     *
     * @param blockNumber
     * @return Get block by block number
     */
    Result<Block> getBlockByNumber(BigInteger blockNumber) throws ApiException;
}

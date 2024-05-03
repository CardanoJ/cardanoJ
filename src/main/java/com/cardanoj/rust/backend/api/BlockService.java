package com.cardanoj.rust.backend.api;

import com.cardanoj.rust.coreapi.exception.ApiException;
import com.cardanoj.rust.coreapi.model.Result;
import com.cardanoj.rust.backend.model.Block;

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

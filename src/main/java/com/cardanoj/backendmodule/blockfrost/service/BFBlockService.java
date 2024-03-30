package com.cardanoj.backendmodule.blockfrost.service;

import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.backend.api.BlockService;
import com.cardanoj.backendmodule.blockfrost.service.http.BlockApi;
import com.cardanoj.backend.model.Block;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigInteger;

public class BFBlockService extends BFBaseService implements BlockService {

    private final BlockApi blockApi;

    public BFBlockService(String baseUrl, String projectId) {
        super(baseUrl, projectId);
        this.blockApi = getRetrofit().create(BlockApi.class);
    }


    @Override
    public Result<Block> getLatestBlock() throws ApiException {
        Call<Block> blockCall = blockApi.getLatestBlock(getProjectId());

        try {
            Response<Block> response = blockCall.execute();
            if(response.isSuccessful())
                return Result.success(response.toString()).withValue(response.body()).code(response.code());
            else
                return Result.error(response.errorBody().string()).code(response.code());

        } catch (IOException e) {
            throw new ApiException("Error getting latest block", e);
        }
    }

    @Override
    public Result<Block> getBlockByHash(String blockHash) throws ApiException {
        Call<Block> blockCall = blockApi.getBlockByHash(getProjectId(), blockHash);

        try {
            Response<Block> response = blockCall.execute();
            if(response.isSuccessful())
                return Result.success(response.toString()).withValue(response.body()).code(response.code());
            else
                return Result.error(response.errorBody().string()).code(response.code());

        } catch (IOException e) {
            throw new ApiException("Error getting block by hash", e);
        }
    }

    @Override
    public Result<Block> getBlockByNumber(BigInteger blockNumber) throws ApiException {
        Call<Block> blockCall = blockApi.getBlockByNumber(getProjectId(), blockNumber);

        try {
            Response<Block> response = blockCall.execute();
            if(response.isSuccessful())
                return Result.success(response.toString()).withValue(response.body()).code(response.code());
            else
                return Result.error(response.errorBody().string()).code(response.code());

        } catch (IOException e) {
            throw new ApiException("Error getting block by number", e);
        }
    }
}

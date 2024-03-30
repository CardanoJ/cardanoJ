package com.cardanoj.backendmodule.blockfrost.service;

import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.backend.api.NetworkInfoService;
import com.cardanoj.backendmodule.blockfrost.service.http.CardanoLedgerApi;
import com.cardanoj.backend.model.Genesis;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class BFNetworkService extends BFBaseService implements NetworkInfoService {

    private final CardanoLedgerApi ledgerApi;

    public BFNetworkService(String baseUrl, String projectId) {
        super(baseUrl, projectId);
        this.ledgerApi = getRetrofit().create(CardanoLedgerApi.class);
    }

    @Override
    public Result<Genesis> getNetworkInfo() throws ApiException {
        Call<Genesis> genesisCall = ledgerApi.genesis(getProjectId());
        try {
            Response<Genesis> response = genesisCall.execute();
            if (response.isSuccessful())
                return Result.success(response.toString()).withValue(response.body()).code(response.code());
            else
                return Result.error(response.errorBody().string()).code(response.code());

        } catch (IOException e) {
            throw new ApiException("Error getting genesis info", e);
        }
    }
}

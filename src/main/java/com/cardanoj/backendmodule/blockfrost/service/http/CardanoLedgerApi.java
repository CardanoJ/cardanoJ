package com.cardanoj.backendmodule.blockfrost.service.http;

import com.cardanoj.backend.model.Genesis;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CardanoLedgerApi {
    @GET("genesis")
    Call<Genesis> genesis(@Header("project_id") String projectId);
}

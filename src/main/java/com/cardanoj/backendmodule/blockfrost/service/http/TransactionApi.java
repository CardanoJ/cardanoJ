package com.cardanoj.backendmodule.blockfrost.service.http;

import com.cardanoj.backend.model.TransactionContent;
import com.cardanoj.backend.model.TxContentRedeemers;
import com.cardanoj.backend.model.TxContentUtxo;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface TransactionApi {
    @Headers("Content-Type: application/cbor")
    @POST("tx/submit")
    Call<String> submit(@Header("project_id") String projectId, @Body RequestBody signedTxn);

    @GET("txs/{hash}")
    Call<TransactionContent> getTransaction(@Header("project_id")  String projectId, @Path("hash") String txnHash);

    @GET("txs/{hash}/utxos")
    Call<TxContentUtxo> getTransactionUtxos(@Header("project_id")  String projectId, @Path("hash") String txnHash);

    @GET("txs/{hash}/redeemers")
    Call<List<TxContentRedeemers>> getTransactionRedeemers(@Header("project_id")  String projectId, @Path("hash") String txnHash);


    @Headers("Content-Type: application/cbor")
    @POST("utils/txs/evaluate")
    Call<Object> evaluateTx(@Header("project_id") String projectId, @Body RequestBody txn);
}

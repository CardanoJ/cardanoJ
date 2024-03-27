package com.org.cardanoJ.blockfrost.service;

import java.io.IOException;

import com.org.cardanoJ.backend.ScriptService;
import com.org.cardanoJ.blockfrost.exception.ApiException;
import com.org.cardanoJ.blockfrost.service.http.ScriptApi;
import com.org.cardanoJ.core.model.Result;
import com.org.cardanoJ.core.model.ScriptDatum;

import retrofit2.Call;
import retrofit2.Response;

public class BlockFrostScriptService extends BlockFrostBaseService implements ScriptService {
	
	private ScriptApi scriptApi;
	public BlockFrostScriptService(String baseUrl, String projectId) {
		super(baseUrl, projectId);
		this.scriptApi = getRetrofit().create(ScriptApi.class);
	}

	@Override
	public Result<ScriptDatum> getScriptDatum(String datumHash) throws ApiException {
		Call<ScriptDatum> call = scriptApi.getDatumValue(getProjectId(), datumHash);
		
        try {
            Response<ScriptDatum> response = call.execute();
            System.out.println(response);
            return processResponse(response);
        } catch (IOException e) {
            throw new ApiException("Exception while fetching script datum for hash: " + datumHash, e);
        }
	}

	@Override
	public Result<ScriptDatum> getReedemer(String txHash) throws ApiException {
		
		    
		    Call<ScriptDatum> call = scriptApi.getRedeemers(getProjectId(), txHash);
		    
		    try {
		     
		        Response<ScriptDatum> response = call.execute();
		        
		       
		        if (response.isSuccessful()) {
		           
		            return processResponse(response);
		        } else {
		            
		            return Result.error("Error: " + response.code());
		        }
		    } catch (IOException e) {
		     
		        throw new ApiException("Exception while fetching redeemers for transaction hash: " + txHash, e);
		    }
	}

}

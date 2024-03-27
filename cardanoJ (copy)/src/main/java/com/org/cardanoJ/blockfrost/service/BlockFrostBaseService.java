package com.org.cardanoJ.blockfrost.service;

import java.io.IOException;

import com.org.cardanoJ.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.java.Log;
import lombok.extern.slf4j.XSlf4j;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class BlockFrostBaseService {
	 private final String baseUrl;
	    private final String projectId;

	    public BlockFrostBaseService(String baseUrl, String projectId) {
	        this.baseUrl = baseUrl;
	        this.projectId = projectId;
	    
    }

	    protected Retrofit getRetrofit() {
	        return new Retrofit.Builder()
	                .baseUrl(getBaseUrl())
	                .addConverterFactory(JacksonConverterFactory.create())
	                .build();
	    }
	    

	    public String getBaseUrl() {
	        return baseUrl;
	    }

	    public String getProjectId() {
	        return projectId;
	    }
	    
	    protected <T> Result<T> processResponse(Response<T> response) throws IOException {
	        if (response.isSuccessful())
	    
	            return Result.success(response.toString()).withValue(response.body()).code(response.code());
	        else
	            return Result.error(response.errorBody().string()).code(response.code());
	    }
}


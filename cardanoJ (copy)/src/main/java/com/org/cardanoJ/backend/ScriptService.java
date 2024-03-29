package com.org.cardanoJ.backend;

import com.org.cardanoJ.blockfrost.exception.ApiException;
import com.org.cardanoJ.core.model.Result;
import com.org.cardanoJ.core.model.ScriptDatum;

public interface ScriptService {
	 /**
     * Datum value
     * Query JSON value of a datum by its hash.
     *
     * @param datumHash Hash of the datum. (required)
     * @return ScriptDatum
     */
    Result<ScriptDatum> getScriptDatum(String datumHash) throws ApiException;
    
    
    Result<ScriptDatum> getReedemer(String txHash) throws ApiException;
}

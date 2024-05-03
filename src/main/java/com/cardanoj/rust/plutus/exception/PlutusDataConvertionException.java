package com.cardanoj.rust.plutus.exception;

public class PlutusDataConvertionException extends RuntimeException {

    public PlutusDataConvertionException(String msg) {
        super(msg);
    }

    public PlutusDataConvertionException(String msg, Exception exception) {
        super(msg, exception);
    }
}

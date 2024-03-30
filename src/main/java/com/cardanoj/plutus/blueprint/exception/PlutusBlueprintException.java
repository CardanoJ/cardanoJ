package com.cardanoj.plutus.blueprint.exception;

public class PlutusBlueprintException extends RuntimeException {

    public PlutusBlueprintException(String msg) {
        super(msg);
    }

    public PlutusBlueprintException(String msg, Throwable t) {
        super(msg, t);
    }

    public PlutusBlueprintException(Throwable t) {
        super(t);
    }
}

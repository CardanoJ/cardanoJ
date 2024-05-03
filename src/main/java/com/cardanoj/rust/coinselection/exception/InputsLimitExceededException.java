package com.cardanoj.rust.coinselection.exception;

import com.cardanoj.rust.coinselection.exception.base.CoinSelectionException;

public class InputsLimitExceededException extends CoinSelectionException {

    public InputsLimitExceededException() {
        super("INPUT_LIMIT_EXCEEDED");
    }

    public InputsLimitExceededException(String message) {
        super(message);
    }
}

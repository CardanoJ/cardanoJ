package com.cardanoj.coinselection.exception;

import com.cardanoj.coinselection.exception.base.CoinSelectionException;

public class InputsExhaustedException extends CoinSelectionException {

    public InputsExhaustedException() {
        super("INPUTS_EXHAUSTED");
    }
}

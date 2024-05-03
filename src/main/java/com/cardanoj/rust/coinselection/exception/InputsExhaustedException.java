package com.cardanoj.rust.coinselection.exception;

import com.cardanoj.rust.coinselection.exception.base.CoinSelectionException;

public class InputsExhaustedException extends CoinSelectionException {

    public InputsExhaustedException() {
        super("INPUTS_EXHAUSTED");
    }
}

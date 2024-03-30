package com.cardanoj.transaction.spec.cert;

import co.nstant.in.cbor.model.Array;
import com.cardanoj.exception.CborSerializationException;

public interface Relay {
    Array serialize() throws CborSerializationException;
}

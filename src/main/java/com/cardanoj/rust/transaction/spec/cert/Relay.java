package com.cardanoj.rust.transaction.spec.cert;

import co.nstant.in.cbor.model.Array;
import com.cardanoj.rust.exception.CborSerializationException;

public interface Relay {
    Array serialize() throws CborSerializationException;
}

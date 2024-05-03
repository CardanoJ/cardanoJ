package com.cardanoj.rust.plutus.api;

import com.cardanoj.rust.plutus.spec.PlutusData;

public interface PlutusObjectConverter {

    PlutusData toPlutusData(Object o);

}

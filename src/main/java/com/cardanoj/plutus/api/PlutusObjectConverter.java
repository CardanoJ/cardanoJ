package com.cardanoj.plutus.api;

import com.cardanoj.plutus.spec.PlutusData;

public interface PlutusObjectConverter {

    PlutusData toPlutusData(Object o);

}

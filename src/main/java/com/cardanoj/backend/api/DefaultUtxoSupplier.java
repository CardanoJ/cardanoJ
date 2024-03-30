package com.cardanoj.backend.api;

import com.cardanoj.coreapi.UtxoSupplier;
import com.cardanoj.coreapi.common.OrderEnum;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.exception.ApiRuntimeException;
import com.cardanoj.coreapi.model.Utxo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DefaultUtxoSupplier implements UtxoSupplier {
    private final UtxoService utxoService;

    public DefaultUtxoSupplier(UtxoService utxoService){
        this.utxoService = utxoService;
    }

    @Override
    public List<Utxo> getPage(String address, Integer nrOfItems, Integer page, OrderEnum order) {
        try{
            var result = utxoService.getUtxos(address, nrOfItems != null ? nrOfItems : UtxoSupplier.DEFAULT_NR_OF_ITEMS_TO_FETCH, page != null ? page + 1 : 1, order);
            return result != null && result.getValue() != null ? result.getValue() : Collections.emptyList();
        } catch (ApiException e) {
            throw new ApiRuntimeException(e);
        }
    }

    @Override
    public Optional<Utxo> getTxOutput(String txHash, int outputIndex) {
        try {
            var result = utxoService.getTxOutput(txHash, outputIndex);
            return result != null && result.getValue() != null ? Optional.of(result.getValue()) : Optional.empty();
        } catch (ApiException e) {
            throw new ApiRuntimeException(e);
        }
    }
}

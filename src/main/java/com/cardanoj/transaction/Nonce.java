package com.cardanoj.transaction;

import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.UnsignedInteger;
import com.cardanoj.common.cbor.CborSerializationUtil;
import com.cardanoj.exception.CborDeserializationException;
import com.cardanoj.exception.CborSerializationException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class Nonce {
    private byte[] hash;

    public DataItem serialize() throws CborSerializationException {
        Array array = new Array();
        if (hash != null) {
            array.add(new UnsignedInteger(1));
            array.add(new ByteString(hash));
        } else {
            array.add(new UnsignedInteger(0));
        }

        return array;
    }

    public static Nonce deserialize(DataItem di) throws CborDeserializationException {
        if (di == null) return null;

        List<DataItem> dataItemList = ((Array) di).getDataItems();
        int i = CborSerializationUtil.toInt(dataItemList.get(0));

        if (i == 0) {
            return new Nonce(null);
        } else if (i == 1) {
            byte[] hash = CborSerializationUtil.toBytes(dataItemList.get(1));
            return new Nonce(hash);
        } else {
            throw new CborDeserializationException("Nonce deserialization failed. No variant matched. i=" + i);
        }
    }
}

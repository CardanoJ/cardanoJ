package com.cardanoj.transaction.spec;

import co.nstant.in.cbor.model.*;
import com.cardanoj.exception.CborDeserializationException;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.util.HexUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Update {
    private java.util.Map<String, ProtocolParamUpdate> protocolParamUpdates;
    private int epoch;

    public void addProtocolParameterUpdate(String genesisHash, ProtocolParamUpdate protocolParamUpdate) {
        if (protocolParamUpdates == null)
            this.protocolParamUpdates = new HashMap<>();
        protocolParamUpdates.put(genesisHash, protocolParamUpdate);
    }

    public DataItem serialize() throws CborSerializationException {
        Array updateArray = new Array();

        Map protoParamUpdatesMap = new Map();
        for(String genesisHash: protocolParamUpdates.keySet()) {
            ProtocolParamUpdate protocolParam = protocolParamUpdates.get(genesisHash);
            protoParamUpdatesMap.put(new ByteString(HexUtil.decodeHexString(genesisHash)), protocolParam.serialize());
        }
        updateArray.add(protoParamUpdatesMap);

        //epoch
        updateArray.add(new UnsignedInteger(epoch));

        return updateArray;
    }

    public static Update deserialize(DataItem din) throws CborDeserializationException {
        Array updateArray = (Array) din;
        if (updateArray.getDataItems().size() != 2)
            throw new CborDeserializationException("Invalid number of dataitems for update. Expected 2, found: " + updateArray.getDataItems().size());

        java.util.Map deProtoParamUpdatesMap = new LinkedHashMap<>();
        Map protoParamUpdatesMap = (Map) updateArray.getDataItems().get(0);
        for (DataItem genhashDI: protoParamUpdatesMap.getKeys()) {
            String genesisHash = HexUtil.encodeHexString(((ByteString)genhashDI).getBytes());
            ProtocolParamUpdate protocolParamUpdate = ProtocolParamUpdate.deserialize(protoParamUpdatesMap.get(genhashDI));

            deProtoParamUpdatesMap.put(genesisHash, protocolParamUpdate);
        }

        int epoch = ((UnsignedInteger)updateArray.getDataItems().get(1)).getValue().intValue();

        return new Update(deProtoParamUpdatesMap, epoch);
    }

}

package com.cardanoj.rust.plutus.spec;


import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.ByteString;
import co.nstant.in.cbor.model.DataItem;
import com.cardanoj.rust.exception.CborSerializationException;
import com.cardanoj.rust.util.HexUtil;
import com.cardanoj.rust.spec.Script;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class PlutusScript implements Script {

    @Setter(AccessLevel.NONE)
    @Getter
    protected String type;
    protected String description;
    protected String cborHex;

    public ByteString serializeAsDataItem() throws CborSerializationException {
        byte[] bytes = HexUtil.decodeHexString(cborHex);
        if (bytes.length > 0) {
            try {
                List<DataItem> diList = CborDecoder.decode(bytes);
                if (diList == null || diList.size() == 0)
                    throw new CborSerializationException("Serialization failed");

                DataItem di = diList.get(0);
                return (ByteString)di;
            } catch (CborException e) {
                throw new CborSerializationException("Serialization failed", e);
            }
        } else {
            return null;
        }
    }

    @Override
    public byte[] serializeScriptBody() throws CborSerializationException {
        return serializeAsDataItem().getBytes();
    }

}

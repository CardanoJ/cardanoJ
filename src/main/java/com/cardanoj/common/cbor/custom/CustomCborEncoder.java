package com.cardanoj.common.cbor.custom;



import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.MajorType;
import co.nstant.in.cbor.model.Map;
import co.nstant.in.cbor.model.SimpleValue;
import co.nstant.in.cbor.model.Tag;
import java.io.OutputStream;

public class CustomCborEncoder extends CborEncoder {
    private final CustomMapEncoder customMapEncoder;

    public CustomCborEncoder(OutputStream outputStream) {
        super(outputStream);
        this.customMapEncoder = new CustomMapEncoder(this, outputStream);
    }

    public void encode(DataItem dataItem) throws CborException {
        if (dataItem.getMajorType().equals(MajorType.MAP)) {
            if (dataItem == null) {
                dataItem = SimpleValue.NULL;
            }

            if (dataItem.hasTag()) {
                Tag tagDi = dataItem.getTag();
                this.encode(tagDi);
            }

            this.customMapEncoder.encode((Map)dataItem);
        } else {
            super.encode(dataItem);
        }

    }
}

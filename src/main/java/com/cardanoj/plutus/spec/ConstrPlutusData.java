package com.cardanoj.plutus.spec;

import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.Tag;
import co.nstant.in.cbor.model.UnsignedInteger;
import com.cardanoj.exception.CborDeserializationException;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.plutus.spec.serializers.ConstrDataJsonDeserializer;
import com.cardanoj.plutus.spec.serializers.ConstrDataJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

@JsonSerialize(
        using = ConstrDataJsonSerializer.class
)
@JsonDeserialize(
        using = ConstrDataJsonDeserializer.class
)
public class ConstrPlutusData implements PlutusData {
    private static final long GENERAL_FORM_TAG = 102L;
    private long alternative;
    private ListPlutusData data;

    public static ConstrPlutusData of(long alternative, PlutusData... plutusDataList) {
        return builder().alternative(alternative).data(ListPlutusData.of(plutusDataList)).build();
    }

    public static ConstrPlutusData deserialize(DataItem di) throws CborDeserializationException {
        Tag tag = di.getTag();
        Long alternative = null;
        ListPlutusData data = null;
        if (102L == tag.getValue()) {
            Array constrArray = (Array)di;
            List<DataItem> dataItems = constrArray.getDataItems();
            if (dataItems.size() != 2) {
                throw new CborDeserializationException("Cbor deserialization failed. Expected 2 DataItem, found : " + dataItems.size());
            }

            alternative = ((UnsignedInteger)dataItems.get(0)).getValue().longValue();
            data = ListPlutusData.deserialize((Array)dataItems.get(1));
        } else {
            alternative = compactCborTagToAlternative(tag.getValue());
            data = ListPlutusData.deserialize((Array)di);
        }

        return builder().alternative(alternative).data(data).build();
    }

    private static Long alternativeToCompactCborTag(long alt) {
        if (alt <= 6L) {
            return 121L + alt;
        } else {
            return alt >= 7L && alt <= 127L ? 1273L + alt : null;
        }
    }

    private static Long compactCborTagToAlternative(long cborTag) {
        if (cborTag >= 121L && cborTag <= 127L) {
            return cborTag - 121L;
        } else {
            return cborTag >= 1280L && cborTag <= 1400L ? cborTag - 1280L + 7L : null;
        }
    }

    public DataItem serialize() throws CborSerializationException {
        Long cborTag = alternativeToCompactCborTag(this.alternative);
        DataItem dataItem = null;
        if (cborTag != null) {
            dataItem = this.data.serialize();
            ((DataItem)dataItem).setTag(cborTag);
        } else {
            Array constrArray = new Array();
            constrArray.add(new UnsignedInteger(this.alternative));
            constrArray.add(this.data.serialize());
            dataItem = constrArray;
            constrArray.setTag(102L);
        }

        return (DataItem)dataItem;
    }

    public static ConstrPlutusDataBuilder builder() {
        return new ConstrPlutusDataBuilder();
    }

    public long getAlternative() {
        return this.alternative;
    }

    public ListPlutusData getData() {
        return this.data;
    }

    public ConstrPlutusData(long alternative, ListPlutusData data) {
        this.alternative = alternative;
        this.data = data;
    }

    public ConstrPlutusData() {
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ConstrPlutusData)) {
            return false;
        } else {
            ConstrPlutusData other = (ConstrPlutusData)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getAlternative() != other.getAlternative()) {
                return false;
            } else {
                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ConstrPlutusData;
    }

    public int hashCode() {
//        int PRIME = true;
        int result = 1;
        long $alternative = this.getAlternative();
        result = result * 59 + (int)($alternative >>> 32 ^ $alternative);
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public static class ConstrPlutusDataBuilder {
        private long alternative;
        private ListPlutusData data;

        ConstrPlutusDataBuilder() {
        }

        public ConstrPlutusDataBuilder alternative(long alternative) {
            this.alternative = alternative;
            return this;
        }

        public ConstrPlutusDataBuilder data(ListPlutusData data) {
            this.data = data;
            return this;
        }

        public ConstrPlutusData build() {
            return new ConstrPlutusData(this.alternative, this.data);
        }

        public String toString() {
            return "ConstrPlutusData.ConstrPlutusDataBuilder(alternative=" + this.alternative + ", data=" + this.data + ")";
        }
    }
}

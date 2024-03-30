package com.cardanoj.common.cbor.custom;


import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.encoder.MapEncoder;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.MajorType;
import co.nstant.in.cbor.model.Map;
import co.nstant.in.cbor.model.SimpleValue;
import com.google.common.primitives.UnsignedBytes;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class CustomMapEncoder extends MapEncoder {
    public CustomMapEncoder(CborEncoder encoder, OutputStream outputStream) {
        super(encoder, outputStream);
    }

    public void encode(Map map) throws CborException {
        Collection<DataItem> keys = map.getKeys();
        if (map.isChunked()) {
            this.encodeTypeChunked(MajorType.MAP);
        } else {
            this.encodeTypeAndLength(MajorType.MAP, keys.size());
        }

        if (!keys.isEmpty()) {
            if (map.isChunked()) {
                this.encodeNonCanonical(map);
                this.encoder.encode(SimpleValue.BREAK);
            } else if (this.encoder.isCanonical() && !(map instanceof SortedMap)) {
                this.encodeCanonical(map);
            } else {
                this.encodeNonCanonical(map);
            }

        }
    }

    private void encodeNonCanonical(Map map) throws CborException {
        Iterator var3 = map.getKeys().iterator();

        while(var3.hasNext()) {
            DataItem key = (DataItem)var3.next();
            this.encoder.encode(key);
            this.encoder.encode(map.get(key));
        }

    }

    private void encodeCanonical(Map map) throws CborException {
        TreeMap<byte[], byte[]> sortedMap = new TreeMap(UnsignedBytes.lexicographicalComparator());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CborEncoder e = new CustomCborEncoder(byteArrayOutputStream);
        Iterator var6 = map.getKeys().iterator();

        while(var6.hasNext()) {
            DataItem key = (DataItem)var6.next();
            e.encode(key);
            byte[] keyBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.reset();
            e.encode(map.get(key));
            byte[] valueBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.reset();
            sortedMap.put(keyBytes, valueBytes);
        }

        var6 = sortedMap.entrySet().iterator();

        while(var6.hasNext()) {
            java.util.Map.Entry<byte[], byte[]> entry = (java.util.Map.Entry)var6.next();
            this.write(entry.getKey());
            this.write(entry.getValue());
        }

    }
}

package com.cardanoj.rust.metadata;

import com.cardanoj.rust.metadata.cbor.CBORMetadata;
import com.cardanoj.rust.metadata.cbor.CBORMetadataList;
import com.cardanoj.rust.metadata.cbor.CBORMetadataMap;

/**
 * Builder class to create Metadata, MetadataMap and MetadataList
 */
public class MetadataBuilder {

    /**
     * Create Metadata object
     * @return Metadata
     */
    public static Metadata createMetadata() {
        return new CBORMetadata();
    }

    /**
     * Create MetadataMap object
     * @return MetadataMap
     */
    public static MetadataMap createMap() {
        return new CBORMetadataMap();
    }

    /**
     * Create MetadataList object
     * @return MetadataList
     */
    public static MetadataList createList() {
        return new CBORMetadataList();
    }
}

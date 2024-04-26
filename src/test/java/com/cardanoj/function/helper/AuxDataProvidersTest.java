package com.cardanoj.function.helper;

import com.cardanoj.coreapi.UtxoSupplier;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.ProtocolParams;
import com.cardanoj.function.BaseTest;
import com.cardanoj.function.TxBuilderContext;
import com.cardanoj.metadata.cbor.CBORMetadata;
import com.cardanoj.transaction.spec.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuxDataProvidersTest extends BaseTest {

    @Mock
    UtxoSupplier utxoSupplier;

    ProtocolParams protocolParams;

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);

        protocolParamJsonFile = "protocol-params.json";
        protocolParams = (ProtocolParams) loadObjectFromJson("protocol-parameters", ProtocolParams.class);
    }

    @Test
    void testMetadataProvider() throws ApiException {
        CBORMetadata metadata1 = new CBORMetadata();
        metadata1.put(BigInteger.valueOf(1000), "hello");

        CBORMetadata metadata2 = new CBORMetadata();
        metadata2.put(BigInteger.valueOf(1001), "value1");

        Transaction transaction = new Transaction();
        TxBuilderContext context = new TxBuilderContext(utxoSupplier, protocolParams);

        AuxDataProviders.metadataProvider(metadata1)
                .andThen(AuxDataProviders.metadataProvider(metadata2))
                .apply(context, transaction);

        CBORMetadata actualMetadata = (CBORMetadata) transaction.getAuxiliaryData().getMetadata();

        assertThat(actualMetadata.get(BigInteger.valueOf(1000))).isEqualTo("hello");
        assertThat(actualMetadata.get(BigInteger.valueOf(1001))).isEqualTo("value1");
    }

    @Test
    void testMetadataProviderSupplier() throws ApiException {
        CBORMetadata metadata1 = new CBORMetadata();
        metadata1.put(BigInteger.valueOf(1000), "hello");

        CBORMetadata metadata2 = new CBORMetadata();
        metadata2.put(BigInteger.valueOf(1001), "value1");

        Transaction transaction = new Transaction();
        TxBuilderContext context = new TxBuilderContext(utxoSupplier, protocolParams);

        AuxDataProviders.metadataProvider(() -> {
            return metadata1;
        }).andThen(AuxDataProviders.metadataProvider(metadata2))
                .apply(context, transaction);

        CBORMetadata actualMetadata = (CBORMetadata) transaction.getAuxiliaryData().getMetadata();

        assertThat(actualMetadata.get(BigInteger.valueOf(1000))).isEqualTo("hello");
        assertThat(actualMetadata.get(BigInteger.valueOf(1001))).isEqualTo("value1");
    }
}

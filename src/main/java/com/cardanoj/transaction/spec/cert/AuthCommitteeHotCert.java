package com.cardanoj.transaction.spec.cert;

import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.UnsignedInteger;
import com.cardanoj.address.Credential;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.transaction.util.CredentialSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthCommitteeHotCert implements Certificate {
    private final CertificateType type = CertificateType.AUTH_COMMITTEE_HOT_CERT;

    private Credential committeeColdCredential;
    private Credential committeeHotCredential;

    @Override
    public Array serialize() throws CborSerializationException {
        Objects.requireNonNull(committeeColdCredential);
        Objects.requireNonNull(committeeHotCredential);

        Array certArray = new Array();
        certArray.add(new UnsignedInteger(type.getValue()));
        certArray.add(CredentialSerializer.serialize(committeeColdCredential));
        certArray.add(CredentialSerializer.serialize(committeeHotCredential));

        return certArray;
    }

    public static AuthCommitteeHotCert deserialize(Array certArray) {
        List<DataItem> dataItemList = certArray.getDataItems();

        Credential committeeColdCred = CredentialSerializer.deserialize((Array) dataItemList.get(1));
        Credential committeeHotCred = CredentialSerializer.deserialize((Array) dataItemList.get(2));

        return new AuthCommitteeHotCert(committeeColdCred, committeeHotCred);
    }
}

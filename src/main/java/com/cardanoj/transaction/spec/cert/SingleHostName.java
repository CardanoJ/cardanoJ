package com.cardanoj.transaction.spec.cert;

import co.nstant.in.cbor.model.Array;
import co.nstant.in.cbor.model.SimpleValue;
import co.nstant.in.cbor.model.UnicodeString;
import co.nstant.in.cbor.model.UnsignedInteger;
import com.cardanoj.exception.CborSerializationException;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class SingleHostName implements Relay {
    private int port;
    private String dnsName;

    public Array serialize() throws CborSerializationException {
        Array array = new Array();
        array.add(new UnsignedInteger(1));

        if (port != 0)
            array.add(new UnsignedInteger(port));
        else
            array.add(SimpleValue.NULL);

        if (dnsName != null && !dnsName.isEmpty())
            array.add(new UnicodeString(dnsName));
        else
            throw new CborSerializationException("Serialization failed. DNS name can't be null for SingleHostName relay");

        return array;
    }
}

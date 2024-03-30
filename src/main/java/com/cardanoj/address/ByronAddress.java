package com.cardanoj.address;

import com.cardanoj.crypto.Base58;
import com.cardanoj.crypto.exception.AddressFormatException;

import static com.cardanoj.address.util.AddressEncoderDecoderUtil.readAddressType;

public class ByronAddress {
    private final byte[] bytes;
    private final String address;

    public ByronAddress(String address) {
        this.bytes = Base58.decode(address);
        AddressType addressType = readAddressType(this.bytes);

        if (!AddressType.Byron.equals(addressType)) {
            throw new AddressFormatException("Invalid Byron address");
        }

        this.address = address;
    }

    public ByronAddress(byte[] bytes) {
        this.bytes = bytes;
        this.address = Base58.encode(bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String toBase58() {
        return address;
    }

    public String getAddress() {
        return address;
    }
}

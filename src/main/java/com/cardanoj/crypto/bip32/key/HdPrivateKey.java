package com.cardanoj.crypto.bip32.key;

import com.cardanoj.crypto.Bech32;

//This file is originally from https://github.com/semuxproject/semux-core
public class HdPrivateKey extends HdKey {

    public String toBech32() {
        return Bech32.encode(getBytes(), "xprv");
    }
}

package com.cardanoj.rust.jna.util;

public class NetworksJNA {
    public static NetworkJNA.ByReference mainnet() {
        NetworkJNA.ByReference mainnet = new NetworkJNA.ByReference();
        mainnet.network_id = 0b0001;
        mainnet.protocol_magic = 764824073;

        return mainnet;
    }

    public static NetworkJNA.ByReference testnet() {
        NetworkJNA.ByReference testnet = new NetworkJNA.ByReference();
        testnet.network_id = 0b0000;
        testnet.protocol_magic = 1097911063;

        return testnet;
    }
}

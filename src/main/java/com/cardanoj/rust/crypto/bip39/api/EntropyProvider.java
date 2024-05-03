package com.cardanoj.rust.crypto.bip39.api;

public interface EntropyProvider {

    /**
     * Generate random entropy
     * @param length
     * @return
     */
    byte[] generateRandom(int length);

}

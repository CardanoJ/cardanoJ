package com.cardanoj.rust.crypto.config;

import com.cardanoj.rust.crypto.api.SigningProvider;
import com.cardanoj.rust.crypto.api.impl.EdDSASigningProvider;
import com.cardanoj.rust.crypto.bip39.DefaultEntropyProviderImpl;
import com.cardanoj.rust.crypto.bip39.api.EntropyProvider;

public enum CryptoConfiguration {
    INSTANCE();

    private SigningProvider signingProvider;
    private EntropyProvider entropyProvider;

    CryptoConfiguration() {
        signingProvider = new EdDSASigningProvider();
        entropyProvider = new DefaultEntropyProviderImpl();
    }

    public SigningProvider getSigningProvider() {
        return signingProvider;
    }

    public void setSigningProvider(SigningProvider signingProvider) {
        this.signingProvider = signingProvider;
    }

    public EntropyProvider getEntropyProvider() {
        return entropyProvider;
    }

    public void setEntropyProvider(EntropyProvider entropyProvider) {
        this.entropyProvider = entropyProvider;
    }

}

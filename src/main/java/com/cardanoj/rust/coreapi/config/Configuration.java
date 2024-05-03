package com.cardanoj.rust.coreapi.config;

import com.cardanoj.rust.coinselection.config.CoinselectionConfig;
import com.cardanoj.rust.crypto.api.SigningProvider;
import com.cardanoj.rust.crypto.bip39.api.EntropyProvider;
import com.cardanoj.rust.crypto.config.CryptoConfiguration;
import com.cardanoj.rust.plutus.api.PlutusObjectConverter;
import com.cardanoj.rust.plutus.impl.DefaultPlutusObjectConverter;
import com.cardanoj.rust.util.OSUtil;

public enum Configuration {
    INSTANCE();

    private PlutusObjectConverter plutusObjectConverter;

    Configuration() {
        plutusObjectConverter = new DefaultPlutusObjectConverter();
    }

    public SigningProvider getSigningProvider() {
        return CryptoConfiguration.INSTANCE.getSigningProvider();
    }

    public void setSigningProvider(SigningProvider signingProvider) {
        CryptoConfiguration.INSTANCE.setSigningProvider(signingProvider);
    }

    public EntropyProvider getEntropyProvider() {
        return CryptoConfiguration.INSTANCE.getEntropyProvider();
    }

    public void setEntropyProvider(EntropyProvider entropyProvider) {
        CryptoConfiguration.INSTANCE.setEntropyProvider(entropyProvider);
    }

    public PlutusObjectConverter getPlutusObjectConverter() {
        return plutusObjectConverter;
    }

    public void setPlutusObjectConverter(PlutusObjectConverter plutusObjectConverter) {
        this.plutusObjectConverter = plutusObjectConverter;
    }

    public int getCoinSelectionLimit() {
        return CoinselectionConfig.INSTANCE.getCoinSelectionLimit();
    }

    public void setCoinSelectionLimit(int coinSelectionLimit) {
        CoinselectionConfig.INSTANCE.setCoinSelectionLimit(coinSelectionLimit);
    }

    public boolean isAndroid() {
        return OSUtil.isAndroid();
    }

    public void setAndroid(boolean android) {
        OSUtil.setAndroid(android);
    }
}

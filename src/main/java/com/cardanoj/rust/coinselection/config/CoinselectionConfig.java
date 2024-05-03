package com.cardanoj.rust.coinselection.config;

public enum CoinselectionConfig {
    INSTANCE;

    private int coinSelectionLimit = 20;

    public int getCoinSelectionLimit() {
        return coinSelectionLimit;
    }

    public void setCoinSelectionLimit(int coinSelectionLimit) {
        this.coinSelectionLimit = coinSelectionLimit;
    }
}

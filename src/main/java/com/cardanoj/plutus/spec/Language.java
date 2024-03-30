package com.cardanoj.plutus.spec;

public enum Language {
    PLUTUS_V1(0), PLUTUS_V2(1);

    private final int key;

    Language(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

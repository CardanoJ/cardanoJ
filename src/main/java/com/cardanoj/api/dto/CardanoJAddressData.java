package com.cardanoj.api.dto;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * This class represents the address data for Cardano.
 */
public class CardanoJAddressData {
    
    private JsonNode vkey;
    private JsonNode skey;
    private String address;

    /**
     * Default constructor.
     */
    public CardanoJAddressData() {
    }

    /**
     * Parameterized constructor to initialize address data.
     *
     * @param vkey the verification key
     * @param skey the signing key
     * @param address the address
     */
    public CardanoJAddressData(JsonNode vkey, JsonNode skey, String address) {
        this.vkey = vkey;
        this.skey = skey;
        this.address = address;
    }

    /**
     * Gets the address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Gets the verification key.
     *
     * @return the verification key
     */
    public JsonNode getVkey() {
        return vkey;
    }

    /**
     * Gets the signing key.
     *
     * @return the signing key
     */
    public JsonNode getSkey() {
        return skey;
    }

    /**
     * Sets the verification key.
     *
     * @param vkey the verification key
     */
    public void setVkey(JsonNode vkey) {
        this.vkey = vkey;
    }

    /**
     * Sets the signing key.
     *
     * @param skey the signing key
     */
    public void setSkey(JsonNode skey) {
        this.skey = skey;
    }

    /**
     * Sets the address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}

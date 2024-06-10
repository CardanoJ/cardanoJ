package com.cardanoj.api.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class CardanoJAddressData {
	private JsonNode vkey;
    private JsonNode skey;
    private String address;

    public CardanoJAddressData() {
    }

    public CardanoJAddressData(JsonNode vkey, JsonNode skey, String address) {
        this.vkey = vkey;
        this.skey = skey;
        this.address = address;
    }

    // Getters and setters
    
    public String getAddress() {
        return address;
    }

    public JsonNode getVkey() {
		return vkey;
	}

	public JsonNode getSkey() {
		return skey;
	}
	

	public void setVkey(JsonNode vkey) {
		this.vkey = vkey;
	}

	public void setSkey(JsonNode skey) {
		this.skey = skey;
	}

	public void setAddress(String address) {
        this.address = address;
    }


		
		
}

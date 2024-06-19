package com.cardanoj.api.dto;

import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data class representing a Cardanoj address, including verification and signing keys.")
public class CardanojAddressData {
	private JsonNode vkey;
    private JsonNode skey;
    @Schema(description = "Address", example = "addr_test1...")
    private String address;

    public CardanojAddressData() {
    }

    public CardanojAddressData(JsonNode vkey, JsonNode skey, String address) {
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

package com.cardanoj.api.util;

import org.springframework.stereotype.Component;

import io.swagger.v3.oas.annotations.media.Schema;

@Component
@Schema(description = "Details of a Cardano transaction")
public class TransactionDetails {
    @Schema(description = "Transaction hash", example = "083d4de03de1b098d477ad7c2b3826b31705378423567e40831efc3a7f174799")
    private String txHash;
    @Schema(description = "Transaction index", example = "1")
    private int txID;
    @Schema(description = "Amount of transaction", example = "1000000 lovelace")
    private String amount;
    @Schema(description = "Additional information of the transaction", example = "TxOutDatumNone")
    private String additionalInfo;

    public TransactionDetails() {
    }

    public TransactionDetails(String txHash, int txID, String amount, String additionalInfo) {
        this.txHash = txHash;
        this.txID = txID;
        this.amount = amount;
        this.additionalInfo = additionalInfo;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public int getTxID() {
        return txID;
    }

    public void setTxID(int txID) {
        this.txID = txID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

	@Override
	public String toString() {
		return "TransactionDetails [txHash=" + txHash + ", txID=" + txID + ", amount=" + amount + ", getTxHash()="
				+ getTxHash() + ", getTxID()=" + getTxID() + ", getAmount()=" + getAmount() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", additionalInfo=" + additionalInfo + "]";
	}

    public String toJSON() {
        StringBuilder jsonBuilder = new StringBuilder("{");
        jsonBuilder.append("\"txHash\": \"").append(txHash).append("\",");
        jsonBuilder.append("\"txID\": ").append(txID).append(",");
        jsonBuilder.append("\"amount\": \"").append(amount).append("\",");
        jsonBuilder.append("\"additionalInfo\": \"").append(additionalInfo).append("\"");
        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
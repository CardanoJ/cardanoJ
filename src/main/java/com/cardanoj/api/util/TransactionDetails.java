package com.cardanoj.api.util;

import org.springframework.stereotype.Component;

@Component
public class TransactionDetails {
    private String txHash;
    private int txIx;
    private String amount;
    private String additionalInfo;

    public TransactionDetails() {
    }

    public TransactionDetails(String txHash, int txIx, String amount, String additionalInfo) {
        this.txHash = txHash;
        this.txIx = txIx;
        this.amount = amount;
        this.additionalInfo = additionalInfo;
    }
    

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public int getTxIx() {
        return txIx;
    }

    public void setTxIx(int txIx) {
        this.txIx = txIx;
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
		return "TransactionDetails [txHash=" + txHash + ", txIx=" + txIx + ", amount=" + amount + ", getTxHash()="
				+ getTxHash() + ", getTxIx()=" + getTxIx() + ", getAmount()=" + getAmount() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", additionalInfo=" + additionalInfo + "]";
	}

    public String toJSON() {
        return String.format(
            "{\"txHash\": \"%s\", \"txIx\": %d, \"amount\": \"%s\", \"additionalInfo\": \"%s\"}",
            txHash, txIx, amount.replace("\"", "\\\""), additionalInfo.replace("\"", "\\\"")
        );
    }
    
}

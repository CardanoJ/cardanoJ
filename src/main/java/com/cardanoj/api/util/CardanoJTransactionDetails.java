package com.cardanoj.api.util;

import org.springframework.stereotype.Component;

/**
 * This class represents the details of a Cardano transaction.
 */
@Component
public class CardanoJTransactionDetails {

    private String txHash;
    private int txIx;
    private String amount;
    private String additionalInfo;

    /**
     * Default constructor.
     */
    public CardanoJTransactionDetails() {
    }

    /**
     * Parameterized constructor to initialize transaction details.
     *
     * @param txHash the transaction hash
     * @param txIx the transaction index
     * @param amount the amount
     * @param additionalInfo additional information
     */
    public CardanoJTransactionDetails(String txHash, int txIx, String amount, String additionalInfo) {
        this.txHash = txHash;
        this.txIx = txIx;
        this.amount = amount;
        this.additionalInfo = additionalInfo;
    }

    /**
     * Gets the transaction hash.
     *
     * @return the transaction hash
     */
    public String getTxHash() {
        return txHash;
    }

    /**
     * Sets the transaction hash.
     *
     * @param txHash the transaction hash
     */
    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    /**
     * Gets the transaction index.
     *
     * @return the transaction index
     */
    public int getTxIx() {
        return txIx;
    }

    /**
     * Sets the transaction index.
     *
     * @param txIx the transaction index
     */
    public void setTxIx(int txIx) {
        this.txIx = txIx;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     *
     * @param amount the amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Gets additional information.
     *
     * @return additional information
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets additional information.
     *
     * @param additionalInfo additional information
     */
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    /**
     * Provides a string representation of the transaction details.
     *
     * @return a string representation of the transaction details
     */
    @Override
    public String toString() {
        return "TransactionDetails [txHash=" + txHash + ", txIx=" + txIx + ", amount=" + amount + ", getTxHash()="
                + getTxHash() + ", getTxIx()=" + getTxIx() + ", getAmount()=" + getAmount() + ", getClass()="
                + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", additionalInfo=" + additionalInfo + "]";
    }

    /**
     * Converts the transaction details to JSON format.
     *
     * @return the JSON representation of the transaction details
     */
    public String toJSON() {
        return String.format(
            "{\"txHash\": \"%s\", \"txIx\": %d, \"amount\": \"%s\", \"additionalInfo\": \"%s\"}",
            txHash, txIx, amount.replace("\"", "\\\""), additionalInfo.replace("\"", "\\\"")
        );
    }
}

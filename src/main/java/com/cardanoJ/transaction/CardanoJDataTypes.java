package com.cardanoJ.transaction;

public class CardanoJDataTypes {
    private Integer sender;
    private Integer receiver;
    private Double amount;
    private String transactionId;

    public CardanoJDataTypes(Integer sender, Integer receiver, Double amount, String transactionId) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.transactionId = transactionId;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

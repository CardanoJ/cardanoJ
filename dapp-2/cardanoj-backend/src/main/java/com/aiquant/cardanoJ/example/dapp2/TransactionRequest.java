package com.aiquant.cardanoJ.example.dapp2;

public class TransactionRequest {
    private String resourcePath;
    private String senderAddress;
    private String receiverAddress;
    private String senderName;
    private String network;
    private String networkId;
    private Long lovelaceAmount;

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public Long getLovelaceAmount() {
        return lovelaceAmount;
    }

    public void setLovelaceAmount(Long lovelaceAmount) {
        this.lovelaceAmount = lovelaceAmount;
    }
}

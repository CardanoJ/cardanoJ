package com.cardanoj.api.dto;

/**
 * Data Transfer Object (DTO) for building a Cardano transaction request.
 * <p>
 * This class is used to encapsulate all the details required to build a Cardano transaction, including
 * certificate files, script files, transaction hashes, and other parameters.
 * </p>
 */
public class CardanoJBuildTransactionRequest {

    private String regCert;
    private String delCert;
    private String scriptFile;
    private String redeemerFile;
    private String protocolparam;
    private String address;
    private String txHash;
    private String txId;
    private String collateralTxHash;
    private String collateralTxId;

    /**
     * Gets the registration certificate.
     * 
     * @return The registration certificate.
     */
    public String getRegCert() {
        return regCert;
    }

    /**
     * Sets the registration certificate.
     * 
     * @param regCert The registration certificate.
     */
    public void setRegCert(String regCert) {
        this.regCert = regCert;
    }

    /**
     * Gets the delegation certificate.
     * 
     * @return The delegation certificate.
     */
    public String getDelCert() {
        return delCert;
    }

    /**
     * Sets the delegation certificate.
     * 
     * @param delCert The delegation certificate.
     */
    public void setDelCert(String delCert) {
        this.delCert = delCert;
    }

    /**
     * Gets the script file.
     * 
     * @return The script file.
     */
    public String getScriptFile() {
        return scriptFile;
    }

    /**
     * Sets the script file.
     * 
     * @param scriptFile The script file.
     */
    public void setScriptFile(String scriptFile) {
        this.scriptFile = scriptFile;
    }

    /**
     * Gets the redeemer file.
     * 
     * @return The redeemer file.
     */
    public String getRedeemerFile() {
        return redeemerFile;
    }

    /**
     * Sets the redeemer file.
     * 
     * @param redeemerFile The redeemer file.
     */
    public void setRedeemerFile(String redeemerFile) {
        this.redeemerFile = redeemerFile;
    }

    /**
     * Gets the protocol parameters.
     * 
     * @return The protocol parameters.
     */
    public String getProtocolparam() {
        return protocolparam;
    }

    /**
     * Sets the protocol parameters.
     * 
     * @param protocolparam The protocol parameters.
     */
    public void setProtocolparam(String protocolparam) {
        this.protocolparam = protocolparam;
    }

    /**
     * Gets the address.
     * 
     * @return The address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     * 
     * @param address The address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the transaction hash.
     * 
     * @return The transaction hash.
     */
    public String getTxHash() {
        return txHash;
    }

    /**
     * Sets the transaction hash.
     * 
     * @param txHash The transaction hash.
     */
    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    /**
     * Gets the transaction ID.
     * 
     * @return The transaction ID.
     */
    public String getTxId() {
        return txId;
    }

    /**
     * Sets the transaction ID.
     * 
     * @param txId The transaction ID.
     */
    public void setTxId(String txId) {
        this.txId = txId;
    }

    /**
     * Gets the collateral transaction hash.
     * 
     * @return The collateral transaction hash.
     */
    public String getCollateralTxHash() {
        return collateralTxHash;
    }

    /**
     * Sets the collateral transaction hash.
     * 
     * @param collateralTxHash The collateral transaction hash.
     */
    public void setCollateralTxHash(String collateralTxHash) {
        this.collateralTxHash = collateralTxHash;
    }

    /**
     * Gets the collateral transaction ID.
     * 
     * @return The collateral transaction ID.
     */
    public String getCollateralTxId() {
        return collateralTxId;
    }

    /**
     * Sets the collateral transaction ID.
     * 
     * @param collateralTxId The collateral transaction ID.
     */
    public void setCollateralTxId(String collateralTxId) {
        this.collateralTxId = collateralTxId;
    }
}

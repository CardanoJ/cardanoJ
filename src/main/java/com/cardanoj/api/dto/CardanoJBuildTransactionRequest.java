package com.cardanoj.api.dto;

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
        public String getRegCert() {
            return regCert;
        }
        public void setRegCert(String regCert) {
            this.regCert = regCert;
        }
        public String getDelCert() {
            return delCert;
        }
        public void setDelCert(String delCert) {
            this.delCert = delCert;
        }
        public String getScriptFile() {
            return scriptFile;
        }
        public void setScriptFile(String scriptFile) {
            this.scriptFile = scriptFile;
        }
        public String getRedeemerFile() {
            return redeemerFile;
        }
        public void setRedeemerFile(String redeemerFile) {
            this.redeemerFile = redeemerFile;
        }
        public String getProtocolparam() {
            return protocolparam;
        }
        public void setProtocolparam(String protocolparam) {
            this.protocolparam = protocolparam;
        }
        public String getAddress() {
            return address;
        }
        public void setAddress(String address) {
            this.address = address;
        }
        public String getTxHash() {
            return txHash;
        }
        public void setTxHash(String txHash) {
            this.txHash = txHash;
        }
        public String getTxId() {
            return txId;
        }
        public void setTxId(String txId) {
            this.txId = txId;
        }
        public String getCollateralTxHash() {
            return collateralTxHash;
        }
        public void setCollateralTxHash(String collateralTxHash) {
            this.collateralTxHash = collateralTxHash;
        }
        public String getCollateralTxId() {
            return collateralTxId;
        }
        public void setCollateralTxId(String collateralTxId) {
            this.collateralTxId = collateralTxId;
        }

        
    
}

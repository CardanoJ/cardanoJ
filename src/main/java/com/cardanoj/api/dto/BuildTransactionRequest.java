package com.cardanoj.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request class for building a transaction on the Cardano network.")
public class BuildTransactionRequest {

        @Schema(description = "Registration certificate in JSON format", example = "{\"type\": \"registration\", \"content\": \"...\"}")
        private String regCert;

        @Schema(description = "Delegation certificate in JSON format", example = "{\"type\": \"delegation\", \"content\": \"...\"}")
        private String delCert;

        @Schema(description = "Path to the script file", example = "/path/to/script.plutus")
        private String scriptFile;

        @Schema(description = "Path to the redeemer file", example = "/path/to/redeemer.json")
        private String redeemerFile;

        @Schema(description = "Protocol parameters in JSON format", example = "{\n" + //
                        "    \"collateralPercentage\": 150,\n" + //
                        "    \"costModels\": {\n" + //
                        "        \"PlutusV1\": [\n" + //
                        "            205665,...")
        private String protocolparam;

        @Schema(description = "Address involved in the transaction", example = "addr_test1...")
        private String address;

        @Schema(description = "Transaction hash", example = "083d4...")
        private String txHash;

        @Schema(description = "Transaction ID", example = "1")
        private String txId;

        @Schema(description = "Collateral transaction hash", example = "083d4...")
        private String collateralTxHash;

        @Schema(description = "Collateral transaction ID", example = "1")
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

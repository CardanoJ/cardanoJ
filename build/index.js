"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.getAddressInfo = getAddressInfo;
exports.getUTXO = getUTXO;
exports.buildTransaction = buildTransaction;
exports.signTransaction = signTransaction;
exports.submitTransaction = submitTransaction;
exports.getDatumHash = getDatumHash;
exports.getScriptAddress = getScriptAddress;
exports.getTransactionDetails = getTransactionDetails;
exports.getProtocolParams = getProtocolParams;
exports.buildStakeAddress = buildStakeAddress;
exports.getStakePoolInfo = getStakePoolInfo;
const axios_1 = __importDefault(require("axios"));
// Function to get address information
function getAddressInfo() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/address`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error retrieving address info:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
// Function to get UTXO from an address
function getUTXO(address) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/queryutxo/${address}`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error retrieving UTXO:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
// Function to build a transaction
function buildTransaction(senderAddress, receiverAddress, lovelace, transactionHash, transactionId) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/build/${senderAddress}/${receiverAddress}/${lovelace}/${transactionHash}/${transactionId}`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error building transaction:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
// Function to sign a transaction
function signTransaction(signKey, txbody) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/sign?signKey=${encodeURIComponent(signKey)}&txbody=${encodeURIComponent(txbody)}`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error signing transaction:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
function submitTransaction(tx) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/submit?tx=${encodeURIComponent(tx)}`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error submitting transaction:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
// Function to get datum information
function getDatumHash(datumValue) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/datum?datumValue=${datumValue}`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error fetching datum hash:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
// Function to get script information
function getScriptAddress(cbor) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/script?cbor=${encodeURIComponent(cbor)}`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error fetching script address:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
function getTransactionDetails(senderAddress, receiverAddress, lovelace, txHash, txID, datumValue) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/script/${senderAddress}/${receiverAddress}/${lovelace}/${txHash}/${txID}/${datumValue}`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error fetching transaction details:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
function getProtocolParams() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = 'http://localhost:8080/api/protocolparam';
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error fetching protocol parameters:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
// Function to build a stake address
function buildStakeAddress(paymentKey, script) {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/stakebuild`;
            const params = { paymentKey, script };
            const response = yield axios_1.default.get(url, {
                params
            });
            return response.data;
        }
        catch (error) {
            console.error('Error building stake address:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}
// Function to get stake pool information
function getStakePoolInfo() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const url = `http://localhost:8080/api/stakepool`;
            const response = yield axios_1.default.get(url);
            return response.data;
        }
        catch (error) {
            console.error('Error retrieving stake pool information:', error instanceof Error ? error.message : String(error));
            throw error;
        }
    });
}

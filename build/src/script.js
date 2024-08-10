#!/usr/bin/env node

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
Object.defineProperty(exports, "__esModule", { value: true });
const commander_1 = require("commander");
const index_1 = require("./index"); // Adjust this path if needed
const program = new commander_1.Command();
program
    .name('cardanoj')
    .description('CLI for Cardano API interactions')
    .version('1.0.0');
// Command to get script address
program
    .command('get-script-address')
    .description('Get script address from CBOR')
    .argument('<cbor>', 'CBOR data to query script address')
    .action((cbor) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const scriptAddress = yield (0, index_1.getScriptAddress)("%7b%20%20%20%0a%20%20%20%20%22type%22%3a%20%22PlutusScriptV1%22%2c%0a%20%20%20%20%22description%22%3a%20%22%22%2c%0a%20%20%20%20%22cborHex%22%3a%20%224e4d01000033222220051200120011%22%0a%7d%0a");
        console.log('Retrieved Script Address Info:', scriptAddress);
    }
    catch (error) {
        console.error('Error retrieving script address:', error instanceof Error ? error.message : 'Unknown error');
    }
}));
// Command to get script transaction details
program
    .command('get-script-transaction-details')
    .description('Get transaction details for a script')
    .option('-s, --sender-address <address>', 'Sender address', 'addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2')
    .option('-r, --receiver-address <address>', 'Receiver address', 'addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8')
    .option('-l, --lovelace <amount>', 'Amount in lovelace', parseInt, 1000000)
    .option('-h, --tx-hash <hash>', 'Transaction hash', '100e0bd6f28708c6acff22da143f9229c8fb03fa8c2407246f44759fc8a39dd5')
    .option('-i, --tx-id <id>', 'Transaction ID', parseInt, 1)
    .option('-d, --datum-value <datum>', 'Datum value', '1')
    .action((options) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { senderAddress, receiverAddress, lovelace, txHash, txID, datumValue } = options;
        const scriptTransactionDetails = yield (0, index_1.getScriptTransactionDetails)(senderAddress, receiverAddress, lovelace, txHash, txID, datumValue);
        console.log('Script Transaction Info:', scriptTransactionDetails);
    }
    catch (error) {
        console.error('Error retrieving script transaction details:', error instanceof Error ? error.message : 'Unknown error');
    }
}));
program.parse(process.argv);

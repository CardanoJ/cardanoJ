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
// Command to get address information
program
    .command('get-address-info')
    .description('Get address information')
    .action(() => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const addressInfo = yield (0, index_1.getAddressInfo)();
        console.log(addressInfo);
    }
    catch (error) {
        console.error('Error:', error instanceof Error ? error.message : 'Unknown error');
    }
}));
// Command to get UTXO from an address
program
    .command('get-utxo')
    .description('Get UTXO from an address')
    .argument('<address>', 'Address to query UTXO')
    .action((address) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const utxos = yield (0, index_1.getUTXO)(address);
        console.log(utxos);
    }
    catch (error) {
        console.error('Error:', error instanceof Error ? error.message : 'Unknown error');
    }
}));
// Command to build a transaction
program
    .command('build-transaction')
    .description('Build a transaction')
    .option('-s, --sender-address <address>', 'Sender address', 'addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2')
    .option('-r, --receiver-address <address>', 'Receiver address', 'addr_test1vp44nmn5a9klmn6eglea9mhjxphlryqe9cvlu43deh7tp2sj03e9g')
    .option('-l, --lovelace <amount>', 'Amount in lovelace', parseInt, 1000000)
    .option('-h, --tx-hash <hash>', 'Transaction hash', '88296585d4a415f36554442d7ff445b7b0f821a4d6f01b3437e7c67b5f6e3543')
    .option('-i, --tx-id <id>', 'Transaction ID', parseInt, 0)
    .action((options) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { senderAddress, receiverAddress, lovelace, txHash, txID } = options;
        const transaction = yield (0, index_1.buildTransaction)(senderAddress, receiverAddress, lovelace, txHash, txID);
        console.log('Transaction built:', transaction);
    }
    catch (error) {
        console.error('Error building transaction:', error instanceof Error ? error.message : 'Unknown error');
    }
}));
// Command to sign a transaction
program
    .command('sign-transaction')
    .description('Sign a transaction')
    .argument('<tx>', 'Transaction to sign')
    .option('-k, --signing-key <key>', 'Signing key')
    .action((tx, options) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const { signingKey } = options;
        const signedTransaction = yield (0, index_1.signTransaction)(tx, signingKey);
        console.log('Transaction signed:', signedTransaction);
    }
    catch (error) {
        console.error('Error signing transaction:', error instanceof Error ? error.message : 'Unknown error');
    }
}));
// Command to submit a transaction
program
    .command('submit-transaction')
    .description('Submit a transaction')
    .argument('<tx>', 'Signed transaction to submit')
    .action((tx) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const result = yield (0, index_1.submitTransaction)(tx);
        console.log('Transaction submitted:', result);
    }
    catch (error) {
        console.error('Error submitting transaction:', error instanceof Error ? error.message : 'Unknown error');
    }
}));
program.parse(process.argv);

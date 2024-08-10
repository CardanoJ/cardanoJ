import { Command } from 'commander';
import { getAddressInfo, getUTXO, buildTransaction, signTransaction, submitTransaction } from './index'; // Adjust this path if needed

const program = new Command();

program
  .name('cardanoj')
  .description('CLI for Cardano API interactions')
  .version('1.0.0');

// Command to get address information
program
  .command('get-address-info')
  .description('Get address information')
  .action(async () => {
    try {
      const addressInfo = await getAddressInfo();
      console.log(addressInfo);
    } catch (error) {
      console.error('Error:', error instanceof Error ? error.message : 'Unknown error');
    }
  });

// Command to get UTXO from an address
program
  .command('get-utxo')
  .description('Get UTXO from an address')
  .argument('<address>', 'Address to query UTXO')
  .action(async (address) => {
    try {
      const utxos = await getUTXO(address);
      console.log(utxos);
    } catch (error) {
      console.error('Error:', error instanceof Error ? error.message : 'Unknown error');
    }
  });

// Command to build a transaction
program
  .command('build-transaction')
  .description('Build a transaction')
  .option('-s, --sender-address <address>', 'Sender address', 'addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2')
  .option('-r, --receiver-address <address>', 'Receiver address', 'addr_test1vp44nmn5a9klmn6eglea9mhjxphlryqe9cvlu43deh7tp2sj03e9g')
  .option('-l, --lovelace <amount>', 'Amount in lovelace', parseInt, 1000000)
  .option('-h, --tx-hash <hash>', 'Transaction hash', '88296585d4a415f36554442d7ff445b7b0f821a4d6f01b3437e7c67b5f6e3543')
  .option('-i, --tx-id <id>', 'Transaction ID', parseInt, 0)
  .action(async (options) => {
    try {
      const { senderAddress, receiverAddress, lovelace, txHash, txID } = options;
      const transaction = await buildTransaction(senderAddress, receiverAddress, lovelace, txHash, txID);
      console.log('Transaction built:', transaction);
    } catch (error) {
      console.error('Error building transaction:', error instanceof Error ? error.message : 'Unknown error');
    }
  });


// Command to sign a transaction
program
  .command('sign-transaction')
  .description('Sign a transaction')
  .argument('<tx>', 'Transaction to sign')
  .option('-k, --signing-key <key>', 'Signing key')
  .action(async (tx, options) => {
    try {
      const { signingKey } = options;
      const signedTransaction = await signTransaction(tx, signingKey);
      console.log('Transaction signed:', signedTransaction);
    } catch (error) {
      console.error('Error signing transaction:', error instanceof Error ? error.message : 'Unknown error');
    }
  });

// Command to submit a transaction
program
  .command('submit-transaction')
  .description('Submit a transaction')
  .argument('<tx>', 'Signed transaction to submit')
  .action(async (tx) => {
    try {
      const result = await submitTransaction(tx);
      console.log('Transaction submitted:', result);
    } catch (error) {
      console.error('Error submitting transaction:', error instanceof Error ? error.message : 'Unknown error');
    }
  });

program.parse(process.argv);

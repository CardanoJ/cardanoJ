#!/usr/bin/env node

const { execSync } = require('child_process');
const path = require('path');
const readline = require('readline');
const fs = require('fs-extra');

const dappPath = path.resolve(process.cwd(), 'cardanoj-dapp');
const port = 3000; // Change this if your application uses a different port

// Check if cardanoj-dapp directory exists
if (!fs.existsSync(dappPath)) {
    console.error(`CardanoJ Dapp directory not found: ${dappPath}`);
    process.exit(1);
}

// Navigate into the cardanoj-dapp directory
process.chdir(dappPath);

// Build the dApp
console.log('Building the dApp...');
execSync('npm run build', { stdio: 'inherit' });

// Install serve to serve the build folder
console.log('Installing serve...');
execSync('npm install -g serve', { stdio: 'inherit' });

// Start serving the build folder
console.log('Starting the server...');
const serveProcess = execSync(`serve -s build -l ${port}`, { stdio: 'inherit', detached: true });
const servePid = serveProcess.pid;

// Prompt user to open the webpage
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

rl.question(`The dApp is being served at http://localhost:${port}. Would you like to open it in your browser now? (yes/no): `, async (openAnswer) => {
    if (openAnswer.toLowerCase() === 'yes') {
        // Open the webpage in the default browser
        const { default: open } = await import('open');
        await open(`http://localhost:${port}`);
    }

    // Prompt user to run tests
    rl.question('Would you like to run tests now? (yes/no): ', (testAnswer) => {
        if (testAnswer.toLowerCase() === 'yes') {
            // Run tests
            console.log('Running tests...');
            execSync('npm test', { stdio: 'inherit' });
        }

        // Clean up: kill the server
        process.kill(servePid);
        console.log('Done.');
        rl.close();
    });
});

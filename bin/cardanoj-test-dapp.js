#!/usr/bin/env node

const { execSync } = require('child_process');
const path = require('path');
const fs = require('fs-extra');

const dappPath = path.join(__dirname, '../cardanoj-dapp');

// Check if cardanoj-dapp path exists
if (!fs.existsSync(dappPath)) {
    console.error(`CardanoJ Dapp directory not found: ${dappPath}`);
    process.exit(1);
}

// Navigate into the cardanoj-dapp directory
process.chdir(dappPath);

// Start the development server
console.log('Starting the development server...');
const startProcess = execSync('npm start', { stdio: 'inherit', detached: true });
const startPid = startProcess.pid;

// Wait for a few seconds to ensure the server has started
setTimeout(async () => {
    // Dynamically import the 'open' package
    const { default: open } = await import('open');
    
    // Open the webpage in the default browser
    console.log('Opening the browser...');
    open('http://localhost:3000'); // Adjust the port if your app uses a different one

    // Wait for a few seconds to ensure the webpage is loaded
    setTimeout(() => {
        // Run tests (assuming there's a test script defined in package.json)
        console.log('Running tests...');
        execSync('npm test', { stdio: 'inherit' });

        // Clean up: kill the server
        process.kill(startPid);
        console.log('Tests completed successfully.');

    }, 10000); // Adjust timeout as needed to ensure the server has started
}, 5000); // Wait time before opening the browser

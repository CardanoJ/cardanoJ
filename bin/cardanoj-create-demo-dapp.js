#!/usr/bin/env node

const { execSync } = require('child_process');
const path = require('path');
const fs = require('fs-extra');

const projectName = process.argv[2];
const projectPath = path.resolve(process.cwd(), projectName);
const templatePath = path.join(__dirname, '../cardanoj-dapp');

// Check if the project name is provided
if (!projectName) {
    console.error('Please provide a name for your demo dApp project.');
    console.error('Example:');
    console.error('    cardanoj-create-demo-dapp my-demo-dapp');
    process.exit(1);
}

// Copy template files from cardanoj-dapp
fs.copySync(templatePath, projectPath);

// Navigate into the project directory
process.chdir(projectPath);

// Install dependencies
console.log('Installing dependencies...');
execSync('npm install', { stdio: 'inherit' });

// Build the project
console.log('Building the project...');
execSync('npm run build', { stdio: 'inherit' });

// Test the project
console.log('Running tests...');
execSync('npm test', { stdio: 'inherit' });

// Start the development server
console.log('Starting the development server...');
execSync('npm start', { stdio: 'inherit' });

// Open the browser
console.log('Opening the browser...');
execSync('xdg-open http://localhost:3000 || open http://localhost:3000', { stdio: 'inherit' });

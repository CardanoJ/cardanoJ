import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api';

// Define an interface for the address data structure
interface Address {
    vkey: {
        type: string;
        description: string;
        cborHex: string;
    };
    skey: {
        type: string;
        description: string;
        cborHex: string;
    };
    address: string;
}

// Function to get address information
export async function getAddressInfo(): Promise<Address> {
    try {
        const response = await axios.get<Address>(`${BASE_URL}/address`);
        return response.data;
    } catch (error) {
        console.error('Error retrieving address info:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the UTXO data structure
interface UTXO {
    txHash: string;
    txID: number;
    amount: string;  // Updated to string based on response
    additionalInfo: string;  // Additional field from response
}

// Function to get UTXO from an address
export async function getUTXO(address: string): Promise<UTXO[]> {
    try {
        const response = await axios.get<UTXO[]>(`${BASE_URL}/queryutxo/${address}`);
        return response.data;
    } catch (error) {
        console.error('Error retrieving UTXO:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the build transaction response
interface BuildTransactionResponse {
    type: string;
    description: string;
    cborHex: string;
}

// Function to build a transaction
export async function buildTransaction(
    senderAddress: string,
    receiverAddress: string,
    lovelace: number,
    transactionHash: string,
    transactionId: number
): Promise<BuildTransactionResponse> {
    try {
        const response = await axios.get<BuildTransactionResponse>(`${BASE_URL}/build/${senderAddress}/${receiverAddress}/${lovelace}/${transactionHash}/${transactionId}`);
        return response.data;
    } catch (error) {
        console.error('Error building transaction:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the sign transaction response
interface SignTransactionResponse {
    type: string;
    description: string;
    cborHex: string;
}

// Function to sign a transaction
export async function signTransaction(
    signKey: string,
    txbody: string
): Promise<SignTransactionResponse> {
    try {
        const response = await axios.get<SignTransactionResponse>(`${BASE_URL}/sign`, {
            params: { signKey, txbody }
        });
        return response.data;
    } catch (error) {
        console.error('Error signing transaction:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the submit transaction response
interface SubmitTransactionResponse {
    txhash: string;
}

// Function to submit a transaction
export async function submitTransaction(
    tx: string
): Promise<SubmitTransactionResponse> {
    try {
        const response = await axios.get<SubmitTransactionResponse>(`${BASE_URL}/submit`, {
            params: { tx }
        });
        return response.data;
    } catch (error) {
        console.error('Error submitting transaction:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the Datum data structure
interface DatumResponse {
    datumHash: string;
}

// Function to get datum information
export async function getDatumHash(datumValue: string): Promise<DatumResponse> {
    try {
        const response = await axios.get<DatumResponse>(`${BASE_URL}/datum`, {
            params: { datumValue }
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching datum hash:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the Script data structure
interface ScriptResponse {
    address: string;
}

// Function to get script information
export async function getScriptAddress(cbor: string): Promise<ScriptResponse> {
    try {
        const response = await axios.get<ScriptResponse>(`${BASE_URL}/script`, {
            params: { cbor }
        });
        return response.data;
    } catch (error) {
        console.error('Error fetching script address:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Function to build a script transaction
interface TransactionResponse {
    type: string;
    description: string;
    cborHex: string;
}

export async function getTransactionDetails(
    senderAddress: string,
    receiverAddress: string,
    lovelace: number,
    txHash: string,
    txID: number,
    datumValue: string
): Promise<TransactionResponse> {
    try {
        const response = await axios.get<TransactionResponse>(`${BASE_URL}/script/${senderAddress}/${receiverAddress}/${lovelace}/${txHash}/${txID}/${datumValue}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching transaction details:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Function to get protocol parameters
interface ProtocolParamsResponse {
    collateralPercentage: number;
    costModels: {
        PlutusV1: number[];
        PlutusV2: number[];
    };
    decentralization: number | null;
    executionUnitPrices: {
        priceMemory: number;
        priceSteps: number;
    };
    extraPraosEntropy: string | null;
    maxBlockBodySize: number;
    maxBlockExecutionUnits: {
        memory: number;
        steps: number;
    };
    maxBlockHeaderSize: number;
    maxCollateralInputs: number;
    maxTxExecutionUnits: {
        memory: number;
        steps: number;
    };
    maxTxSize: number;
    maxValueSize: number;
    minPoolCost: number;
    minUTxOValue: number | null;
    monetaryExpansion: number;
    poolPledgeInfluence: number;
    poolRetireMaxEpoch: number;
    protocolVersion: {
        major: number;
        minor: number;
    };
    stakeAddressDeposit: number;
    stakePoolDeposit: number;
    stakePoolTargetNum: number;
    treasuryCut: number;
    txFeeFixed: number;
    txFeePerByte: number;
    utxoCostPerByte: number;
}

export async function getProtocolParams(): Promise<ProtocolParamsResponse> {
    try {
        const response = await axios.get<ProtocolParamsResponse>(`${BASE_URL}/protocolparam`);
        return response.data;
    } catch (error) {
        console.error('Error fetching protocol parameters:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the response from the stake build endpoint
interface StakeBuildResponse {
    address: string;
}

// Function to build a stake address
export async function buildStakeAddress(paymentKey: string, script: string): Promise<StakeBuildResponse> {
    try {
        const response = await axios.get<StakeBuildResponse>(`${BASE_URL}/stakebuild`, {
            params: { paymentKey, script }
        });
        return response.data;
    } catch (error) {
        console.error('Error building stake address:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

// Define an interface for the response from the stakepool endpoint
interface StakePoolResponse {
    stakepool: string;  // Adjust the type if the stake pool information structure changes
}

// Function to get stake pool information
export async function getStakePoolInfo(): Promise<StakePoolResponse> {
    try {
        const response = await axios.get<StakePoolResponse>(`${BASE_URL}/stakepool`);
        return response.data;
    } catch (error) {
        console.error('Error retrieving stake pool information:', error instanceof Error ? error.message : String(error));
        throw error;
    }
}

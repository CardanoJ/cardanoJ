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
export declare function getAddressInfo(): Promise<Address>;
interface UTXO {
    txHash: string;
    txID: number;
    amount: string;
    additionalInfo: string;
}
export declare function getUTXO(address: string): Promise<UTXO[]>;
interface BuildTransactionResponse {
    type: string;
    description: string;
    cborHex: string;
}
export declare function buildTransaction(senderAddress: string, receiverAddress: string, lovelace: number, transactionHash: string, transactionId: number): Promise<BuildTransactionResponse>;
interface SignTransactionResponse {
    type: string;
    description: string;
    cborHex: string;
}
export declare function signTransaction(signKey: string, txbody: string): Promise<SignTransactionResponse>;
interface SubmitTransactionResponse {
    txhash: string;
}
export declare function submitTransaction(tx: string): Promise<SubmitTransactionResponse>;
interface DatumResponse {
    datumHash: string;
}
export declare function getDatumHash(datumValue: string): Promise<DatumResponse>;
interface ScriptResponse {
    address: string;
}
export declare function getScriptAddress(cbor: string): Promise<ScriptResponse>;
interface TransactionResponse {
    type: string;
    description: string;
    cborHex: string;
}
export declare function getScriptTransactionDetails(senderAddress: string, receiverAddress: string, lovelace: number, txHash: string, txID: number, datumValue: string): Promise<TransactionResponse>;
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
export declare function getProtocolParams(): Promise<ProtocolParamsResponse>;
interface StakeBuildResponse {
    address: string;
}
export declare function buildStakeAddress(paymentKey: string, script: string): Promise<StakeBuildResponse>;
interface StakePoolResponse {
    stakepool: string;
}
export declare function getStakePoolInfo(): Promise<StakePoolResponse>;
export {};
//# sourceMappingURL=index.d.ts.map
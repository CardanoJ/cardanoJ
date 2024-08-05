"use strict";
// import axios from 'axios';
// import MockAdapter from 'axios-mock-adapter';
// import { getAddressInfo, getUTXO, buildTransaction, signTransaction, submitTransaction, getDatumHash, getScriptAddress,getTransactionDetails, getProtocolParams, buildStakeAddress, getStakePoolInfo } from '../src/index';
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
// const mock = new MockAdapter(axios);
// jest.mock('axios');
// const mockedAxios = axios as jest.Mocked<typeof axios>;
// describe('API functions', () => {
//     afterEach(() => {
//         // Reset any changes to the mock after each test
//         mock.reset();
//       });
//     it('should get address info', async () => {
//         const mockAddressData = {
//             vkey: { type: 'string', description: 'string', cborHex: 'string' },
//             skey: { type: 'string', description: 'string', cborHex: 'string' },
//             address: 'string'
//         };
//         mockedAxios.get.mockResolvedValueOnce({ data: mockAddressData });
//         const data = await getAddressInfo();
//         expect(data).toEqual(mockAddressData);
//     });
//     it('should get UTXO', async () => {
//         const mockUTXOData = [
//             { txHash: 'string', txID: 1, amount: 'string', additionalInfo: 'string' }
//         ];
//         mockedAxios.get.mockResolvedValueOnce({ data: mockUTXOData });
//         const data = await getUTXO('addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2');
//         expect(data).toEqual(mockUTXOData);
//     });
//     it('should build transaction', async () => {
//         const mockBuildTransactionData = { type: 'string', description: 'string', cborHex: 'string' };
//         mockedAxios.get.mockResolvedValueOnce({ data: mockBuildTransactionData });
//         const data = await buildTransaction('addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2', 'addr_test1vp44nmn5a9klmn6eglea9mhjxphlryqe9cvlu43deh7tp2sj03e9g', 1000000, '8d085d681253d96d514f16ece10ca144f9e733fc94527107d08b1593a7dc692c', '1');
//         expect(data).toEqual(mockBuildTransactionData);
//     });
//     it('should sign transaction', async () => {
//         const mockSignTransactionData = { type: 'string', description: 'string', cborHex: 'string' };
//         mockedAxios.get.mockResolvedValueOnce({ data: mockSignTransactionData });
//         const data = await signTransaction('%7B%0A%20%20%20%20%22type%22%3A%20%22PaymentSigningKeyShelley_ed25519%22%2C%0A%20%20%20%20%22description%22%3A%20%22Payment%20Signing%20Key%22%2C%0A%20%20%20%20%22cborHex%22%3A%20%225820eaadd017a202d990e3918022623cc3a2d861a4efb5641c7863d160ef9d40c7dc%22%0A%7D', '%7B%0A%20%20%20%20%22type%22%3A%20%22Unwitnessed%20Tx%20BabbageEra%22%2C%0A%20%20%20%20%22description%22%3A%20%22Ledger%20Cddl%20Format%22%2C%0A%20%20%20%20%22cborHex%22%3A%20%2284a30081825820e2f8cb915a664c2bdbbe8dec8fc66a544004bdac9ea282d96645091a0446692001018282581d60b0a50d3b48510b903a7ceb963e119520c1446505362e2d98e8a7ca221a000f424082581d6073910a62b3d834ae39bf3606a3223b4bb52c6eff34d76d4c8e785f521b00000002437269d5021a000286a9a0f5f6%22%0A%7D');
//         expect(data).toEqual(mockSignTransactionData);
//     });
//     it('should submit transaction', async () => {
//         const mockSubmitTransactionData = { txhash: 'string' };
//         mockedAxios.get.mockResolvedValueOnce({ data: mockSubmitTransactionData });
//         const data = await submitTransaction("%7B%0A%20%20type%3A%20'Witnessed%20Tx%20BabbageEra'%2C%0A%20%20description%3A%20'Ledger%20Cddl%20Format'%2C%0A%20%20cborHex%3A%20'84a30081825820e2f8cb915a664c2bdbbe8dec8fc66a544004bdac9ea282d96645091a0446692001018282581d60b0a50d3b48510b903a7ceb963e119520c1446505362e2d98e8a7ca221a000f424082581d6073910a62b3d834ae39bf3606a3223b4bb52c6eff34d76d4c8e785f521b00000002437269d5021a000286a9a100818258206b91df1cd791f8777de3ec065b4e4655cad9819490ffaeb1e9f6ff9eee995e4e5840af55d24b006e9284930046168f7982658d8ef2247fa4af4b0534cae7db2e77f8e0aab10a34232cd911ec13d452292a8f52aad0b8e7b628a100ebf94219769d0cf5f6'%0A%7D");
//         expect(data).toEqual(mockSubmitTransactionData);
//     });
//     it('should get datum hash', async () => {
//         const mockDatumResponse = { datumHash: 'string' };
//         mockedAxios.get.mockResolvedValueOnce({ data: mockDatumResponse });
//         const data = await getDatumHash(123);
//         expect(data).toEqual(mockDatumResponse);
//     });
//     it('should get script address', async () => {
//         const mockScriptResponse = { address: 'string' };
//         mockedAxios.get.mockResolvedValueOnce({ data: mockScriptResponse });
//         const data = await getScriptAddress('%7b%20%20%20%0a%20%20%20%20%22type%22%3a%20%22PlutusScriptV1%22%2c%0a%20%20%20%20%22description%22%3a%20%22%22%2c%0a%20%20%20%20%22cborHex%22%3a%20%224e4d01000033222220051200120011%22%0a%7d%0a');
//         expect(data).toEqual(mockScriptResponse);
//     });
//     it('should get transaction details', async () => {
//         const mockTransactionResponse = { type: 'string', description: 'string', cborHex: 'string' };
//         mockedAxios.get.mockResolvedValueOnce({ data: mockTransactionResponse });
//         const data = await getTransactionDetails('addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2', 'addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8', 1000000, '100e0bd6f28708c6acff22da143f9229c8fb03fa8c2407246f44759fc8a39dd5', '8d085d681253d96d514f16ece10ca144f9e733fc94527107d08b1593a7dc692c', 1);
//         expect(data).toEqual(mockTransactionResponse);
//     });
//     it('should get protocol parameters', async () => {
//         const mockProtocolParamsResponse = {
//             collateralPercentage: 1,
//             costModels: { PlutusV1: [1], PlutusV2: [2] },
//             decentralization: null,
//             executionUnitPrices: { priceMemory: 1, priceSteps: 1 },
//             extraPraosEntropy: null,
//             maxBlockBodySize: 1,
//             maxBlockExecutionUnits: { memory: 1, steps: 1 },
//             maxBlockHeaderSize: 1,
//             maxCollateralInputs: 1,
//             maxTxExecutionUnits: { memory: 1, steps: 1 },
//             maxTxSize: 1,
//             maxValueSize: 1,
//             minPoolCost: 1,
//             minUTxOValue: null,
//             monetaryExpansion: 1,
//             poolPledgeInfluence: 1,
//             poolRetireMaxEpoch: 1,
//             protocolVersion: { major: 1, minor: 1 },
//             stakeAddressDeposit: 1,
//             stakePoolDeposit: 1,
//             stakePoolTargetNum: 1,
//             treasuryCut: 1,
//             txFeeFixed: 1,
//             txFeePerByte: 1,
//             utxoCostPerByte: 1
//         };
//         mockedAxios.get.mockImplementationOnce(() => Promise.resolve({ data: mockProtocolParamsResponse }));
//         const data = await getProtocolParams();
//         expect(data).toEqual(mockProtocolParamsResponse);
//     });
//     it('should build stake address', async () => {
//         const mockStakeBuildResponse = { address: 'string' };
//         mockedAxios.get.mockImplementationOnce(() => Promise.resolve({ data: mockStakeBuildResponse }));
//         const data = await buildStakeAddress('%7b%0a%20%20%20%20%22type%22%3a%20%22PaymentVerificationKeyShelley_ed25519%22%2c%0a%20%20%20%20%22description%22%3a%20%22Payment%20Verification%20Key%22%2c%0a%20%20%20%20%22cborHex%22%3a%20%225820f20faa630348393b4cc48e4969c0a3ba703235dcc17db1abbc1fdfed27de51b8%22%0a%7d', '%7b%0a%20%20%20%20%22type%22%3a%20%22PlutusScriptV2%22%2c%0a%20%20%20%20%22description%22%3a%20%22%22%2c%0a%20%20%20%20%22cborHex%22%3a%20%22590a16590a13010000332323322332232323232323232323232323232332232323232323232323232323222232325335323232333353500222001202820272325335333573466e20cdc1240086666aa6016240026466a01e44666a0200060020046a01a00266a01c44460066004002400244a66a646a002446a01e446466a00a466a0084a66a666ae68cdc78010008198190a80188191019119a80210191299a999ab9a3371e0040020660642a00620642a66a00642a66a004426603000400220602a66a0024206020606a0044444008266e00004c8c09c004d4008888800c4004d5400c888888888888029200053353001355003222222222222006210011326320223357389201147769746864726177616c206e6f7420666f756e64000220280291029133573892011b696e73756666696369656e74207265776172642073686172696e67000283200135502a22533500115018221350022253353301400200713501d001130060032027135001220023333573466e1cd55cea801a4000466442466002006004646464646464646464646464646666ae68cdc39aab9d500c480008cccccccccccc88888888888848cccccccccccc00403403002c02802402001c01801401000c008cd4070074d5d0a80619a80e00e9aba1500b33501c01e35742a014666aa040eb9407cd5d0a804999aa8103ae501f35742a01066a03804e6ae85401cccd540800a1d69aba150063232323333573466e1cd55cea801240004664424660020060046464646666ae68cdc39aab9d5002480008cc8848cc00400c008cd40c9d69aba150023033357426ae8940088c98c80d4cd5ce01b01a81989aab9e5001137540026ae854008c8c8c8cccd5cd19b8735573aa004900011991091980080180119a8193ad35742a00460666ae84d5d1280111931901a99ab9c036035033135573ca00226ea8004d5d09aba2500223263203133573806406205e26aae7940044dd50009aba1500533501c75c6ae854010ccd540800908004d5d0a801999aa8103ae200135742a004604c6ae84d5d1280111931901699ab9c02e02d02b135744a00226ae8940044d5d1280089aba25001135744a00226ae8940044d5d1280089aba25001135744a00226ae8940044d55cf280089baa00135742a006602c6ae84d5d1280191931900f99ab9c02001f01d3333573466e1cd55ce9baa0044800080788c98c8078cd5ce00f80f00e080e89931900e99ab9c491035054350001d135573ca00226ea8004c8004d5408088448894cd40044d401800c884ccd4024014c010008ccd54c01c4800401401000448d40048800448d40048800848848cc00400c00888cd40088cd40088cd40088cd40088cc01c008004807c8cd4008807c8cc01c00800488807c888cd4010807c8894cd4ccd5cd19b8700600302202115335333573466e1c0140080880844ccd5cd19b870040010220211021102122333573466e3c00800406c06848c88c008dd6000990009aa80d911999aab9f0012501a233501930043574200460066ae880080508c8c8cccd5cd19b8735573aa004900011991091980080180118061aba150023005357426ae8940088c98c8050cd5ce00a80a00909aab9e5001137540024646464646666ae68cdc39aab9d5004480008cccc888848cccc00401401000c008c8c8c8cccd5cd19b8735573aa0049000119910919800801801180a9aba1500233500d014357426ae8940088c98c8064cd5ce00d00c80b89aab9e5001137540026ae854010ccd54021d728039aba150033232323333573466e1d4005200423212223002004357426aae79400c8cccd5cd19b875002480088c84888c004010dd71aba135573ca00846666ae68cdc3a801a400042444006464c6403666ae7007006c06406005c4d55cea80089baa00135742a00466a012eb8d5d09aba2500223263201533573802c02a02626ae8940044d5d1280089aab9e500113754002266aa002eb9d6889119118011bab00132001355018223233335573e0044a030466a02e66442466002006004600c6aae754008c014d55cf280118021aba200301213574200224464646666ae68cdc3a800a400046a00e600a6ae84d55cf280191999ab9a3370ea00490011280391931900919ab9c01301201000f135573aa00226ea800448488c00800c44880048c8c8cccd5cd19b875001480188c848888c010014c01cd5d09aab9e500323333573466e1d400920042321222230020053009357426aae7940108cccd5cd19b875003480088c848888c004014c01cd5d09aab9e500523333573466e1d40112000232122223003005375c6ae84d55cf280311931900819ab9c01101000e00d00c00b135573aa00226ea80048c8c8cccd5cd19b8735573aa004900011991091980080180118029aba15002375a6ae84d5d1280111931900619ab9c00d00c00a135573ca00226ea80048c8cccd5cd19b8735573aa002900011bae357426aae7940088c98c8028cd5ce00580500409baa001232323232323333573466e1d4005200c21222222200323333573466e1d4009200a21222222200423333573466e1d400d2008233221222222233001009008375c6ae854014dd69aba135744a00a46666ae68cdc3a8022400c4664424444444660040120106eb8d5d0a8039bae357426ae89401c8cccd5cd19b875005480108cc8848888888cc018024020c030d5d0a8049bae357426ae8940248cccd5cd19b875006480088c848888888c01c020c034d5d09aab9e500b23333573466e1d401d2000232122222223005008300e357426aae7940308c98c804ccd5ce00a00980880800780700680600589aab9d5004135573ca00626aae7940084d55cf280089baa0012323232323333573466e1d400520022333222122333001005004003375a6ae854010dd69aba15003375a6ae84d5d1280191999ab9a3370ea0049000119091180100198041aba135573ca00c464c6401866ae700340300280244d55cea80189aba25001135573ca00226ea80048c8c8cccd5cd19b875001480088c8488c00400cdd71aba135573ca00646666ae68cdc3a8012400046424460040066eb8d5d09aab9e500423263200933573801401200e00c26aae7540044dd500089119191999ab9a3370ea00290021091100091999ab9a3370ea00490011190911180180218031aba135573ca00846666ae68cdc3a801a400042444004464c6401466ae7002c02802001c0184d55cea80089baa0012323333573466e1d40052002200c23333573466e1d40092000200c23263200633573800e00c00800626aae74dd5000a4c240029201035054310032001355007223350014800088d4008894cd4ccd5cd19b8f00200c00b00a130070011300600332001355006223350014800088d4008894cd4ccd5cd19b8f00200b00a0091001130060031122002122122330010040031220021220014881001123230010012233003300200200133351222513335122233512233002300548811c73910a62b3d834ae39bf3606a3223b4bb52c6eff34d76d4c8e785f520050072212330010030022001212230020032122300100320011212230020031122001120011%22%0a%7d');
//         expect(data).toEqual(mockStakeBuildResponse);
//     });
//     it('should get stake pool info', async () => {
//         const mockStakePoolResponse = { stakepool: 'string' };
//         mockedAxios.get.mockImplementationOnce(() => Promise.resolve({ data: mockStakePoolResponse }));
//         const data = await getStakePoolInfo();
//         expect(data).toEqual(mockStakePoolResponse);
//     });
// });
// import {
//     getAddressInfo,
//     getUTXO,
//     buildTransaction,
//     signTransaction,
//     submitTransaction,
//     getDatumHash,
//     getScriptAddress,
//     getTransactionDetails,
//     getProtocolParams,
//     buildStakeAddress,
//     getStakePoolInfo
// } from '../src/index';
// describe('Cardano-J Library Functions', () => {
//     test('getAddressInfo should return address info', async () => {
//         const result = await getAddressInfo();
//         console.log(result);
//         expect(result).toBeDefined();
//         expect(result).toHaveProperty('address');
//     });
//     test('getUTXO should return UTXO info', async () => {
//         const address = 'addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2';
//         const result = await getUTXO(address);
//         console.log(result);
//         expect(result).toBeDefined();
//         expect(result).toHaveProperty('utxo');
//         // Additional checks based on expected structure
//     });
//     test('buildTransaction should return transaction info', async () => {
//         const result = await buildTransaction(
//             "addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2",
//             "addr_test1vp44nmn5a9klmn6eglea9mhjxphlryqe9cvlu43deh7tp2sj03e9g",
//             1000000,
//             "8d085d681253d96d514f16ece10ca144f9e733fc94527107d08b1593a7dc692c",
//             1
//         );
//         console.log(result);
//         expect(result).toBeDefined();
//         expect(result).toHaveProperty('transaction');
//     });
//     test('signTransaction should return signed transaction info', async () => {
//         const result = await signTransaction(
//             "%7B%0A%20%20%20%20%22type%22%3A%20%22PaymentSigningKeyShelley_ed25519%22%2C%0A%20%20%20%20%22description%22%3A%20%22Payment%20Signing%20Key%22%2C%0A%20%20%20%20%22cborHex%22%3A%20%225820eaadd017a202d990e3918022623cc3a2d861a4efb5641c7863d160ef9d40c7dc%22%0A%7D",
//             "%7B%0A%20%20%20%20%22type%22%3A%20%22Unwitnessed%20Tx%20BabbageEra%22%2C%0A%20%20%20%20%22description%22%3A%20%22Ledger%20Cddl%20Format%22%2C%0A%20%20%20%20%22cborHex%22%3A%20%2284a30081825820e2f8cb915a664c2bdbbe8dec8fc66a544004bdac9ea282d96645091a0446692001018282581d60b0a50d3b48510b903a7ceb963e119520c1446505362e2d98e8a7ca221a000f424082581d6073910a62b3d834ae39bf3606a3223b4bb52c6eff34d76d4c8e785f521b00000002437269d5021a000286a9a0f5f6%22%0A%7D"
//         );
//         console.log(result);
//         expect(result).toBeDefined();
//         expect(result).toHaveProperty('signedTransaction');
//     });
//     test('submitTransaction should return submission confirmation', async () => {
//         const result = await submitTransaction(
//             "%7B%0A%20%20type%3A%20'Witnessed%20Tx%20BabbageEra'%2C%0A%20%20description%3A%20'Ledger%20Cddl%20Format'%2C%0A%20%20cborHex%3A%20'84a30081825820e2f8cb915a664c2bdbbe8dec8fc66a544004bdac9ea282d96645091a0446692001018282581d60b0a50d3b48510b903a7ceb963e119520c1446505362e2d98e8a7ca221a000f424082581d6073910a62b3d834ae39bf3606a3223b4bb52c6eff34d76d4c8e785f521b00000002437269d5021a000286a100818258206b91df1cd791f8777de3ec065b4e4655cad9819490ffaeb1e9f6ff9eee995e4e5840af55d24b006e9284930046168f7982658d8ef2247fa4af4b0534cae7db2e77f8e0aab10a34232cd911ec13d452292a8f52aad0b8e7b628a100ebf94219769d0cf5f6'%0A%7D"
//         );
//         console.log(result);
//         expect(result).toBeDefined();
//         expect(result).toHaveProperty('submissionStatus');
//     });
//     test('getDatumHash should return datum hash', async () => {
//         const result = await getDatumHash("1234");
//         console.log(result);
//         expect(result).toBeDefined();
//         expect(result).toHaveProperty('datumHash');
//     });
//     test('getScriptAddress should return script address', async () => {
//         const result = await getScriptAddress(
//             "%7b%20%20%20%0a%20%20%20%20%22type%22%3a%20%22PlutusScriptV1%22%2c%0a%20%20%20%20%22description%22%3a%20%22%22%2c%0a%20%20%20%20%22cborHex%22%3a%20%224e4d01000033222220051200120011%22%0a%7d%0a"
//         );
//         console.log(result);
//         expect(result).toBeDefined();
//         expect(result).toHaveProperty('scriptAddress');
//     });
// test('getTransactionDetails should return transaction details', async () => {
//     const result = await getTransactionDetails(
//         "addr_test1vpeezznzk0vrft3ehumqdgez8d9m2trwlu6dwm2v3eu975s9ngev2",
//         "addr_test1wpnlxv2xv9a9ucvnvzqakwepzl9ltx7jzgm53av2e9ncv4sysemm8",
//         1000000,
//         "100e0bd6f28708c6acff22da143f9229c8fb03fa8c2407246f44759fc8a39dd5", 1, "8d085d681253d96d514f16ece10ca144f9e733fc94527107d08b1593a7dc692c"
//     );
//     console.log(result);
//     expect(result).toBeDefined();
//     expect(result).toHaveProperty('transactionDetails');
// });
// test('getProtocolParams should return protocol parameters', async () => {
//     const result = await getProtocolParams();
//     console.log(result);
//     expect(result).toBeDefined();
//     expect(result).toHaveProperty('protocolParams');
// });
// test('buildStakeAddress should return stake address', async () => {
//     const result = await buildStakeAddress("%7b%0a%20%20%20%20%22type%22%3a%20%22PaymentVerificationKeyShelley_ed25519%22%2c%0a%20%20%20%20%22description%22%3a%20%22Payment%20Verification%20Key%22%2c%0a%20%20%20%20%22cborHex%22%3a%20%225820f20faa630348393b4cc48e4969c0a3ba703235dcc17db1abbc1fdfed27de51b8%22%0a%7d", "%7b%0a%20%20%20%20%22type%22%3a%20%22PlutusScriptV2%22%2c%0a%20%20%20%20%22description%22%3a%20%22%22%2c%0a%20%20%20%20%22cborHex%22%3a%20%22590a16590a13010000332323322332232323232323232323232323232332232323232323232323232323222232325335323232333353500222001202820272325335333573466e20cdc1240086666aa6016240026466a01e44666a0200060020046a01a00266a01c44460066004002400244a66a646a002446a01e446466a00a466a0084a66a666ae68cdc78010008198190a80188191019119a80210191299a999ab9a3371e0040020660642a00620642a66a00642a66a004426603000400220602a66a0024206020606a0044444008266e00004c8c09c004d4008888800c4004d5400c888888888888029200053353001355003222222222222006210011326320223357389201147769746864726177616c206e6f7420666f756e64000220280291029133573892011b696e73756666696369656e74207265776172642073686172696e67000283200135502a22533500115018221350022253353301400200713501d001130060032027135001220023333573466e1cd55cea801a4000466442466002006004646464646464646464646464646666ae68cdc39aab9d500c480008cccccccccccc88888888888848cccccccccccc00403403002c02802402001c01801401000c008cd4070074d5d0a80619a80e00e9aba1500b33501c01e35742a014666aa040eb9407cd5d0a804999aa8103ae501f35742a01066a03804e6ae85401cccd540800a1d69aba150063232323333573466e1cd55cea801240004664424660020060046464646666ae68cdc39aab9d5002480008cc8848cc00400c008cd40c9d69aba150023033357426ae8940088c98c80d4cd5ce01b01a81989aab9e5001137540026ae854008c8c8c8cccd5cd19b8735573aa004900011991091980080180119a8193ad35742a00460666ae84d5d1280111931901a99ab9c036035033135573ca00226ea8004d5d09aba2500223263203133573806406205e26aae7940044dd50009aba1500533501c75c6ae854010ccd540800908004d5d0a801999aa8103ae200135742a004604c6ae84d5d1280111931901699ab9c02e02d02b135744a00226ae8940044d5d1280089aba25001135744a00226ae8940044d5d1280089aba25001135744a00226ae8940044d55cf280089baa00135742a006602c6ae84d5d1280191931900f99ab9c02001f01d3333573466e1cd55ce9baa0044800080788c98c8078cd5ce00f80f00e080e89931900e99ab9c491035054350001d135573ca00226ea8004c8004d5408088448894cd40044d401800c884ccd4024014c010008ccd54c01c4800401401000448d40048800448d40048800848848cc00400c00888cd40088cd40088cd40088cd40088cc01c008004807c8cd4008807c8cc01c00800488807c888cd4010807c8894cd4ccd5cd19b8700600302202115335333573466e1c0140080880844ccd5cd19b870040010220211021102122333573466e3c00800406c06848c88c008dd6000990009aa80d911999aab9f0012501a233501930043574200460066ae880080508c8c8cccd5cd19b8735573aa004900011991091980080180118061aba150023005357426ae8940088c98c8050cd5ce00a80a00909aab9e5001137540024646464646666ae68cdc39aab9d5004480008cccc888848cccc00401401000c008c8c8c8cccd5cd19b8735573aa0049000119910919800801801180a9aba1500233500d014357426ae8940088c98c8064cd5ce00d00c80b89aab9e5001137540026ae854010ccd54021d728039aba150033232323333573466e1d4005200423212223002004357426aae79400c8cccd5cd19b875002480088c84888c004010dd71aba135573ca00846666ae68cdc3a801a400042444006464c6403666ae7007006c06406005c4d55cea80089baa00135742a00466a012eb8d5d09aba2500223263201533573802c02a02626ae8940044d5d1280089aab9e500113754002266aa002eb9d6889119118011bab00132001355018223233335573e0044a030466a02e66442466002006004600c6aae754008c014d55cf280118021aba200301213574200224464646666ae68cdc3a800a400046a00e600a6ae84d55cf280191999ab9a3370ea00490011280391931900919ab9c01301201000f135573aa00226ea800448488c00800c44880048c8c8cccd5cd19b875001480188c848888c010014c01cd5d09aab9e500323333573466e1d400920042321222230020053009357426aae7940108cccd5cd19b875003480088c848888c004014c01cd5d09aab9e500523333573466e1d40112000232122223003005375c6ae84d55cf280311931900819ab9c01101000e00d00c00b135573aa00226ea80048c8c8cccd5cd19b8735573aa004900011991091980080180118029aba15002375a6ae84d5d1280111931900619ab9c00d00c00a135573ca00226ea80048c8cccd5cd19b8735573aa002900011bae357426aae7940088c98c8028cd5ce00580500409baa001232323232323333573466e1d4005200c21222222200323333573466e1d4009200a21222222200423333573466e1d400d2008233221222222233001009008375c6ae854014dd69aba135744a00a46666ae68cdc3a8022400c4664424444444660040120106eb8d5d0a8039bae357426ae89401c8cccd5cd19b875005480108cc8848888888cc018024020c030d5d0a8049bae357426ae8940248cccd5cd19b875006480088c848888888c01c020c034d5d09aab9e500b23333573466e1d401d2000232122222223005008300e357426aae7940308c98c804ccd5ce00a00980880800780700680600589aab9d5004135573ca00626aae7940084d55cf280089baa0012323232323333573466e1d400520022333222122333001005004003375a6ae854010dd69aba15003375a6ae84d5d1280191999ab9a3370ea0049000119091180100198041aba135573ca00c464c6401866ae700340300280244d55cea80189aba25001135573ca00226ea80048c8c8cccd5cd19b875001480088c8488c00400cdd71aba135573ca00646666ae68cdc3a8012400046424460040066eb8d5d09aab9e500423263200933573801401200e00c26aae7540044dd500089119191999ab9a3370ea00290021091100091999ab9a3370ea00490011190911180180218031aba135573ca00846666ae68cdc3a801a400042444004464c6401466ae7002c02802001c0184d55cea80089baa0012323333573466e1d40052002200c23333573466e1d40092000200c23263200633573800e00c00800626aae74dd5000a4c240029201035054310032001355007223350014800088d4008894cd4ccd5cd19b8f00200c00b00a130070011300600332001355006223350014800088d4008894cd4ccd5cd19b8f00200b00a0091001130060031122002122122330010040031220021220014881001123230010012233003300200200133351222513335122233512233002300548811c73910a62b3d834ae39bf3606a3223b4bb52c6eff34d76d4c8e785f520050072212330010030022001212230020032122300100320011212230020031122001120011%22%0a%7d");
//     console.log(result);
//     expect(result).toBeDefined();
//     expect(result).toHaveProperty('stakeAddress');
// });
// test('getStakePoolInfo should return stake key info', async () => {
//     const result = await getStakePoolInfo();
//     console.log(result);
//     expect(result).toBeDefined();
//     expect(result).toHaveProperty('StakePoolInfo');
// });
// });
const globals_1 = require("@jest/globals");
const axios_1 = __importDefault(require("axios"));
const index_1 = require("../src/index"); // Adjust the path as necessary
// Mock data
const mockAddressInfo = {
    vkey: {
        type: 'someType',
        description: 'someDescription',
        cborHex: 'someCborHex'
    },
    skey: {
        type: 'someType',
        description: 'someDescription',
        cborHex: 'someCborHex'
    },
    address: 'someAddress'
};
const mockUTXO = [
    {
        txHash: 'someTxHash',
        txID: 1,
        amount: '1000',
        additionalInfo: 'someAdditionalInfo'
    }
];
const mockTransaction = {
    type: 'someType',
    description: 'someDescription',
    cborHex: 'someCborHex'
};
const mockDatumHash = {
    datumHash: 'someDatumHash'
};
const mockScriptAddress = {
    address: 'someScriptAddress'
};
const mockProtocolParams = {
    collateralPercentage: 10,
    costModels: {
        PlutusV1: [1, 2, 3],
        PlutusV2: [4, 5, 6]
    },
    decentralization: 0.5,
    executionUnitPrices: {
        priceMemory: 1,
        priceSteps: 2
    },
    extraPraosEntropy: 'someEntropy',
    maxBlockBodySize: 10000,
    maxBlockExecutionUnits: {
        memory: 1000000,
        steps: 2000000
    },
    maxBlockHeaderSize: 2000,
    maxCollateralInputs: 5,
    maxTxExecutionUnits: {
        memory: 1000000,
        steps: 2000000
    },
    maxTxSize: 16000,
    maxValueSize: 5000,
    minPoolCost: 340000,
    minUTxOValue: 1000000,
    monetaryExpansion: 0.1,
    poolPledgeInfluence: 0.1,
    poolRetireMaxEpoch: 100,
    protocolVersion: {
        major: 1,
        minor: 0
    },
    stakeAddressDeposit: 2000000,
    stakePoolDeposit: 500000000,
    stakePoolTargetNum: 100,
    treasuryCut: 0.01,
    txFeeFixed: 200000,
    txFeePerByte: 1,
    utxoCostPerByte: 1
};
const mockStakeBuildResponse = {
    address: 'someStakeAddress'
};
const mockStakePoolInfo = {
    stakepool: 'someStakePool'
};
// Mocking axios
globals_1.jest.mock('axios');
const mockedAxios = axios_1.default;
// Define test cases
describe('API Functions', () => {
    beforeEach(() => {
        globals_1.jest.clearAllMocks();
    });
    test('getAddressInfo returns address info', () => __awaiter(void 0, void 0, void 0, function* () {
        mockedAxios.get.mockResolvedValueOnce({ data: mockAddressInfo });
        const result = yield (0, index_1.getAddressInfo)();
        expect(result).toEqual(mockAddressInfo);
    }));
    test('getUTXO returns UTXO', () => __awaiter(void 0, void 0, void 0, function* () {
        const address = 'someAddress';
        mockedAxios.get.mockResolvedValueOnce({ data: mockUTXO });
        const result = yield (0, index_1.getUTXO)(address);
        expect(result).toEqual(mockUTXO);
    }));
    test('buildTransaction returns transaction details', () => __awaiter(void 0, void 0, void 0, function* () {
        const senderAddress = 'senderAddress';
        const receiverAddress = 'receiverAddress';
        const lovelace = 1000;
        const transactionHash = 'someHash';
        const transactionId = 1;
        mockedAxios.get.mockResolvedValueOnce({ data: mockTransaction });
        const result = yield (0, index_1.buildTransaction)(senderAddress, receiverAddress, lovelace, transactionHash, transactionId);
        expect(result).toEqual(mockTransaction);
    }));
    test('signTransaction returns sign transaction response', () => __awaiter(void 0, void 0, void 0, function* () {
        const signKey = 'someSignKey';
        const txbody = 'someTxbody';
        mockedAxios.get.mockResolvedValueOnce({ data: mockTransaction });
        const result = yield (0, index_1.signTransaction)(signKey, txbody);
        expect(result).toEqual(mockTransaction);
    }));
    test('submitTransaction returns transaction hash', () => __awaiter(void 0, void 0, void 0, function* () {
        const tx = 'someTransaction';
        mockedAxios.get.mockResolvedValueOnce({ data: { txhash: 'someTxHash' } });
        const result = yield (0, index_1.submitTransaction)(tx);
        expect(result).toEqual({ txhash: 'someTxHash' });
    }));
    test('getDatumHash returns datum hash', () => __awaiter(void 0, void 0, void 0, function* () {
        const datumValue = 'someDatumValue';
        mockedAxios.get.mockResolvedValueOnce({ data: mockDatumHash });
        const result = yield (0, index_1.getDatumHash)(datumValue);
        expect(result).toEqual(mockDatumHash);
    }));
    test('getScriptAddress returns script address', () => __awaiter(void 0, void 0, void 0, function* () {
        const cbor = 'someCbor';
        mockedAxios.get.mockResolvedValueOnce({ data: mockScriptAddress });
        const result = yield (0, index_1.getScriptAddress)(cbor);
        expect(result).toEqual(mockScriptAddress);
    }));
    test('getTransactionDetails returns transaction details', () => __awaiter(void 0, void 0, void 0, function* () {
        const senderAddress = 'senderAddress';
        const receiverAddress = 'receiverAddress';
        const lovelace = 1000;
        const txHash = 'someHash';
        const txID = 1;
        const datumValue = 'someDatumValue';
        mockedAxios.get.mockResolvedValueOnce({ data: mockTransaction });
        const result = yield (0, index_1.getTransactionDetails)(senderAddress, receiverAddress, lovelace, txHash, txID, datumValue);
        expect(result).toEqual(mockTransaction);
    }));
    test('getProtocolParams returns protocol parameters', () => __awaiter(void 0, void 0, void 0, function* () {
        mockedAxios.get.mockResolvedValueOnce({ data: mockProtocolParams });
        const result = yield (0, index_1.getProtocolParams)();
        expect(result).toEqual(mockProtocolParams);
    }));
    test('buildStakeAddress returns stake address', () => __awaiter(void 0, void 0, void 0, function* () {
        const paymentKey = 'somePaymentKey';
        const script = 'someScript';
        mockedAxios.get.mockResolvedValueOnce({ data: mockStakeBuildResponse });
        const result = yield (0, index_1.buildStakeAddress)(paymentKey, script);
        expect(result).toEqual(mockStakeBuildResponse);
    }));
    test('getStakePoolInfo returns stake pool information', () => __awaiter(void 0, void 0, void 0, function* () {
        mockedAxios.get.mockResolvedValueOnce({ data: mockStakePoolInfo });
        const result = yield (0, index_1.getStakePoolInfo)();
        expect(result).toEqual(mockStakePoolInfo);
    }));
});

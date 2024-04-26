package com.cardanoj.function.helper;

import com.cardanoj.coreapi.account.Account;
import com.cardanoj.coreapi.UtxoSupplier;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.helper.FeeCalculationService;
import com.cardanoj.coreapi.model.ProtocolParams;
import com.cardanoj.coreapi.util.AssetUtil;
import com.cardanoj.coreapi.util.PolicyUtil;
import com.cardanoj.common.model.Networks;
import com.cardanoj.function.BaseTest;
import com.cardanoj.function.TxBuilder;
import com.cardanoj.function.TxBuilderContext;
import com.cardanoj.plutus.spec.BigIntPlutusData;
import com.cardanoj.plutus.spec.ExUnits;
import com.cardanoj.plutus.spec.Redeemer;
import com.cardanoj.plutus.spec.RedeemerTag;
import com.cardanoj.transaction.spec.*;
import com.cardanoj.util.HexUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.cardanoj.common.ADAConversionUtil.adaToLovelace;
import static com.cardanoj.common.CardanoConstants.ONE_ADA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FeeCalculatorsTest extends BaseTest {
    @Mock
    UtxoSupplier utxoSupplier;

    @Mock
    FeeCalculationService feeCalculationService;

    ProtocolParams protocolParams;

    @BeforeEach
    public void setup() throws IOException, ApiException {
        MockitoAnnotations.openMocks(this);

        protocolParamJsonFile = "protocol-params.json";
        protocolParams = (ProtocolParams) loadObjectFromJson("protocol-parameters", ProtocolParams.class);

        protocolParamJsonFile = "protocol-params.json";
        protocolParams = (ProtocolParams) loadObjectFromJson("protocol-parameters", ProtocolParams.class);
    }

    @Test
    void feeCalculator_whenAdaTransfer() throws Exception {
        BigInteger expectedFee = BigInteger.valueOf(18000);
        given(feeCalculationService.calculateFee(any(Transaction.class))).willReturn(expectedFee);

        //prepare transaction
        List<TransactionInput> inputs = List.of(
          new TransactionInput("735262c68b5fa220dee2b447d0d1dd44e0800ba6212dcea7955c561f365fb0e9", 0),
          new TransactionInput("88c014d348bf1919c78a5cb87a5beed87729ff3f8a2019be040117a41a83e82e", 1)
        );

        String receiver = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";
        Account account = new Account(Networks.testnet());
        String sender = account.baseAddress();

        Policy policy = PolicyUtil.createMultiSigScriptAllPolicy("test", 1);
        String unit = policy.getPolicyId() + HexUtil.encodeHexString("token1".getBytes(StandardCharsets.UTF_8));
        MultiAsset ma = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(800));
        MultiAsset changeMa = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(200));

        List<TransactionOutput> outputs = List.of(
                TransactionOutput.builder()
                        .address(receiver)
                        .value(Value.builder()
                            .coin(ONE_ADA.multiply(BigInteger.valueOf(5)))
                                .multiAssets(List.of(ma))
                                .build()
                        )
                    .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(ONE_ADA.multiply(BigInteger.valueOf(3)))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build()
        );

        TxBuilderContext context = new TxBuilderContext(utxoSupplier, protocolParams);
        context.setFeeCalculationService(feeCalculationService); //Mock

        Transaction transaction = new Transaction();
        TransactionBody body = TransactionBody.builder()
                .inputs(inputs)
                .outputs(outputs)
                .ttl(6500000).build();

        transaction.setBody(body);
        transaction.setValid(true);

        //apply
        TxBuilder txBuilder = FeeCalculators.feeCalculator(sender, 1);
        txBuilder.apply(context, transaction);

        //assert
        assertThat(transaction.getBody().getFee()).isEqualTo(expectedFee);
        assertThat(transaction.getBody().getOutputs().get(1).getValue().getCoin())
                .isEqualTo(ONE_ADA.multiply(BigInteger.valueOf(3)).subtract(expectedFee));
    }

    @Test
    void feeCalculator_withScript() throws Exception {
        BigInteger expectedFee = BigInteger.valueOf(18000);
        BigInteger scriptFee = BigInteger.valueOf(1000);

        given(feeCalculationService.calculateFee(any(Transaction.class))).willReturn(expectedFee);
        given(feeCalculationService.calculateScriptFee(ArgumentMatchers.<List<ExUnits>>any())).willReturn(scriptFee);

        //prepare transaction
        List<TransactionInput> inputs = List.of(
                new TransactionInput("735262c68b5fa220dee2b447d0d1dd44e0800ba6212dcea7955c561f365fb0e9", 0),
                new TransactionInput("88c014d348bf1919c78a5cb87a5beed87729ff3f8a2019be040117a41a83e82e", 1)
        );

        String receiver = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";
        Account account = new Account(Networks.testnet());
        String sender = account.baseAddress();

        Policy policy = PolicyUtil.createMultiSigScriptAllPolicy("test", 1);
        String unit = policy.getPolicyId() + HexUtil.encodeHexString("token1".getBytes(StandardCharsets.UTF_8));
        MultiAsset ma = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(800));
        MultiAsset changeMa = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(200));

        List<TransactionOutput> outputs = List.of(
                TransactionOutput.builder()
                        .address(receiver)
                        .value(Value.builder()
                                .coin(ONE_ADA.multiply(BigInteger.valueOf(5)))
                                .multiAssets(List.of(ma))
                                .build()
                        )
                        .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(ONE_ADA.multiply(BigInteger.valueOf(3)))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build()
        );

        TxBuilderContext context = new TxBuilderContext(utxoSupplier, protocolParams);
        context.setFeeCalculationService(feeCalculationService); //Mock FeeCalculationService

        Transaction transaction = new Transaction();
        TransactionBody body = TransactionBody.builder()
                .inputs(inputs)
                .outputs(outputs)
                .ttl(6500000).build();
        transaction.setBody(body);
        transaction.setAuxiliaryData(AuxiliaryData.builder()
                .plutusV1Scripts(List.of())
                .build());
        transaction.setWitnessSet(new TransactionWitnessSet());
        Redeemer redeemer = Redeemer.builder()
                .index(BigInteger.ZERO)
                .tag(RedeemerTag.Spend)
                .data(BigIntPlutusData.of(7))
                .exUnits(ExUnits.builder()
                    .mem(BigInteger.valueOf(20001))
                        .steps(BigInteger.valueOf(899))
                        .build()
                ).build();
        transaction.getWitnessSet().getRedeemers().add(redeemer);
        transaction.setValid(true);

        //apply
        TxBuilder txBuilder = FeeCalculators.feeCalculator(sender, 1);
        txBuilder.apply(context, transaction);

        //assert
        assertThat(transaction.getBody().getFee()).isEqualTo(expectedFee.add(scriptFee));
        assertThat(transaction.getBody().getOutputs().get(1).getValue().getCoin())
                .isEqualTo(ONE_ADA.multiply(BigInteger.valueOf(3)).subtract(expectedFee.add(scriptFee)));
    }

    @Test
    void feeCalculator_multipleChanges_whenNegtiveChangeOutput_deductFeeFromNegativeOutput() throws Exception {
        BigInteger expectedFee = BigInteger.valueOf(18000);
        given(feeCalculationService.calculateFee(any(Transaction.class))).willReturn(expectedFee);

        //prepare transaction
        List<TransactionInput> inputs = List.of(
                new TransactionInput("735262c68b5fa220dee2b447d0d1dd44e0800ba6212dcea7955c561f365fb0e9", 0),
                new TransactionInput("88c014d348bf1919c78a5cb87a5beed87729ff3f8a2019be040117a41a83e82e", 1)
        );

        String receiver = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";
        Account account = new Account(Networks.testnet());
        String sender = account.baseAddress();

        Policy policy = PolicyUtil.createMultiSigScriptAllPolicy("test", 1);
        String unit = policy.getPolicyId() + HexUtil.encodeHexString("token1".getBytes(StandardCharsets.UTF_8));
        MultiAsset ma = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(800));
        MultiAsset changeMa = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(200));

        List<TransactionOutput> outputs = List.of(
                TransactionOutput.builder()
                        .address(receiver)
                        .value(Value.builder()
                                .coin(ONE_ADA.multiply(BigInteger.valueOf(5)))
                                .multiAssets(List.of(ma))
                                .build()
                        )
                        .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(adaToLovelace(3))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(ONE_ADA.multiply(BigInteger.valueOf(-2)))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(adaToLovelace(5))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build()
        );

        TxBuilderContext context = new TxBuilderContext(utxoSupplier, protocolParams);
        context.setFeeCalculationService(feeCalculationService); //Mock

        Transaction transaction = new Transaction();
        TransactionBody body = TransactionBody.builder()
                .inputs(inputs)
                .outputs(outputs)
                .ttl(6500000).build();

        transaction.setBody(body);
        transaction.setValid(true);

        //apply
        TxBuilder txBuilder = FeeCalculators.feeCalculator(sender, 1);
        txBuilder.apply(context, transaction);

        //assert
        assertThat(transaction.getBody().getFee()).isEqualTo(expectedFee);
        assertThat(transaction.getBody().getOutputs().get(1).getValue().getCoin())
                .isEqualTo(adaToLovelace(3));
        assertThat(transaction.getBody().getOutputs().get(2).getValue().getCoin())
                .isEqualTo(ONE_ADA.multiply(BigInteger.valueOf(-2)).subtract(expectedFee));
        assertThat(transaction.getBody().getOutputs().get(3).getValue().getCoin())
                .isEqualTo(adaToLovelace(5));
    }

    @Test
    void feeCalculator_multipleChageOutput_deductFeeFromLargerChangeOutput() throws Exception {
        BigInteger expectedFee = BigInteger.valueOf(18000);
        given(feeCalculationService.calculateFee(any(Transaction.class))).willReturn(expectedFee);

        //prepare transaction
        List<TransactionInput> inputs = List.of(
                new TransactionInput("735262c68b5fa220dee2b447d0d1dd44e0800ba6212dcea7955c561f365fb0e9", 0),
                new TransactionInput("88c014d348bf1919c78a5cb87a5beed87729ff3f8a2019be040117a41a83e82e", 1)
        );

        String receiver = "addr_test1qqwpl7h3g84mhr36wpetk904p7fchx2vst0z696lxk8ujsjyruqwmlsm344gfux3nsj6njyzj3ppvrqtt36cp9xyydzqzumz82";
        Account account = new Account(Networks.testnet());
        String sender = account.baseAddress();

        Policy policy = PolicyUtil.createMultiSigScriptAllPolicy("test", 1);
        String unit = policy.getPolicyId() + HexUtil.encodeHexString("token1".getBytes(StandardCharsets.UTF_8));
        MultiAsset ma = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(800));
        MultiAsset changeMa = AssetUtil.getMultiAssetFromUnitAndAmount(unit, BigInteger.valueOf(200));

        List<TransactionOutput> outputs = List.of(
                TransactionOutput.builder()
                        .address(receiver)
                        .value(Value.builder()
                                .coin(ONE_ADA.multiply(BigInteger.valueOf(5)))
                                .multiAssets(List.of(ma))
                                .build()
                        )
                        .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(adaToLovelace(3))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(ONE_ADA.multiply(BigInteger.valueOf(2)))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build(),
                TransactionOutput.builder()
                        .address(sender)
                        .value(Value.builder()
                                .coin(adaToLovelace(5))
                                .multiAssets(List.of(changeMa))
                                .build()
                        )
                        .build()
        );

        TxBuilderContext context = new TxBuilderContext(utxoSupplier, protocolParams);
        context.setFeeCalculationService(feeCalculationService); //Mock

        Transaction transaction = new Transaction();
        TransactionBody body = TransactionBody.builder()
                .inputs(inputs)
                .outputs(outputs)
                .ttl(6500000).build();

        transaction.setBody(body);
        transaction.setValid(true);

        //apply
        TxBuilder txBuilder = FeeCalculators.feeCalculator(sender, 1);
        txBuilder.apply(context, transaction);

        //assert
        assertThat(transaction.getBody().getFee()).isEqualTo(expectedFee);
        assertThat(transaction.getBody().getOutputs().get(1).getValue().getCoin())
                .isEqualTo(adaToLovelace(3));
        assertThat(transaction.getBody().getOutputs().get(2).getValue().getCoin())
                .isEqualTo(ONE_ADA.multiply(BigInteger.valueOf(2)));
        assertThat(transaction.getBody().getOutputs().get(3).getValue().getCoin())
                .isEqualTo(adaToLovelace(5).subtract(expectedFee));
    }

}

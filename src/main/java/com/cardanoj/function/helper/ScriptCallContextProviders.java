package com.cardanoj.function.helper;

import com.cardanoj.coreapi.model.Utxo;
import com.cardanoj.coreapi.config.Configuration;
import com.cardanoj.exception.CborRuntimeException;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.function.TxBuilder;
import com.cardanoj.function.exception.TxBuildException;
import com.cardanoj.function.helper.model.ScriptCallContext;
import com.cardanoj.plutus.spec.*;
import com.cardanoj.transaction.spec.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import static com.cardanoj.function.helper.RedeemerUtil.getScriptInputIndex;

/**
 * Provides helper methods to add plutus script specific data
 */
public class ScriptCallContextProviders {

    /**
     * Function to add plutus script specific data to a <code>{@link Transaction}</code> object
     * @param sc required data as <code>{@link ScriptCallContext}</code>
     * @return <code>TxBuilder</code> function
     * @throws CborRuntimeException if cbor serialization/de-serialization error
     */
    public static TxBuilder createFromScriptCallContext(ScriptCallContext sc) {
        return scriptCallContext(sc.getScript(), sc.getUtxo(), sc.getDatum(), sc.getRedeemer(), sc.getRedeemerTag(), sc.getExUnits());
    }

    /**
     * Function to add plutus script specific data to a <code>{@link Transaction}</code> object.
     * <br>
     * <br>If custom Java objects are passed as redeemer and datum, it converts them to <code>{@link PlutusData}</code>
     * <br>Add redeemer and datum objects to <code>{@link TransactionWitnessSet}</code>
     * <br>Add plutus script to <code>{@link TransactionWitnessSet}</code>
     * <br>Compute script datahash and set it in <code>{@link TransactionBody}</code>
     *
     * @param plutusScript Plutus Script
     * @param utxo Script Utxo
     * @param datum Datum as PlutusData or custom Java object (with <code>{@link com.cardanoj.plutus.annotation.Constr}</code> annotation)
     * @param redeemerData Redeemer as PlutusData or custom Java object (with <code>{@link com.cardanoj.plutus.annotation.Constr}</code> annotation)
     * @param tag Redeemer tag
     * @param exUnits Execution Units
     * @param <T> Datum class type
     * @param <K> Redeemer class type
     * @return <code>TxBuilder</code> function
     * @throws CborRuntimeException if cbor serialization/de-serialization error
     */
    public static <T, K> TxBuilder scriptCallContext(PlutusScript plutusScript, Utxo utxo, T datum, K redeemerData,
                                                     RedeemerTag tag, ExUnits exUnits) {
        return (context, transaction) -> {
            int scriptInputIndex = -1;
            if (utxo != null) {
                scriptInputIndex = getScriptInputIndex(utxo, transaction);
                if (scriptInputIndex == -1)
                    throw new TxBuildException("Script utxo is not found in transaction inputs : " + utxo.getTxHash());
            }

            scriptCallContext(plutusScript, scriptInputIndex, datum, redeemerData, tag, exUnits).apply(context, transaction);
        };
    }

    /**
     * Function to add plutus script specific data to a <code>{@link Transaction}</code> object.
     * <br>
     * <br>If custom Java objects are passed as redeemer and datum, it converts them to <code>{@link PlutusData}</code>
     * <br>Add redeemer and datum objects to <code>{@link TransactionWitnessSet}</code>
     * <br>Add plutus script to <code>{@link TransactionWitnessSet}</code>
     * <br>Compute script datahash and set it in <code>{@link TransactionBody}</code>
     *
     * @param plutusScript Plutus Script
     * @param scriptInputIndex Script index in transaction input list
     * @param datum Datum as PlutusData or custom Java object (with <code>{@link com.cardanoj.plutus.annotation.Constr}</code> annotation)
     * @param redeemerData Redeemer as PlutusData or custom Java object (with <code>{@link com.cardanoj.plutus.annotation.Constr}</code> annotation)
     * @param tag Redeemer tag
     * @param exUnits Execution Units
     * @param <T> Datum class type
     * @param <K> Redeemer class type
     * @return <code>TxBuilder</code> function
     * @throws CborRuntimeException if cbor serialization/de-serialization error
     */
    public static <T, K> TxBuilder scriptCallContext(PlutusScript plutusScript, int scriptInputIndex, T datum, K redeemerData,
                                                     RedeemerTag tag, ExUnits exUnits) {
        Objects.requireNonNull(plutusScript);
        Objects.requireNonNull(tag);
        Objects.requireNonNull(exUnits);

        return (context, transaction) -> {
            if (transaction.getWitnessSet() == null) {
                transaction.setWitnessSet(new TransactionWitnessSet());
            }

            //Datum
            if (datum != null) {
                PlutusData datumPlutusData;
                if (datum instanceof PlutusData)
                    datumPlutusData = (PlutusData) datum;
                else
                    datumPlutusData = Configuration.INSTANCE.getPlutusObjectConverter().toPlutusData(datum);

                transaction.getWitnessSet().getPlutusDataList().add(datumPlutusData);
            }

            //redeemer
            if (redeemerData != null) {
                //redeemer
                PlutusData redeemerPlutusData;
                if (redeemerData instanceof PlutusData)
                    redeemerPlutusData = (PlutusData) redeemerData;
                else
                    redeemerPlutusData = Configuration.INSTANCE.getPlutusObjectConverter().toPlutusData(redeemerData);

                int index = scriptInputIndex;
                if (index == -1 && tag == RedeemerTag.Mint) {
                    if (transaction.getBody().getMint() != null && !transaction.getBody().getMint().isEmpty()) {
                        //sort multiassets
                        List<MultiAsset> sortedMultiAssets = MintUtil.getSortedMultiAssets(transaction.getBody().getMint());
                        try {
                            index = MintUtil.getIndexByPolicyId(sortedMultiAssets, plutusScript.getPolicyId());
                        } catch (CborSerializationException e) {
                            throw new TxBuildException("Error getting policy id from the mint script " + plutusScript);
                        }
                    }
                }

                //TODO -- index for RedeemerTag.Cert

                Redeemer redeemer = Redeemer.builder()
                        .tag(tag)
                        .data(redeemerPlutusData)
                        .index(index != -1? BigInteger.valueOf(index): BigInteger.ZERO)
                        .exUnits(exUnits).build();

                transaction.getWitnessSet().getRedeemers().add(redeemer);
            }

            if (plutusScript instanceof PlutusV1Script) {
                if (!transaction.getWitnessSet().getPlutusV1Scripts().contains(plutusScript)) //To avoid duplicate script in list
                    transaction.getWitnessSet().getPlutusV1Scripts().add((PlutusV1Script) plutusScript);
            } else if (plutusScript instanceof PlutusV2Script) {
                if (!transaction.getWitnessSet().getPlutusV2Scripts().contains(plutusScript)) //To avoid duplicate script in list
                    transaction.getWitnessSet().getPlutusV2Scripts().add((PlutusV2Script) plutusScript);
            }
        };
    }

}

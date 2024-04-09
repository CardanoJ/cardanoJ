package com.cardanoj.function.helper;

import com.cardanoj.coreapi.TransactionEvaluator;
import com.cardanoj.coreapi.exception.ApiException;
import com.cardanoj.coreapi.model.EvaluationResult;
import com.cardanoj.coreapi.model.Result;
import com.cardanoj.exception.CborSerializationException;
import com.cardanoj.function.TxBuilder;
import com.cardanoj.function.exception.TxBuildException;
import com.cardanoj.plutus.spec.Redeemer;

import java.util.List;

/**
 * Helper functions to evaluate script costs
 */
public class ScriptCostEvaluators {

    //TODO -- Unit tests pending
    /**
     * Function to evaluate script costs and add to <code>{@link Redeemer}</code> objects.
     * <br>
     * It uses <code>{@link TransactionEvaluator}</code> set in {@link com.cardanoj.function.TxBuilderContext}
     * to evaluate script costs
     * @return TxBuilder function
     */
    public static TxBuilder evaluateScriptCost() {
        return (ctx, transaction) -> {
            if (transaction.getWitnessSet().getRedeemers() == null ||
                    transaction.getWitnessSet().getRedeemers().isEmpty())
                return; //non-script transaction

            TransactionEvaluator transactionEvaluator = ctx.getTxnEvaluator();
            if (transactionEvaluator == null)
                throw new TxBuildException("Transaction evaluator is not set. Transaction evaluator is required to calculate script cost");

            try {
                Result<List<EvaluationResult>> evaluationResult = transactionEvaluator.evaluateTx(transaction.serialize());
                if (!evaluationResult.isSuccessful())
                    throw new TxBuildException("Failed to compute script cost : " + evaluationResult.getResponse());

                List<EvaluationResult> evaluationResults = evaluationResult.getValue();
                for (Redeemer redeemer : transaction.getWitnessSet().getRedeemers()) {
                    for (EvaluationResult evalRes : evaluationResults) {
                        if (redeemer.getIndex().intValue() == evalRes.getIndex() && redeemer.getTag() == evalRes.getRedeemerTag()) {
                            redeemer.getExUnits()
                                    .setMem(evalRes.getExUnits().getMem());
                            redeemer.getExUnits()
                                    .setSteps(evalRes.getExUnits().getSteps());
                            break;
                        }
                    }
                }
            } catch (CborSerializationException | ApiException e) {
                throw new TxBuildException("Failed to compute script cost", e);
            }
        };
    }
}

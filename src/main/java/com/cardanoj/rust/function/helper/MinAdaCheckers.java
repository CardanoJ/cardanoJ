package com.cardanoj.rust.function.helper;

import com.cardanoj.rust.coreapi.MinAdaCalculator;
import com.cardanoj.rust.function.MinAdaChecker;
import com.cardanoj.rust.function.TxBuilderContext;
import com.cardanoj.rust.transaction.spec.TransactionOutput;

import java.math.BigInteger;

/**
 * Provides helper method to get {@link MinAdaChecker} function
 */
public class MinAdaCheckers {

    /**
     * Returns a function which takes two inputs {@link TxBuilderContext} and {@link TransactionOutput} and checks
     * against minimum required Ada to return additional required lovelace in the output
     *
     * @return <code>{@link MinAdaChecker}</code> function
     */
    public static MinAdaChecker minAdaChecker() {
        return (MinAdaCheckers::checkMinAdaRequirement);
    }

    private static BigInteger checkMinAdaRequirement(TxBuilderContext context, TransactionOutput output) {
        MinAdaCalculator minAdaCalculator = new MinAdaCalculator(context.getProtocolParams());
        BigInteger minAda = minAdaCalculator.calculateMinAda(output);

        if (minAda.compareTo(output.getValue().getCoin()) == 1) {
            return minAda.subtract(output.getValue().getCoin());
        } else {
            return BigInteger.ZERO;
        }
    }

}

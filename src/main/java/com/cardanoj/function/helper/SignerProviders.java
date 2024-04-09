package com.cardanoj.function.helper;

import com.cardanoj.coreapi.account.Account;
import com.cardanoj.crypto.SecretKey;
import com.cardanoj.crypto.bip32.HdKeyPair;
import com.cardanoj.function.TxSigner;
import com.cardanoj.transaction.TransactionSigner;
import com.cardanoj.transaction.spec.Policy;
import com.cardanoj.transaction.spec.Transaction;

/**
 * Provides helper methods to get TxSigner function to sign a <code>{@link Transaction}</code> object
 */
public class SignerProviders {

    /**
     * Function to sign a transaction using one or more <code>Account</code>
     * @param signers account(s) to sign the transaction
     * @return <code>TxSigner</code> function which returns a <code>Transaction</code> object with witnesses when invoked
     */
    public static TxSigner signerFrom(Account... signers) {

        return transaction -> {
            Transaction outputTxn = transaction;
            for (Account signer : signers) {
                outputTxn = signer.sign(outputTxn);
            }

            return outputTxn;
        };
    }

    /**
     * Function to sign a transaction with one or more <code>SecretKey</code>
     * @param secretKeys secret keys to sign the transaction
     * @return <code>TxSigner</code> function which returns a <code>Transaction</code> object with witnesses when invoked
     */
    public static TxSigner signerFrom(SecretKey... secretKeys) {

        return transaction -> {
            Transaction outputTxn = transaction;
            for (SecretKey sk : secretKeys) {
                outputTxn = TransactionSigner.INSTANCE.sign(outputTxn, sk);
            }

            return outputTxn;
        };
    }

    /**
     * Function to sign a transaction with one or more <code>Policy</code>
     * @param policies one or more policy
     * @return <code>TxSigner</code> function which returns a <code>Transaction</code> object with witnesses when invoked
     */
    public static TxSigner signerFrom(Policy... policies) {

        return transaction -> {
            Transaction outputTxn = transaction;
            for (Policy policy : policies) {
                for (SecretKey sk : policy.getPolicyKeys()) {
                    outputTxn = TransactionSigner.INSTANCE.sign(outputTxn, sk);
                }
            }

            return outputTxn;
        };
    }

    /**
     * Function to sign a transaction with one or more {@link HdKeyPair}
     * @param hdKeyPairs
     * @return
     */
    public static TxSigner signerFrom(HdKeyPair... hdKeyPairs) {

        return transaction -> {
            Transaction outputTxn = transaction;
            for (HdKeyPair hdKeyPair : hdKeyPairs) {
                outputTxn = TransactionSigner.INSTANCE.sign(outputTxn, hdKeyPair);
            }

            return outputTxn;
        };
    }

    /**
     * Function to sign a transaction with one or more stake key(s) of <code>Account</code>(s)
     * @param signers account(s) to sign the transaction
     * @return <code>TxSigner</code> function which returns a <code>Transaction</code> object with witnesses when invoked
     */
    public static TxSigner stakeKeySignerFrom(Account... signers) {

        return transaction -> {
            Transaction outputTxn = transaction;
            for (Account signer : signers) {
                outputTxn = signer.signWithStakeKey(outputTxn);
            }

            return outputTxn;
        };
    }

    public static TxSigner drepKeySignerFrom(Account... signers) {
        return transaction -> {
            Transaction outputTxn = transaction;
            for (Account signer : signers) {
                outputTxn = signer.signWithDRepKey(outputTxn);
            }

            return outputTxn;
        };
    }
}

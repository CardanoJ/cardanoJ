package org.cardanoJ;

import org.cardanoJ.transaction.BuildTransaction;
import org.cardanoJ.wallet.CreateWallet;

public class Main {
    public static void main(String[] args) {
        CreateWallet createWallet = new CreateWallet();   //Wallet Creation
        createWallet.create();


        BuildTransaction buildTransaction= new BuildTransaction();  // Build Transaction
        buildTransaction.transact();
    }
}

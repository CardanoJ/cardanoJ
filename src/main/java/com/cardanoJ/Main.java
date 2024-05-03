package com.cardanoJ;

import com.cardanoJ.wallet.CreateWallet;
import com.cardanoJ.transaction.BuildTransaction;

public class Main {
    public static void main(String[] args) {
        CreateWallet createWallet = new CreateWallet();   //Wallet Creation
//        createWallet.create();


        BuildTransaction buildTransaction= new BuildTransaction();  // Build Transaction
        buildTransaction.transact();
//        buildTransaction.query();
    }
}

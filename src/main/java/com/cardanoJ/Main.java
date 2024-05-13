package com.cardanoJ;

import com.cardanoJ.transaction.BuildTransaction;

public class Main {
    public static void main(String[] args) {


        BuildTransaction buildTransaction= new BuildTransaction();  // Build Transaction
        buildTransaction.transact();
//        buildTransaction.query();
    }
}

package com.cardanoj;

import com.cardanoj.coreapi.account.Account;
import com.cardanoj.common.model.Networks;


public class AccountGen {

    public static void genAccount() {

//        Networks.mainnet()
//        Account account = new Account(Networks.mainnet());
        Account account = new Account(Networks.testnet());
        String baseAddress = account.baseAddress();
        String mnemonic = account.mnemonic();

        System.out.println("Mnemonics : " + mnemonic);
        System.out.println("Address : " + baseAddress);

    }

    public static void main(String[] args) {
        genAccount();
    }
}

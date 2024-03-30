package com.cardanoj;

import com.cardanoj.backend.api.BackendService;
import com.cardanoj.backendmodule.blockfrost.service.BFBackendService;
import com.cardanoj.backend.api.DefaultUtxoSupplier;
import com.cardanoj.common.Constants;
import com.cardanoj.common.model.Networks;
import com.cardanoj.coreapi.UtxoSupplier;
import com.cardanoj.coreapi.account.Account;

public class Main {

    public static void main(String[] args) {
        //    Creating Account
        Account account = new Account(Networks.testnet());
        String baseAddress = account.baseAddress();
        String mnemonic = account.mnemonic();
        System.out.println(mnemonic);

        //Blockfrost Backend Service
        BackendService backendService =
                new BFBackendService(Constants.BLOCKFROST_PREPROD_URL, "preprodwZOfRrBDvBuzp7rDkfHRlnQ0mQqhXW2i");
    }

}

package org.cardanoJ;

import org.cardanoJ.wallet.CreateWallet;

public class Main {
    public static void main(String[] args) {
        CreateWallet createWallet = new CreateWallet();
        createWallet.create();
        
    }
}

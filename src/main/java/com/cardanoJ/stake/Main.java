package com.cardanoJ.stake;

import static com.cardanoJ.transaction.CardanoJConstant.*;

public class Main {
    public static void main(String[] args) {
        CardanoJRegisterAndDelegate cardanoJRegisterAndDelegate = new CardanoJRegisterAndDelegate();
        String a = cardanoJRegisterAndDelegate.stakeAddress(CLI_PATH);
        System.out.println("user1scriptstake :" + a);
        String b = cardanoJRegisterAndDelegate.stakeAddressBuild(CLI_PATH);
        System.out.println("user1script :" + b);
        String c = cardanoJRegisterAndDelegate.stakeAddressRegCert(CLI_PATH);
        System.out.println("RegCert :" + c);

        CardanoJQueryStakePool cardanoJQueryStakePool = new CardanoJQueryStakePool();
        String first = cardanoJQueryStakePool.queryStakePool(CLI_PATH, SOCKET_PATH);
        System.out.println("Pool :" + first);
        String d = cardanoJRegisterAndDelegate.stakeAddressDelCert(CLI_PATH, first);
        System.out.println("DelCert :" + d);

        String e = cardanoJRegisterAndDelegate.queryProtocolParam(CLI_PATH, SOCKET_PATH);
        System.out.println("QueryPP :" + e);

        String f = cardanoJRegisterAndDelegate.buildTransaction(CLI_PATH, "41f479dbfb62f62b79643389eb85d2807217f6b26c202ea7035682d05a757842#0", SOCKET_PATH);
        System.out.println("Build :" + f);
        String g = cardanoJRegisterAndDelegate.signTransaction(CLI_PATH, SOCKET_PATH);
        System.out.println("Signed :" + g);


        String h = cardanoJRegisterAndDelegate.submitTransaction(CLI_PATH, SOCKET_PATH);
        System.out.println("Submit :" + h);


        //Withdraw

        CardanoJWithdrawUser cardanoJWithdrawUser = new CardanoJWithdrawUser();
        String i = cardanoJWithdrawUser.buildTransaction(CLI_PATH, "7dd2c84807a7e39401e4577a756f0e021c3c17f12e837ec4155ae82b94472b8d#0", SOCKET_PATH);
        System.out.println("Build : " + i);

        String j = cardanoJWithdrawUser.signTransaction(CLI_PATH, SOCKET_PATH);
        System.out.println("Sign : " + j);

        String k = cardanoJWithdrawUser.submitTransaction(CLI_PATH, SOCKET_PATH);
        System.out.println("Build : " + k);


    }
}

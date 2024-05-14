package com.cardanoJ.stake;

import static com.cardanoJ.transaction.Constant.*;
public class Main {
    public static void main(String[] args) {
        RegisterAndDelegate registerAndDelegate = new RegisterAndDelegate();
        String a = registerAndDelegate.stakeAddress(CLI_PATH);
        System.out.println("user1scriptstake :" + a);
        String b = registerAndDelegate.stakeAddressBuild(CLI_PATH);
        System.out.println("user1script :" + b);
        String c = registerAndDelegate.stakeAddressRegCert(CLI_PATH);
        System.out.println("RegCert :" + c);

        QueryStakePool queryStakePool = new QueryStakePool();
        String first = queryStakePool.queryStakePool(CLI_PATH,SOCKET_PATH);
        System.out.println("Pool :"+ first);
        String d = registerAndDelegate.stakeAddressDelCert(CLI_PATH, first);
        System.out.println("DelCert :" + d);

        String e = registerAndDelegate.queryProtocolParam(CLI_PATH, SOCKET_PATH);
        System.out.println("QueryPP :" + e);

        String f = registerAndDelegate.buildTransaction(CLI_PATH, "41f479dbfb62f62b79643389eb85d2807217f6b26c202ea7035682d05a757842#0", SOCKET_PATH);
        System.out.println("Build :" + f);
        String g = registerAndDelegate.signTransaction(CLI_PATH, SOCKET_PATH);
        System.out.println("Signed :" + g);


        String h = registerAndDelegate.submitTransaction(CLI_PATH, SOCKET_PATH);
        System.out.println("Submit :" + h);



        //Withdraw

        WithdrawUser withdrawUser = new WithdrawUser();
        String i = withdrawUser.buildTransaction(CLI_PATH,"7dd2c84807a7e39401e4577a756f0e021c3c17f12e837ec4155ae82b94472b8d#0",SOCKET_PATH);
        System.out.println("Build : " + i);

String j = withdrawUser.signTransaction(CLI_PATH,SOCKET_PATH);
        System.out.println("Sign : " + j);

String k = withdrawUser.submitTransaction(CLI_PATH,SOCKET_PATH);
        System.out.println("Build : " + k);



    }
}

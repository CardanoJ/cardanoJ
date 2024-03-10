package org.cardanoJ.wallet;

import org.cardanoJ.utils.*;

import java.util.Scanner;

import static java.lang.System.exit;

public class CreateWallet {
    public static String cliPath;
    public static String caddrPath;
    public static String os;
    public static String resourcePath;

    public void create(){
        String network ="";
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose the Network. \n1. Testnet-magic \n2. Mainnet \n3. Exit");
        int a = sc.nextInt();
        switch (a){
            case 1:
                network = "testnet";
                break;
            case 2:
                network = "mainnet";
                break;
            case 3:
                exit(0);
            default:
                System.out.println("You Have to choose the network");

        }
        execute(network);
    }


    private void execute(String network) {
        // Initialize cliPath and os
        os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows path
            cliPath = "src/main/resources/bin/cardano-address.exe";
        } else {
            // Unix/Linux/MacOS command
            cliPath = "src/main/resources/bin/cardano-address"; // Assuming the executable for Unix/Linux doesn't have '.exe'
        }
        resourcePath = getResourcePath();


        // Call method to generate and save recovery phrase
        SeedPhraseGenerate seed = new SeedPhraseGenerate();
        seed.setPhrase(cliPath, os,resourcePath);
        RootPrivateKey rpk = new RootPrivateKey();
        rpk.setRootPrivateKey(cliPath, resourcePath + "phrase.prv", resourcePath, os);
        PolicyPrvPub ppp = new PolicyPrvPub();
        ppp.setPubPolicy(cliPath,resourcePath + "root.xsk",resourcePath,os);
//        System.out.println(ppp.getPubPolicy());
        ppp.setPrvPolicy(cliPath,resourcePath + "root.xsk",resourcePath,os);
//        System.out.println(ppp.getPrvPolicy());
        PaymentKey pk = new PaymentKey();
        pk.setKey(cliPath,resourcePath+"root.xsk",resourcePath,os);
//        System.out.println(pk.getKey());
        PaymentAddress pa = new PaymentAddress();
        pa.setAddress(cliPath,resourcePath+"addr.xvk",network, resourcePath, os);
        System.out.println("Address of " + network +" is: "+ pa.getAddress());
    }
    private static String getResourcePath() {
        return CreateWallet.class.getClassLoader().getResource("").getPath();
    }
}

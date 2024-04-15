package com.org.cardano.client.jni;

import com.sun.jna.Pointer;

import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
//      int result = ExampleInterface.INSTANCE.add_numbers(3, 4);
//      System.out.println("Result: " + result);

        String[] mnemonicWords = {
                "insect", "sad", "deal", "defense", "tomorrow", "odor", "uncle", "female", "lamp", "burger",
                "magnet", "chicken", "dash", "chair", "cement", "violin", "van", "good", "attack", "topic",
                "equal", "limb", "prize", "ocean"
        };
        String phrase= String.join(" ", mnemonicWords);
        //String phrase = "";
        int index = 0;
        boolean isTestnet = false;

        Pointer result = JNAInterface.INSTANCE.getBaseAddress(phrase, index, isTestnet);
        Pointer byNnet = JNAInterface.INSTANCE.getBaseAddressByNetwork(phrase, 1, Networks.testnet());

        // Convert the result to a String
        String address = result.getString(0);
        System.out.println("Main Address: " + result.getString(0));
        System.out.println("Test Address: " + byNnet.getString(0));

        Pointer privateKey = JNAInterface.INSTANCE.getPrivateKeyFromMnemonic(phrase,index);
        System.out.println("Private Key: " + privateKey.getString(0));

        Pointer sign = JNAInterface.INSTANCE.sign("01d8d23cb0f8954e1c3c8c487983294d6b31d7d09eb4e16169121b573c75e7e4",privateKey.getString(0));
        System.out.println("Sign: " + sign.getString(0));
//addr_test1qp0gkefrqvtumqmnrcx3y0n7h5n88gx3huxnmmaavh0q55la7na6zfexdssu39huq9926avf63x8u966kv4kcqkdlqzqrz4eg6
    }
}

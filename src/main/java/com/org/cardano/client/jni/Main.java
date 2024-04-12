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

        // Convert the result to a String
        String address = result.getString(0);
        System.out.println("Base Address: " + address);

    }
}

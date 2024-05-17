package com.cardanoJ.transaction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CardanoJGenerateUnitJSON {
    public String gen(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        byte[] result = md.digest(data.getBytes());

        // Convert byte array to hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte b : result) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String result = new CardanoJGenerateUnitJSON().gen("Hello");
        System.out.println(new CardanoJGenerateUnitJSON().gen("Hello world"));
        System.out.println(result);
    }
}


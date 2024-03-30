package com.cardanoj.util;


import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class StringUtils {
    public StringUtils() {
    }

    public static String[] splitStringEveryNCharacters(String text, int n) {
        return text.split("(?<=\\G.{" + n + "})");
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isUtf8String(byte[] bytes) {
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

        try {
            decoder.decode(ByteBuffer.wrap(bytes));
            return true;
        } catch (CharacterCodingException var3) {
            return false;
        }
    }
}

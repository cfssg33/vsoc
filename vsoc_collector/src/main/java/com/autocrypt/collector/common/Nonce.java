package com.autocrypt.collector.common;

import java.security.SecureRandom;
import java.util.Locale;

public class Nonce {
    public static final String UPPER_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_ALPHABET = UPPER_ALPHABET.toLowerCase(Locale.ROOT);
    public static final String DIGITS = "0123456789";
    public static final String ALPHANUM = UPPER_ALPHABET + LOWER_ALPHABET + DIGITS;

    // Generate Nonce for Chang An Open API
    public static String generate(int length, SecureRandom rnd, String symbols) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(symbols.charAt(rnd.nextInt(symbols.length())));
        }
        return sb.toString();
    }
}

package main.java.models;

import java.math.BigInteger;
import java.security.SecureRandom;
public class SecureString {

    private static SecureRandom random = new SecureRandom();

    public static String get(int size) {
        return new BigInteger(size*5, random).toString(32);
    }
}

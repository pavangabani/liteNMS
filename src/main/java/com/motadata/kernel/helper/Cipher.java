package com.motadata.kernel.helper;

import java.util.Base64;

public class Cipher {

    public static String encode(String target){

        return Base64.getEncoder().encodeToString(target.getBytes());

    }

    public static String decode(String target){

        byte[] passwordBytes = Base64.getDecoder().decode(target);

        return new String(passwordBytes);
    }
}

package com.motadata.kernel.helper;

import java.util.Base64;

public class Cipher
{
    public static String encode(String target)
    {
        String encoded = null;

        try
        {
            encoded = Base64.getEncoder().encodeToString(target.getBytes());

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return encoded;
    }

    public static String decode(String target)
    {
        String decoded = null;

        try
        {
            byte[] passwordBytes = Base64.getDecoder().decode(target);

            decoded = new String(passwordBytes);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return decoded;
    }
}

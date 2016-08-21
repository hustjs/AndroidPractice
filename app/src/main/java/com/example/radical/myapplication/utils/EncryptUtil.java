package com.example.radical.myapplication.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Title.
 * <p/>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2016-08-03
 */
public class EncryptUtil {
    public static String md5(String string)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}

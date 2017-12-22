package com.gmail.rashjohar0007.securenotepad.basic;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography {
    public static String ConvertByteArraytoString(byte[] code) {
        return Base64.encodeToString(code,Base64.DEFAULT);
    }
    public static byte[] ConvertStringtoByteArray(String code) {
        return Base64.decode(code,Base64.DEFAULT);
    }
    public static String ConvertKeyToString(SecretKey key) {
        return ConvertByteArraytoString(key.getEncoded());
    }
    public static SecretKey ConvertStringToKey(String data) {
        SecretKey ke= new SecretKeySpec(ConvertStringtoByteArray(data),"AES");
        return ke;
    }
    public static SecretKey generateKey(String password) {
        return new SecretKeySpec(password.getBytes(), "AES");
    }
    public static String Encrypt(SecretKey key, String data){
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {

        } catch (NoSuchPaddingException e) {

        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {

        }
        byte[] cipherText = new byte[0];
        try {
            cipherText = cipher.doFinal(data.getBytes("UTF-8"));
        } catch (IllegalBlockSizeException e) {

        } catch (BadPaddingException e) {

        } catch (UnsupportedEncodingException e) {

        }
        String encryptedValue = ConvertByteArraytoString(cipherText);
        return encryptedValue;
    }
    public static String Decrypt(SecretKey key, String data) {
        byte[] cipherText=ConvertStringtoByteArray(data);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {

        } catch (NoSuchPaddingException e) {

        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException e) {

        }
        String decryptString = null;
        try {
            decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        } catch (UnsupportedEncodingException e) {

        } catch (IllegalBlockSizeException e) {

        } catch (BadPaddingException e) {

        }
        return decryptString;
    }
}
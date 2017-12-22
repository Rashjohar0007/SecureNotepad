package com.gmail.rashjohar0007.securenotepad.basic;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography implements IConstants{
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
    public static SecretKey generateKey(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecureRandom random=new SecureRandom();
        byte[] asl=new byte[16];
        random.nextBytes(asl);
        KeySpec rt=new PBEKeySpec(password.toCharArray(),asl,65536,256);
        SecretKeyFactory f=SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKeySpec mykey=f.generateSecret(rt);
        return new SecretKeySpec(key, "AES");
    }
    public static String Encrypt(SecretKey key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = new byte[0];
        cipherText = cipher.doFinal(ConvertStringtoByteArray(data));
        String encryptedValue = ConvertByteArraytoString(cipherText);
        return encryptedValue;
    }
    public static String Decrypt(SecretKey key, String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        byte[] cipherText=ConvertStringtoByteArray(data);
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        String decryptString = null;
        decryptString =ConvertByteArraytoString(cipher.doFinal(cipherText));
        return decryptString;
    }
}
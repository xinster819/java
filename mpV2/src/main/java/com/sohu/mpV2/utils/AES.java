package com.sohu.mpV2.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AES {

    static Logger LOGGER = LoggerFactory.getLogger(AES.class);

    private static final String ALGORITHM = "AES";
    private static Key KEY = new SecretKeySpec("ASDKJFH2390R7326".getBytes(), ALGORITHM);

    public static String encrypt(String value) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, KEY);
            byte[] encValue = c.doFinal(value.getBytes());
            byte[] encryptedByteValue = new Base64().encode(encValue);
            return new String(encryptedByteValue);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("encrypt failed. value = {}", value, e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("encrypt failed. value = {}", value, e);
        } catch (InvalidKeyException e) {
            LOGGER.error("encrypt failed. value = {}", value, e);
        } catch (IllegalBlockSizeException e) {
            LOGGER.error("encrypt failed. value = {}", value, e);
        } catch (BadPaddingException e) {
            LOGGER.error("encrypt failed. value = {}", value, e);
        }
        return null;
    }

    public static String decrypt(String value) {
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, KEY);
            byte[] decodedValue = new Base64().decode(value.getBytes());
            byte[] decryptedVal;
            decryptedVal = c.doFinal(decodedValue);
            return new String(decryptedVal);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            LOGGER.error("decrypt failed. value = {}", value, e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("decrypt failed. value = {}", value, e);
        } catch (NoSuchPaddingException e) {
            LOGGER.error("decrypt failed. value = {}", value, e);
        } catch (InvalidKeyException e) {
            LOGGER.error("decrypt failed. value = {}", value, e);
        }
        return null;
    }
}

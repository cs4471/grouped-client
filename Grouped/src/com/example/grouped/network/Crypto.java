package com.example.grouped.network;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    private final boolean USE_ENCRYPTION = true;
    private SecretKey key;
    private String keyStr;
    private final Cipher cipher;

    public Crypto() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    public String encrypt(String unencrypted) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        if (this.USE_ENCRYPTION) {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.key);
            return new String(Base64Coder.encode(this.cipher
                    .doFinal(unencrypted.getBytes())));
        } else {
            return unencrypted;
        }
    }

    public String decrypt(String encrypted) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        if (this.USE_ENCRYPTION) {
            this.cipher.init(Cipher.DECRYPT_MODE, this.key);
            return new String(
                    this.cipher.doFinal(Base64Coder.decode(encrypted)));
        } else {
            return encrypted;
        }
    }

    public void setKey(String strKey) {
        this.keyStr = strKey;
        byte[] encodedKey = Base64Coder.decode(strKey);
        this.key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    public String getKey() {
        return this.keyStr;
    }

    public String generateKey() throws NoSuchAlgorithmException {
        if (this.key == null) {
            // Generate a 256-bit key
            final int outputKeyLength = 128;

            SecureRandom secureRandom = new SecureRandom();
            // Do *not* seed secureRandom! Automatically seeded from system
            // entropy.
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(outputKeyLength, secureRandom);
            this.key = keyGenerator.generateKey();
            this.keyStr = new String(Base64Coder.encode(this.key.getEncoded()));
        }
        return this.keyStr;
    }

    public static void main(String[] args) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException,
            NoSuchAlgorithmException, NoSuchPaddingException {

        Crypto c1 = new Crypto();
        Crypto c2 = new Crypto();
        String test1 = "testInput";
        String test2 = "abcdefghijklmnop\tqrstufwxyz\nABCDEFGHIJKLM\tNOPQRSTUVWXYZ";

        c1.generateKey();
        String key = c1.getKey();
        System.out.println("Key1: " + key);

        c2.setKey(key);
        System.out.println("Key2: " + c2.getKey());

        String e1 = c1.encrypt(test1);
        String e2 = c2.encrypt(test1);
        System.out.println("Encrypted: " + e1 + ", " + e2);

        String d1 = c1.decrypt(e2);
        String d2 = c2.decrypt(e1);
        System.out.println("Decrypted: " + d1 + ", " + d2);

        String e3 = c1.encrypt(test2);
        String e4 = c2.encrypt(test2);
        System.out.println("\n\nEncrypted: \n" + e3 + "\n" + e4);

        String d3 = c2.decrypt(e3);
        String d4 = c1.decrypt(e4);
        System.out.println("\nDecrypted: \n" + d3 + "\n" + d4);

    }
}
package org.owasp.psafix.devsec.cryptodemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class CipherUtilSecret {

    private final Logger log = LoggerFactory.getLogger(CipherUtilSecret.class);

    public static final String CIPHER_ALGORITHM = "AES";
    public static final String KEY_ALGORITHM = "AES";
    public static final byte[] SECRET_KEY = "16BYTESSECRETKEY" .getBytes(StandardCharsets.UTF_8); // exactly 16 bytes to not use JCE (Java Cryptography Extension)

    public String decrypt(String encryptedInput) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
            return new String(cipher.doFinal(Base64Utils.decodeFromUrlSafeString(encryptedInput)), StandardCharsets.UTF_8);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String str) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET_KEY, KEY_ALGORITHM));
            return Base64Utils.encodeToUrlSafeString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        CipherUtilSecret cipherUtil = new CipherUtilSecret();
        // Encryption
        String encryptedString = cipherUtil.encrypt("1,2,3 allons dans les bois : " + String.valueOf(new Date().getTime()));
        // Before Decryption
        System.out.println("Avant déchiffrement : " + encryptedString);
        String s = cipherUtil.decrypt(encryptedString);
        System.out.println("Après déchiffrement : " + s);
    }
}
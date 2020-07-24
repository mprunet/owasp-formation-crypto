package org.owasp.psafix.devsec.cryptodemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

@Component
public class CipherUtilSecretAes {

    private final Logger log = LoggerFactory.getLogger(CipherUtilSecretAes.class);

    public static final String CIPHER_ALGORITHM = "AES";
    public static final String KEY_ALGORITHM = "AES";
    private SecretKey secretKey;

    @Autowired
    KeyStorage keyStorage;

    @PostConstruct
    public void init() throws Exception {
/*        KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
        keygen.init(128);
        secretKey = keygen.generateKey();*/
        secretKey = keyStorage.getKey(KeyStorage.AES_KEY_ALIAS);
    }


    public byte[] decrypt(byte[] encryptedInput) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(encryptedInput);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public byte[] encrypt(byte[] str) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(str);

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


}
package org.owasp.psafix.devsec.cryptodemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class CipherUtilSecretAesCbcFixedIV {

    private final Logger log = LoggerFactory.getLogger(CipherUtilSecretAesCbcFixedIV.class);

    public static final String CIPHER_ALGORITHM = "AES/CBC/Pkcs5Padding";
    public static final String KEY_ALGORITHM = "AES";
    private SecretKey secretKey;
    private byte[] iv;

    @Autowired
    KeyStorage keyStorage;

    @PostConstruct
    public void init() throws Exception {
/*        KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
        keygen.init(128);
        secretKey = keygen.generateKey();*/
        secretKey = keyStorage.getKey(KeyStorage.AES_CBC_KEY_ALIAS);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        iv = new byte[16];
        random.nextBytes(iv);
    }


    public byte[] uncipher(byte[] toDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey,new IvParameterSpec(iv));
        return cipher.doFinal(toDecrypt, 0, toDecrypt.length);
    }

    public byte[] cipher(byte[] toEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(iv));
        byte[] token = cipher.doFinal(toEncrypt);

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        bos2.write(token);
        return bos2.toByteArray();
    }


}
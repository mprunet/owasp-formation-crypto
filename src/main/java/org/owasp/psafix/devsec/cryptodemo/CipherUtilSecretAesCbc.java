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
import java.security.*;

@Component
public class CipherUtilSecretAesCbc {

    private final Logger log = LoggerFactory.getLogger(CipherUtilSecretAesCbc.class);

    public static final String CIPHER_ALGORITHM = "AES/CBC/Pkcs5Padding";
    public static final String KEY_ALGORITHM = "AES";
    private SecretKey secretKey;

    @Autowired
    KeyStorage keyStorage;

    @PostConstruct
    public void init() throws Exception {
/*        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        keygen.init(256, new SecureRandom());
        SecretKey secretKey = keygen.generateKey();

/*        KeyGenerator keygen = KeyGenerator.getInstance(KEY_ALGORITHM);
        keygen.init(128);
        secretKey = keygen.generateKey();*/
        this.secretKey = keyStorage.getKey(KeyStorage.AES_CBC_KEY_ALIAS);
    }


    public byte[] uncipher(byte[] toDecrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey,new IvParameterSpec(toDecrypt, 0, cipher.getBlockSize()));
        return cipher.doFinal(toDecrypt, cipher.getBlockSize(), toDecrypt.length - cipher.getBlockSize());
    }

    public byte[] cipher(byte[] toEncrypt) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] iv = new byte[cipher.getBlockSize()];
        random.nextBytes(iv);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(iv));

        byte[] token = cipher.doFinal(toEncrypt);

        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        bos2.write(iv);
        bos2.write(token);
        return bos2.toByteArray();
    }


}
package org.owasp.psafix.devsec.cryptodemo;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Service
public class KeyStorage {
    private final static Path keystorePath = Paths.get("keystore.pkcs12");
    private final static char[] KS_PWD = "adbsjiYSG83OPskHEYfshodnwJHvC7954DCIOS203".toCharArray();
    private final static char[] KEY_PWD = "IA0ks284xbqpdicHDBXKQAHXWVDOZ7V1JDOEUXBOJa".toCharArray();
    public static final String AES_CBC_KEY_ALIAS = "AES-CBC";
    public static final String AES_KEY_ALIAS = "AES";
    public static final String AES_GCM_KEY_ALIAS = "AES-GCM";
    public static final String HMAC_KEY_ALIAS = "HMAC";

    private KeyStore keyStore;

    public SecretKey getKey(String alias) throws Exception{
        KeyStore.SecretKeyEntry e = (KeyStore.SecretKeyEntry)keyStore.getEntry(alias, new KeyStore.PasswordProtection(KEY_PWD));
        return e.getSecretKey();
    }

    @PostConstruct
    public void init() throws Exception{
        if (!Files.exists(keystorePath)) {
            generateKeys();
        }
        keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream is = Files.newInputStream(keystorePath)) {
            keyStore.load(is,KS_PWD);
        }
    }

    public void generateKeys() throws Exception{
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(null,null);
        generateKey(ks, AES_KEY_ALIAS,"AES", 128);
        generateKey(ks, AES_CBC_KEY_ALIAS,"AES", 128);
        generateKey(ks, AES_GCM_KEY_ALIAS,"AES", 128);
        generateKey(ks, HMAC_KEY_ALIAS,"HmacSHA256", 256);
        try (OutputStream os = Files.newOutputStream(keystorePath)) {
            ks.store(os, KS_PWD);
        }

    }

    private void generateKey(KeyStore ks, String alias, String algorithm, int keySize) throws NoSuchAlgorithmException, KeyStoreException {

        //SecureRandom random = SecureRandom.getInstanceStrong();
        SecureRandom random = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(keySize, random);
        SecretKey sk = keyGenerator.generateKey();
        ks.setEntry(alias, new KeyStore.SecretKeyEntry(sk), new KeyStore.PasswordProtection(KEY_PWD));
    }
}

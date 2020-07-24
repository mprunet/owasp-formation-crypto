package org.owasp.psafix.devsec.cryptodemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/encrypt")
public class EncryptController {

    @Autowired
    private CipherUtilSecretAes cipher;

    @Autowired
    private CipherUtilSecretAesCbc cipherCbc;

    @Autowired
    private CipherUtilSecretAesGcm cipherGcm;

    @Autowired
    private CipherUtilSecretAesCbcFixedIV cipherCbcFixedIV;

    @Autowired
    private CipherUtilSecretAesGcmFixedIV cipherGcmFixedIV;

    @PostMapping("aes-cbc")
    public byte[] encryptCBC(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherCbc.cipher(file.getBytes());
    }

    @PostMapping("aes-gcm")
    public byte[] encryptGCM(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherGcm.cipher(file.getBytes());
    }

    @PostMapping("aes-cbc-fixed-iv")
    public byte[] encryptCBCFixedIV(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherCbcFixedIV.cipher(file.getBytes());
    }

    @PostMapping("aes-gcm-fixed-iv")
    public byte[] encryptGCMFixedIV(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherGcmFixedIV.cipher(file.getBytes());
    }

}

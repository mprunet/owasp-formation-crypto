package org.owasp.psafix.devsec.cryptodemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/decrypt")
public class DecryptController {

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
    public byte[] decryptCBC(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherCbc.uncipher(file.getBytes());
    }

    @PostMapping("aes-gcm")
    public byte[] decryptGCM(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherGcm.uncipher(file.getBytes());
    }

    @PostMapping("aes-cbc-fixed-iv")
    public byte[] decryptCBCFixedIV(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherCbcFixedIV.uncipher(file.getBytes());
    }

    @PostMapping("aes-gcm-fixed-iv")
    public byte[] decryptGCMFixedIV(@RequestParam("file") MultipartFile file) throws Exception{
        return cipherGcmFixedIV.uncipher(file.getBytes());
    }

}

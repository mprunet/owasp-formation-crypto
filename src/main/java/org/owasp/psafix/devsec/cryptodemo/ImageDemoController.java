package org.owasp.psafix.devsec.cryptodemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/encrypt")
public class ImageDemoController {

    @Autowired
    private CipherUtilSecretAes cipher;

    @PostMapping("aes")
    public byte[] encrypt(@RequestParam("file") MultipartFile file) throws Exception{
        return cipher.encrypt(file.getBytes());
    }

    @PostMapping(path = "bmp-retriever", produces = "image/bmp")
    public byte[] forceBMP(@RequestParam("file") MultipartFile file) throws Exception{

        byte[] bmpHeader = {0x42,0x4d,(byte)0xee,(byte)0xf6,0x29,0x00,0x00,0x00,0x00,0x00,
                0x36,0x00,0x00,0x00,0x28,0x00,0x00,0x00,(byte)0x8c,0x02,0x00,0x00,0x7e,0x05,
                0x00,0x00,0x01,0x00,0x18,0x00,0x00,0x00,0x00,0x00,(byte)0xb8,(byte)0xf6,0x29,0x01,
                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        byte[] content = file.getBytes();
        System.arraycopy(bmpHeader, 0, content, 0, bmpHeader.length);
        return content;
    }

}

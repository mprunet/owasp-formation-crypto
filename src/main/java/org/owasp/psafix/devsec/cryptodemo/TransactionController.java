package org.owasp.psafix.devsec.cryptodemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
public class TransactionController {

    @Autowired
    CipherUtilSecretAesCbc cipherCbc;

    @PostMapping(path = "/api/chiffrer-fichier", produces = "text/plain")
    public byte[] archive(@RequestParam("file") MultipartFile file) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             PrintWriter pw = new PrintWriter(bos);
             InputStreamReader isr = new InputStreamReader(file.getInputStream());
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            pw.println(br.readLine()); // Recopy header
            while ((line = br.readLine()) != null) {
                pw.println(Base64Utils.encodeToUrlSafeString(cipherCbc.cipher(line.getBytes(StandardCharsets.UTF_8))));
            }
            pw.flush();
            return bos.toByteArray();
        }
    }

    @PostMapping(path = "/api/record", produces = "text/plain")
    public byte[] record(String transaction) throws IOException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(bos);
        try {
            byte[] uncipher = cipherCbc.uncipher(Base64Utils.decodeFromUrlSafeString(transaction));
            String data = new String(uncipher, StandardCharsets.UTF_8);
            String[] csv = data.split(";");
            if (csv.length == 3) {
                pw.println(String.format("Transfert de %s sur le compte de %s (%s)",csv[0],csv[1],csv[2]));
            } else {
                pw.println("Donnee invalide " + data);
            }
        } catch (Exception ex) {
            ex.printStackTrace(pw);
        }
        pw.flush();
        return bos.toByteArray();
    }
}
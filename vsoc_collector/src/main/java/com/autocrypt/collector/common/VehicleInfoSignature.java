package com.autocrypt.collector.common;

import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class VehicleInfoSignature {

    public static String generate(String url, String parameters, String timestamp, String nonce, String accessToken, String applicationKey) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());

        String request = "GET\n" +
                url + "\n" +
                parameters + "\n" +
                "x-vcs-access-token:" + accessToken + "\n" +
                "x-vcs-nonce:" + nonce + "\n" +
                "x-vcs-timestamp:"+ timestamp + "\n\n" +
                "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        //Hash SHA256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] sha256Hashed = md.digest(request.getBytes(StandardCharsets.UTF_8));

        //HMAC SHA256
        HMac hmac = new HMac(new SHA256Digest());
        final byte[] hmacResult = new byte[hmac.getMacSize()]; // output buffer

        String TBS = "VCS-HMAC-SHA256\n" + timestamp + "\n" + Hex.toHexString(sha256Hashed);
        byte[] tbsBytes = TBS.getBytes(StandardCharsets.UTF_8);

        hmac.init(new KeyParameter(applicationKey.getBytes(StandardCharsets.UTF_8)));
        hmac.update(tbsBytes, 0, tbsBytes.length);
        hmac.doFinal(hmacResult, 0);

        return Hex.toHexString(hmacResult);
    }
}

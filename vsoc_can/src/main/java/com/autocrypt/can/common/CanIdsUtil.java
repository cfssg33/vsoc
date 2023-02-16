package com.autocrypt.can.common;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.UUID;

@Component
public class CanIdsUtil {

    public static String uuid() {
        UUID uuid = UUID.randomUUID();

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return Base64.encodeBase64URLSafeString(bb.array());
    }
    
}
    
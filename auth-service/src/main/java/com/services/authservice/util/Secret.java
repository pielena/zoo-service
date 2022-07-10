package com.services.authservice.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Secret {

    static String generateSecretKey() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String secretKey = new BigInteger(1, bytes).toString(16);
        return secretKey;
    }
}

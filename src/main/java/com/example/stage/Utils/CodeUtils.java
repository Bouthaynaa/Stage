package com.example.stage.Utils;

import java.security.SecureRandom;
import java.util.Base64;

public class CodeUtils {
    private static final int CODE_LENGTH = 6; // Length of the generated code

    public static String generateCode() {
        byte[] randomBytes = new byte[CODE_LENGTH];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

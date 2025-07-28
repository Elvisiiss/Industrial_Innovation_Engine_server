package com.IIE.Industrial_Innovation_Engine_server.tools;

import java.util.UUID;

public class TokenGenerator {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}

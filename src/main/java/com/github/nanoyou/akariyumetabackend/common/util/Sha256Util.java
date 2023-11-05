package com.github.nanoyou.akariyumetabackend.common.util;

import java.util.regex.Pattern;

public final class Sha256Util {
    private static final String SHA256_REGEX = "[a-fA-F0-9]{128}";
    private static final Pattern pattern = Pattern.compile(SHA256_REGEX);
    public static boolean isSha256(String input) {
        if (input == null || input.length() != 128) {
            return false;
        }
        return pattern.matcher(input).matches();
    }
}

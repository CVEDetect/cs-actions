package io.cloudslang.content.jclouds.entities;

/**
 * Created by Mihai Tusa.
 * 6/3/2016.
 */
public enum Architecture {
    I386,
    X86_64;

    public static final String UNSUPPORTED_ARCHITECTURE = "Invalid architecture value. Valid values: i386, x86_64.";

    public static String getValue(String input) throws RuntimeException {
        try {
            return Architecture.valueOf(input.toUpperCase()).toString().toLowerCase();
        } catch (IllegalArgumentException iae) {
            throw new RuntimeException(UNSUPPORTED_ARCHITECTURE);
        }
    }
}

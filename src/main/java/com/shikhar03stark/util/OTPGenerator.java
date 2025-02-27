package com.shikhar03stark.util;

public class OTPGenerator {
    public static String generateOTP() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }
}

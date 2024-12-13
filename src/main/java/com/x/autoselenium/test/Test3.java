package com.x.autoselenium.test;

import java.util.Random;

public class Test3 {
    public static void main(String[] args) {
        int numberOfStrings = 10;
        int minLength = 10;
        int maxLength = 15;

        String[] randomStrings = generateRandomStrings(numberOfStrings, minLength, maxLength);

        for (String str : randomStrings) {
            System.out.println(str);
        }
    }

    public static String[] generateRandomStrings(int count, int minLength, int maxLength) {
        String[] result = new String[count];
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < count; i++) {
            int length = random.nextInt(maxLength - minLength + 1) + minLength;
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < length; j++) {
                char randomChar = characters.charAt(random.nextInt(characters.length()));
                sb.append(randomChar);
            }

            result[i] = sb.toString();
        }
        return result;
    }
}

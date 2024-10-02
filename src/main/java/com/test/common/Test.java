package com.test.common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    static boolean isVowel(String strChar) {
        return strChar.matches("[aeiouy]");
    }

    public static void main(String[] args) {
        String sourceStr = "SarfrazMalik";
        String ch = String.valueOf(sourceStr.charAt(1));
        System.out.println("isVowel: " +isVowel(ch));
        Integer num = 102;
        System.out.println(Integer.reverse(num));

        int[] array = {4, 2, 3,  7, 1, 2, 5, 3, 6};
        Arrays.stream(array).sorted().forEach(System.out::println);

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Sarfraz");
        map.put(2, "Malik");
        map.forEach((k,v) -> System.out.println(k +" : "+ v));
    }
}

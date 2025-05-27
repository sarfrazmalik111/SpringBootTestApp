package com.test.common;

import java.util.*;

public class zTest {

    public static void main(String[] args) {
        List<Integer> numList = Arrays.asList(12, 6, 3, 8, 5, 10);
        List<String> strList = Arrays.asList("Mango", "Apple", "Guaua", "Orange", "Banana");

        Collections.sort(strList);
        System.out.println(strList);

        System.out.println(numList.subList(1,3));

    }

}

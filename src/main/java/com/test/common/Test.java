package com.test.common;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        System.out.println("------------Java-Program-Start-------------");
        int[] array = {4, 2, 3, 2, 1, 5};
        System.out.println("Original Array: ");
        Arrays.stream(array).forEach(a -> System.out.print(a +" "));
        System.out.println("\nSorted Array: ");
        Arrays.stream(array).sorted().forEach(a -> System.out.print(a +" "));

    }
}

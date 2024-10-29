package com.test.common;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class DSATest {

    static boolean isVowel(String strChar) {
        return strChar.matches("[aeiouy]");
    }

    static void printArray(int arr[]) {
        Arrays.stream(arr).forEach(a -> System.out.print(a +" "));
        System.out.println();
    }
    static void print2DArray(int arr[][]) {
        System.out.println(Arrays.deepToString(arr));
    }

    static int linearSearch(int arr[], int x) {
        System.out.println("--------linear-Search-------");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x)
                return i;
        }
        return -1;  // return -1 if the element is not found
    }

    static int binarySearch(int arr[], int item) {
        System.out.println("--------binary-Search-------");
        int lIndex = 0, rIndex = arr.length-1;
        int itemFoundAtIndex = -1;
        while (lIndex <= rIndex) {
            int midIndex = (lIndex + rIndex)/2;
            if(arr[midIndex] == item) {
                itemFoundAtIndex = midIndex;
                break;
            }
            else if(arr[midIndex] > item) {
                rIndex = midIndex - 1;
            } else {
                lIndex = midIndex + 1;
            }
        }
        System.out.println("item-Found-At-Index: "+ itemFoundAtIndex);
        return itemFoundAtIndex;
    }

    static int factorial(int n) {
//        System.out.println("--------factorial-------");
//        int factorialValue = 1;
//        while(n>0) {
//            factorialValue = factorialValue * n;
//            n--;
//        }
//        System.out.println(factorialValue);
//        return factorialValue;

//      Get-Factorial by Recursive call
        if(n>0) {
            return n * factorial(n-1);
        } else {
            return 1;
        }
    }

    static boolean primeNumber(int number) {
        System.out.println("--------prime-Number-------");
        boolean isPrime = IntStream.range(2, number).noneMatch(x -> number%x == 0);
        return isPrime;
    }

    static void swapTwoNumbersWithoutThirdNo(int a, int b) {
        System.out.println("--------swap-Two-Numbers-Without-Third-No-------");
        System.out.println("A: "+ a +", B: "+ b);
        a = a+b;
        b = a-b;
        a = a-b;
        System.out.println("A: "+ a +", B: "+ b);
    }

    static void reverseInteger(int number) {
        System.out.println("--------reverse-Integer-------");
        int reverseNum = 0;
        while(number != 0) {
            int remainder = number % 10;
            number = number/10;
            reverseNum = reverseNum * 10 + remainder;
        }
        System.out.println("Reversed number: " + reverseNum);
    }

    static void reverseArray(int array[]) {
        System.out.println("--------reverse-Array-------");
        for(int x=0; x<array.length/2; x++) {
            int temp = array[x];
            array[x] = array[array.length-1-x];
            array[array.length-1-x] = temp;
        }
        printArray(array);
    }

    static int[] bubbleSortArray(int array[]) {
        System.out.println("--------bubble-Sort-Array-------");
        for(int x=0; x<array.length; x++) {
            for(int y=0; y<array.length-1-x; y++) {
                if(array[y] > array[y+1]) {
                    int temp = array[y];
                    array[y] = array[y+1];
                    array[y+1] = temp;
                }
            }
        }
        printArray(array);
        return array;
    }

    static int[][] bubbleSortArray2D(int array[][]) {
        System.out.println("--------bubble-Sort-Array-2D-------");
        for(int x=0; x<array.length; x++) {
            array[x] = bubbleSortArray(array[x]);
        }
        print2DArray(array);
        return array;
    }

    static int[][] bubbleSortArray2D2(int array[][]) {
        System.out.println("--------bubble-Sort-Array-2D-2-------");
        int myArr[] = new int[array.length * array[0].length];
        int index = 0;
        int rows = array.length;
        int columns = array[0].length;
        for(int x=0; x<rows; x++) {
            for(int y=0; y<columns; y++) {
                myArr[index] = array[x][y];
                index++;
            }
        }
//      OR
//      myArr = Arrays.stream(array).flatMapToInt(s-> Arrays.stream(s)).toArray();
//      myArr = Arrays.stream(myArr).sorted().toArray();
        myArr = bubbleSortArray(myArr);
        index = 0;
        for(int x=0; x<rows; x++) {
            for(int y=0; y<columns; y++) {
                array[x][y] = myArr[index++];
            }
        }
        print2DArray(array);
        return array;
    }

    static int[][] shiftLastItemAtFirstPlaceIn2DArray(int array[][]) {
        System.out.println("--------shift-Last-Item-At-First-Place-In-2DArray-------");
        int index = 0;
        int rows = array.length;
        int columns = array[0].length;
        int lastItem = array[rows-1][columns-1];
        int myArr[] = Arrays.stream(array).flatMapToInt(ar-> Arrays.stream(ar)).toArray();
        for(int x=myArr.length-1; x>0; x--) {
            myArr[x] = myArr[x-1];
        }
        myArr[0] = lastItem;
        for(int x=0; x<rows; x++) {
            for(int y=0; y<columns; y++) {
                array[x][y] = myArr[index++];
            }
        }
        print2DArray(array);
        return array;
    }

    static void fabonacciSeries(int noOfItems) {
        System.out.println("--------fabonacci-Series-------");
        int i=0;
        int arr[] = new int[noOfItems];
        while(i < noOfItems) {
            if(i==0) arr[i] = 0;
            else if(i==1) arr[i] = 1;
            else arr[i] = arr[i-2]+arr[i-1];
            i++;
        }
        printArray(arr);
    }


    static int value(char r) {
        if (r == 'I') return 1;
        if (r == 'V') return 5;
        if (r == 'X') return 10;
        if (r == 'L') return 50;
        if (r == 'C') return 100;
        if (r == 'D') return 500;
        if (r == 'M') return 1000;
        return -1;
    }
    static int convertRomanToInt(String strNo) {
        int total = 0;
        for (int i=0; i<strNo.length(); i++) {
            int s1 = value(strNo.charAt(i));
            if (i+1 < strNo.length()) {
                int s2 = value(strNo.charAt(i+1));
                //comparing the current character from its right character
                if (s1 >= s2) {
                    total = total + s1;
                } else {
                    total = total - s1;
                }
            } else {
                total = total + s1;
            }
        }
        return total;
    }


    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();
//        MyImmutable obj = new MyImmutable(20, "Sarfraz", list);
//        System.out.println(obj.getList()); //[ ]
//        list.add("64");
//        System.out.println(obj.getList());// [ ]

        int[] array = {4, 2, 3, 5, 1};
        int array2[][] = {{2,3,4}, {5,6,1}};
        Arrays.sort(array2, Comparator.comparingInt(a -> a[0]));
        printArray(array);
        print2DArray(array2);

        binarySearch(array, 5);
    }
}

final class MyImmutable{
    private final int number;
    private final String name;
    private final List<String> list;

    public MyImmutable(int number, String name, List<String> list) {
        this.number = number;
        this.name = name;
//        this.list = list; //In this case List will be mutable
        this.list = Arrays.asList(list.toArray(new String[list.size()]));
    }

    public int getNumber() {
        return this.number;
    }
    public String getName() {
        return this.name;
    }
    public List<String> getList(){
        return list;
    }
}

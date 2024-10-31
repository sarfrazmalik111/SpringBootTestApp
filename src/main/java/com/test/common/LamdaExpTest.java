package com.test.common;

import com.test.modal.Student;
import lombok.val;

import java.sql.DriverManager;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LamdaExpTest {

    static void getListUniqueItems(List<Integer> numList) {
//        List<Integer> numList = Arrays.asList(2,5,1,2,7,3,9);
        System.out.println("------------getting-Unique-Items---------------");
        if(numList == null || numList.isEmpty() || numList.stream().count() < 1) {
            return;
        }
        System.out.println("List-Size: "+ numList.size());
        List<Integer> distinctNumList = numList.stream().distinct().collect(Collectors.toList());
        System.out.println(distinctNumList);
        Set<Integer> distinctNumSet = numList.stream().collect(Collectors.toSet());   //Return in ASC Order
        System.out.println(distinctNumSet);
    }

    static void getDuplicateItems(List<Integer> numList) {
        System.out.println("------------getting-Duplicate-Items---------------");
        System.out.println(Collections.frequency(numList, 2));
        System.out.println(numList.contains(2));
        numList.stream().filter(n -> Collections.frequency(numList, n) > 1)
                .collect(Collectors.toSet()).forEach(System.out::println);
    }

    static void getListMaxItem(List<Integer> numList, List<String> strList) {
        System.out.println("------------getting-MAX-Item---------------");
        Integer maxNum = numList.stream().max(Integer::compare).get();
        Integer maxNum2 = numList.stream().max(Comparator.naturalOrder()).get();
        Integer maxNum3 = numList.stream().reduce(Integer::max).get();
        Integer maxNum4 = numList.stream().mapToInt(a -> a).max().getAsInt();
        System.out.println(maxNum);
//      Integer.compare(x, y) = return (x > y) ? 1 : ((x < y) ? -1 : 0);

        String maxStr = strList.stream().max(Comparator.naturalOrder()).get();    //ordering of strings in alphabetical order
//      String maxStr = strList.stream().max(Comparator.comparing(a -> a)).get();
//      String maxStr = strList.stream().max(Comparator.comparing(String::valueOf)).get();
//      String maxStr = strList.stream().reduce((a,b) -> a.compareTo(b) > 0 ? a : b).get();
        System.out.println(maxStr);
    }

    static void getListSortedItems() {
        System.out.println("------------getting-Sorted-Items---------------");
        List<String> strList = Arrays.asList("Mango", "Apple", "Guaua", "Orange", "Banana");
        List sortedStrList = strList.stream().sorted((a,b) -> a.compareTo(b)).collect(Collectors.toList());
        List sortedStrList2 = strList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());  //Same for Number
        List sortedStrList3 = strList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());  //Same for Number
        System.out.println(sortedStrList);
    }

    static void removeStringDuplicateChars(String str) {
        System.out.println("------------getting-Unique-Chars---------------");
        String str2 = str.chars().distinct().collect(
                StringBuilder::new,
                StringBuilder::appendCodePoint,
                StringBuilder::append
        ).toString();
        System.out.println(str2);

        String str3 = Arrays.stream(str.split("")).distinct().collect(Collectors.joining());
        System.out.println(str3);

        String str4 = Arrays.asList(str.split("")).stream().distinct().collect(Collectors.joining());
        System.out.println(str4);
    }

    static Boolean isPrime(Integer number){
        if ( number == 1 ) { return false; }
        return IntStream.range(2, number).noneMatch(a -> number % a == 0 );
//        IntStream.range(2,5).forEach(System.out::println);  //2,3,4
    }

    static void sortStudentByName() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1,"Salman", "Rampur", 23));
        students.add(new Student(2,"Usman", "Roorkee", 25));
        students.add(new Student(3,"Malik", "Naagal", 22));
        students.add(new Student(4,"Abdul", "Rampur", 26));

        System.out.println("--------------Collection-Sorting---------------");
        students.sort(Comparator.comparing(s -> s.getName()));
        students.forEach(System.out::println);

        System.out.println("--------------Stream-Sorting---------------");
        List<Student> list = students.stream()
                .sorted(Comparator.comparingInt(s -> s.getAge()))
                .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("--------------Stream-Sorting-By-Name-And-Age------------");
        List<Student> list2 = students.stream()
                .sorted(Comparator.comparing(Student::getName).thenComparing(Student::getAge))
                .collect(Collectors.toList());
        list2.forEach(System.out::println);

        System.out.println("--------------Find-All-Address-With-More-than-10-Students--------------");
        Map<String, Integer> map = students.stream()
                .collect(Collectors.groupingBy(Student::getAddress, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() > 10)
//                .forEach(entry -> System.out.println(entry.getKey() +" : "+ entry.getValue()));
                .collect(Collectors.toMap(entry->entry.getKey(), entry -> entry.getValue().intValue()));
        map.forEach((k,v) -> System.out.println(k +" : "+ v));
    }

    static void getSecondLargestNum() {
//        list.stream().sorted(Comparator.comparing(Employee::getSalary))
//                .skip(list.size()-2).findFirst().get();

//        list.stream().sorted(Comparator.reverseOrder()).limit(2).skip(1).findFirst().get();
    }

    static void printAllSubStrings(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i + 1; j <= str.length(); j++) {
                System.out.println(str.substring(i, j));
            }
        }
    }

    private void commonAPI() {
        int[] intArray = {4, 2, 3,  7, 1, 2, 5, 3, 6};
        List<Integer> numList = Arrays.asList(2, 5, 1, 2, 7, 3, 9, 9, 9);
        List<String> strList = Arrays.asList("Mango", "Apple", "Guaua", "Orange", "Banana");
        System.out.println(numList);
        String[] sampleArray = strList.toArray(String[]::new);
// anyMatch
        boolean anyMatchFound = numList.stream().anyMatch(n -> n == 5);   //str.equals("Apple")
        System.out.println("anyMatchFound: " + anyMatchFound);
// findFirst
        String firstItem = strList.stream().findFirst().orElse("NA");
        System.out.println("firstItem: " + firstItem);

        int sum = Arrays.stream(intArray).sum();    //IntStream.sum();
        int max = Arrays.stream(intArray).max().getAsInt();
        double average = Arrays.stream(intArray).average().getAsDouble();

        System.out.println("---------AAA------------");
        IntStream.iterate(1, i -> i < 5, i -> i + 1).forEach(System.out::println); //1,2,3,4
        IntStream.iterate(1, i -> i + 1).limit(5).forEach(System.out::println); //1,2,3,4,5
        IntStream.range(0, 10).filter(x -> x % 3 == 0).forEach((x) -> x = x + 2); //No printing
        IntStream.range(0, 10).filter(x -> x % 3 == 0).forEach(System.out::println);    //0,3,6,9

        System.out.println("--------000----------");
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Sarfraz");
        map.put(2, "Malik");
        map.forEach((k,v) -> System.out.println(k +" : "+ v));
    }

    public static void main(String[] args) {
        String desktopPath = System.getProperty("user.home") + "/Desktop/test.txt";
        int[] intArray = {4, 2, 3,  7, 1, 2, 5, 3, 6};
        List<Integer> numList = Arrays.asList(2, 5, 1, 2, 7, 3, 9, 9, 9);
        List<String> strList = Arrays.asList("Mango", "Apple", "Guaua", "Orange", "Banana");

        sortStudentByName();
    }



}


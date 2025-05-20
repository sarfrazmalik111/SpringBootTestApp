package com.test.common;

import com.test.modal.Student;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LamdaExpTest {

    private void commonAPI() {
        int[] intArray = {4, 2, 3,  7, 1, 2, 5, 3, 6};
        List<Integer> numList = Arrays.asList(2, 5, 1, 2, 7, 3, 9, 9, 9);
        List<String> strList = Arrays.asList("Mango", "Apple", "Guaua", "Orange", "Banana");
        System.out.println(numList);
        String[] sampleArray = strList.toArray(String[]::new);

        List<Integer> list = IntStream.of(intArray).boxed().collect(Collectors.toList());
        List<Integer> list2 = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        IntStream intStream = Arrays.stream(intArray);
        Stream<Integer> intStream2 = intStream.boxed(); //Convert into generic Stream of Integer

// findFirst
        String firstItem = strList.stream().findFirst().orElse("NA");
        System.out.println("firstItem: " + firstItem);
// anyMatch
        boolean anyMatchFound = numList.stream().anyMatch(n -> n == 5);   //str.equals("Apple")
        System.out.println("anyMatchFound: " + anyMatchFound);

        int sum = Arrays.stream(intArray).sum();    //IntStream.sum();
        int max = Arrays.stream(intArray).max().getAsInt();
        double average = Arrays.stream(intArray).average().getAsDouble();
//      Map<String, Double> averageSalaries = employees.stream()
//            .collect(Collectors.groupingBy(Employee::getDeptName, Collectors.averagingDouble(Employee::getSalary)))
//            .forEach((k,v) -> System.out.println(k +" : "+ v));;

        System.out.println("---------AAA------------");
        IntStream.iterate(1, i -> i < 5, i -> i + 1).forEach(System.out::println); //1,2,3,4
        IntStream.iterate(1, i -> i + 1).limit(5).forEach(System.out::println); //1,2,3,4,5
        IntStream.range(0, 5).forEach(System.out::println);    //0,1,2,3,4
        IntStream.range(0, 10).filter(x -> x % 3 == 0).forEach((x) -> x = x + 2); //No printing
        IntStream.range(0, 10).filter(x -> x % 3 == 0).forEach(System.out::println);    //0,3,6,9

        strList.stream().distinct().collect(Collectors.toMap(Function.identity(), String::length))
                .forEach((k, v)-> System.out.println(k +" : "+ v));
        strList.stream().distinct().collect(Collectors.toMap(s->s, s->s.length()))
                .forEach((k, v)-> System.out.println(k +" : "+ v));
//      Concat all strings into one
        String str = strList.stream().collect(Collectors.joining());
    }

    static int findGCDOfTwoNumber(int a, int b) {
        int gcd = IntStream.iterate(a, i-> i>1, i-> i-1)    //i-- : post descrement will not work here, use --i or i-1
                .filter(n-> (a%n == 0 && b%n == 0))
                .findFirst().orElse(-1);
        return gcd;
    }

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
        if(numList.contains(2)) {
            System.out.println("frequency(2): " +Collections.frequency(numList, 2));
        }
        numList.stream().filter(n -> Collections.frequency(numList, n) > 1)
                .collect(Collectors.toSet()).forEach(System.out::println);
//      Input: 1,2,3,2,9,9,9
//      Output: 2,9
    }

    static void getListMaxItem(List<Integer> numList, List<String> strList) {
        System.out.println("------------getting-MAX-Item---------------");
        Integer maxNum = numList.stream().max(Integer::compare).get();
        Integer maxNum2 = numList.stream().max(Comparator.naturalOrder()).get();
        Integer maxNum3 = numList.stream().reduce(Integer::max).get();  //Integer::sum
        Integer maxNum4 = numList.stream().mapToInt(a -> a).max().getAsInt();
        System.out.println(maxNum);
//      Integer.compare(x, y) = return (x > y) ? 1 : ((x < y) ? -1 : 0);

        String maxStr = strList.stream().max(Comparator.naturalOrder()).get();    //ordering of strings in alphabetical order
//      String maxStr = strList.stream().max(Comparator.comparing(a -> a)).get();
//      String maxStr = strList.stream().max(Comparator.comparing(String::valueOf)).get();  //Compare value
//      String maxStr = strList.stream().max(Comparator.comparing(String::length)).get();   //Compare length
//      String maxStr = strList.stream().reduce((a,b) -> a.compareTo(b) > 0 ? a : b).get();
        System.out.println(maxStr);
    }

    static void getListSortedItems() {
        System.out.println("------------getting-Sorted-Items---------------");
        List<String> strList = Arrays.asList("Mango", "Apple", "Guaua", "Orange", "Banana");
//        Collections.sort(strList);
        List sortedList = strList.stream().sorted().collect(Collectors.toList());
        strList.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());  //Same for Number
        strList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());  //Same for Number
        strList.stream().sorted((a,b) -> a.compareTo(b)).collect(Collectors.toList());
        System.out.println(sortedList);
//        nums.stream().sorted((a,b) -> a.compareTo(b));    //ASC
//        nums.stream().sorted((a,b) -> b.compareTo(a));    //DESC
//        nums.stream().sorted((a,b) -> a>b ? 1 : (a==b ? 0 : -1))  //ASC
//        nums.stream().sorted((a,b) -> b>a ? 1 : (a==b ? 0 : -1))  //DESC
//        nums.stream().sorted((a,b) -> a>b ? -1 : (a==b ? 0 : 1))  //DESC
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
        return IntStream.range(2, number/2).noneMatch(a -> number % a == 0 );
//        IntStream.range(2,5).forEach(System.out::println);  //2,3,4
    }
    static Integer findFirstPrimeNumber(List<Integer> numList) {
//        boolean isPrimeFound = numList.stream().anyMatch(a -> isPrime(a));
        return numList.stream().filter(a -> isPrime(a)).findFirst().orElse(-1);
    }

    static void sortStudentByName() {
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Salman", "Rampur", 23));
        students.add(new Student(2, "Usman", "Roorkee", 25));
        students.add(new Student(3, "Malik", "Naagal", 22));
        students.add(new Student(4, "Abdul", "Rampur", 26));
//      Collections.sort(students);   //To do this need to implement Comparable interface in Student
//      students.sort(Comparator.comparingInt(Student::getAge));    //It will override the Comparable sorting

        System.out.println("--------------Collection-Sorting---------------");
        students.sort(Comparator.comparing(s -> s.getName()));
        students.forEach(System.out::println);
//      students.stream().sorted(Comparator.comparingInt(Student::getAge).reversed());    //DESC
//      students.stream().sorted(Comparator.comparing(Student::getName).reversed());      //DESC

        System.out.println("--------------Stream-Sorting-By-Name-And-Age--DESC----------");
        students.stream()
                .sorted(Comparator.comparing(Student::getName).thenComparing(Student::getAge))
                .forEach(System.out::println);

        System.out.println("--------------Find-All-Address-With-More-than-10-Students--------------");
        Map<String, Integer> map = students.stream()
                .collect(Collectors.groupingBy(Student::getAddress, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() > 10)
//                .forEach(entry -> System.out.println(entry.getKey() +" : "+ entry.getValue()));
                .collect(Collectors.toMap(entry->entry.getKey(), entry -> entry.getValue().intValue()));
        map.forEach((k,v) -> System.out.println(k +" : "+ v));
    }

    static Integer getSecondLargestNum(List<Integer> list) {
        return list.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().orElse(0);
//      String secondMaxStr = list.stream()
//                .sorted(Comparator.comparingInt(String::length).reversed())
//                .skip(1).findFirst().get();

//        list.stream().sorted(Comparator.comparing(Employee::getSalary))
//                .skip(list.size()-2)
//                .findFirst().get();

//        Get Second MAX salary department-wise
//        employees.stream()
//                .collect(Collectors.groupingBy(Employee::depart,
//                        Collectors.collectingAndThen(Collectors.toList(),
//                              list -> list.stream().sorted(Comparator.comparing(Employee::salary).reversed()).skip(1).findFirst().orElse(null))
//                ))
//                .forEach((k,v) -> System.out.println(k +" : "+ v));

//        Get Highest salary by department
//        employees.stream()
//                .collect(Collectors.groupingBy(Employee::depart, Collectors.maxBy(Comparator.comparing(Employee::salary))))
//                .forEach((k, v) -> System.out.println(k +" : "+ v.get().salary()));


//        sorted(Map.Entry.comparingByValue())
//        sorted(Comparator.comparing(Map.Entry::getValue))

    }

    static void printAllSubStrings(String str) {
        for (int i = 0; i < str.length(); i++) {
            for (int j = i +1; j <= str.length(); j++) {
                System.out.println(str.substring(i, j));
            }
//      OR      for (int j = i; j < str.length(); j++) {
//                System.out.println(str.substring(i, j+1));
//            }
        }
    }

    static void breakListIntoSubList(List<Integer> list) {
        int chunk = (list.size()+1)/2;  //Break list haft-by-half
        final AtomicInteger counter = new AtomicInteger();
        Map<Integer, List<Integer>> map = list.stream().collect(Collectors.groupingBy(s -> counter.getAndIncrement()/chunk));
        System.out.println(map.size());
        System.out.println(map.get(0));
        System.out.println(map.get(1));

//        int chunk = list.size()/2;  //Break list haft-by-half
//        System.out.println(list.subList(0, chunk));
//        System.out.println(list.subList(chunk, list.size()));
    }

    //Get the MAx sum of any sub array from the given array
    static int subArrayMaxSum(int arr[]) {
        int res = arr[0];
        for(int x=1; x < arr.length; x++) {
            int currSum = 0;
            for(int y=x; y < arr.length; y++) {
                currSum = currSum + arr[y];
                res = Math.max(res, currSum);   //max() -> (a >= b) ? a : b;
            }
        }
        System.out.println("Sub Array Max Sum: "+ res);
        return res;
    }

    static void printUnionAndIntersectionOfLists() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);
        System.out.println("Union:->");
        Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .forEach(System.out::println);

        System.out.println("Intersection:->");
        list1.stream().filter(a -> list2.contains(a))
                .forEach(System.out::println);
    }

    static void convertListToMap() {
        List<String> list = Arrays.asList("apple:2", "banana:3", "apple:4");
        list.stream().map(s -> s.split(":"))
            .collect(Collectors.groupingBy(s -> s[0],
                    Collectors.summingInt(s -> Integer.parseInt(s[1]))
            )).forEach((k,v) -> System.out.println(k +" : "+ v));
//.collect(Collectors.groupingBy(s -> s[0],
//      Collectors.mapping(s -> s[1], Collectors.toList())
//  )) ---> Map(String, List<String>)
        //OUTPUT:
        // apple : 6
        // banana : 3
    }

    public static void main(String[] args) {
        String desktopPath = System.getProperty("user.home") + "/Desktop/test.txt";
        int[] intArray = {4, 2, 3,  7, 1, 2, 5, 3, 6};
        List<Integer> numList = Arrays.stream(intArray).boxed().collect(Collectors.toList());
        List<String> strList = Arrays.asList("Mango", "Apple", "Guaua", "Orange", "Banana");

        System.out.println(Math.max(5,3));
        convertListToMap();

    }

}

record Employee(String name, String dept, int age, int salary) {

}

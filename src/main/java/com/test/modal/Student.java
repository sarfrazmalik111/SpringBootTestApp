package com.test.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student implements Comparable {
    int id;
    String name;
    String address;
    int age;

    public String toString() {
        return id+" : "+name+", "+", "+address+", "+age;
    }

    @Override
    public int compareTo(Object o) {
        Student st = (Student) o;
        return name.compareTo(st.name);
//        return Integer.compare(st.age, age);    //DESC
//        Integer age = null;
//        age.compareTo(st.age);    //ASC
    }
}

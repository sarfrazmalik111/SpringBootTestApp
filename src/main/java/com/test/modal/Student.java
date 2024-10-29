package com.test.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    int id;
    String name;
    String address;
    int age;

    public String toString() {
        return id+" : "+name+", "+", "+address+", "+age;
    }
}

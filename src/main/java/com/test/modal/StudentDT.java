package com.test.modal;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StudentDT {
    int id;
    String name;
    String address;
    String category;

    public String toString() {
        return id+" : "+name+", "+", "+address+", "+category;
    }
}

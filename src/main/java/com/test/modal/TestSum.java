package com.test.modal;

@FunctionalInterface
public interface TestSum {
    int sum(int a, int b);
}

//  Functional Interface implementation by Lamda-Exp
//  static void LamdaTest () {
//      TestSum testSum = (a, b) -> a + b;  //Implementation by Lamda-Exp
//      TestSum testSum = (int a, int b) -> a + b; //Here int datatype is optional
//      System.out.println( "Lamda-Result: "+ testSum.sum(12,12));
//  }
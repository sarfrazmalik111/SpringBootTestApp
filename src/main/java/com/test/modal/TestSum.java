package com.test.modal;

public interface TestSum {
    int sum(int a, int b);
}

//  Functional Interface implementation by Lamda-Exp
//  static void LamdaTest () {
//      TestSum testSum = (a, b) -> a + b;  //Implementation by Lamda-Exp
//      System.out.println( "Lamda-Result: "+ testSum.sum(12,12));
//  }
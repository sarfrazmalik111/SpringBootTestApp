package com.test.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculatorTest {

    private int doSum(int a, int b, int c) {
        return a+b+c;
    }

    private int doProduct(int a, int b) {
        return a*b;
    }

    private boolean compareTwoNums(int a, int b) {
        return a == b;
    }

    @Test
    void testSum() {
        int expected = 7;
        int actualVal = this.doSum(1, 2, 3);
        assertThat(actualVal).isEqualTo(expected);
//        Assertions.assertEquals(expected, actualVal);
    }

    @Test
    @Disabled
    void testProduct() {
        int expected = 4;
        int actualVal = this.doProduct(2,2);
        assertThat(actualVal).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "123, 123", "212, 212"
    })
//    @ValueSource(strings = {
//            "abc", "xyz"
//    })
    void testCompareNums(int a, int b) {
        boolean actualResult = this.compareTwoNums(a, b);
        assertThat(actualResult).isTrue();
    }
}

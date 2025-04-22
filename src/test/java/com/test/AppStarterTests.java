package com.test;

import com.test.service.CalculatorTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppStarterTests {

    private CalculatorTest cal = new CalculatorTest();
    @Test
    void contextLoads() {

    }

}

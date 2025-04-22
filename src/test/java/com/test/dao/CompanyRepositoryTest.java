package com.test.dao;

import com.test.modal.CompanyDto;
import com.test.service.CompanyArgumentProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import java.time.LocalDateTime;

@SpringBootTest
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepo;

    @BeforeAll
    static void beforeAll(){
        System.out.println("-------beforeAll--------");
    }
    @AfterAll
    static void afterAll() {
        System.out.println("-------afterAll--------");
    }
    @BeforeEach
    void beforeEach() {
        System.out.println("-------beforeEach--------");
    }
    @AfterEach
    void afterEach(){
        System.out.println("-------afterEach--------");
    }

    @ParameterizedTest
    @ArgumentsSource(CompanyArgumentProvider.class)
    void saveNewCompanies(CompanyDto companyDto) {
        Assertions.assertNotNull(companyRepo.save(companyDto));
    }

    @Test
    void isCompanyExists() {
        boolean actualResult = companyRepo.existsByCompanyName("HCL");
        assertThat(actualResult).isTrue();
    }
}
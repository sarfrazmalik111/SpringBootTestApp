package com.test.service;

import com.test.dao.CompanyRepository;
import com.test.modal.CompanyDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDateTime;

//@SpringBootTest used with @Autowired and @MockBean,
//If you want some data mock and rest not then this must be used
@SpringBootTest
class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;
    @MockBean   //This mockBean will be injected into companyService
    private CompanyRepository companyRepo;

//    @InjectMocks    //It not works with interfaces, It will work only if u want to data mock only
//    private CompanyServiceImpl companyService;
//    @Mock
//    private CompanyRepository companyRepo;
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.initMocks(this);
//    }

    @Test
    void findAllCompanies() {
        companyService.findAllCompanies();
        Mockito.verify(companyRepo).findAll();
    }

    @Test
    void findByName() {
        CompanyDto company = CompanyArgumentProvider.getCompanyFake();
        Mockito.when(companyRepo.findByCompanyName(ArgumentMatchers.anyString())).thenReturn(company);
        CompanyDto companyDto = companyService.findCompanyByName("ABC");
        Assertions.assertNotNull(companyDto);
//        Assertions.assertNotNull(companyDto, "ERROR: Not found");
    }

}
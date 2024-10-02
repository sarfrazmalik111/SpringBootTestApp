package com.test.service;

import com.test.modal.CompanyDto;
import java.util.List;

public interface CompanyService {

    CompanyDto saveCompany(CompanyDto company);
    Boolean deleteById(Long id);
    CompanyDto findCompanyById(Long id);
    List<CompanyDto> findAllCompanies();
}
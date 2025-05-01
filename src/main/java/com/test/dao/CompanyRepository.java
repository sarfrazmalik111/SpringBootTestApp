package com.test.dao;

import com.test.modal.CompanyDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyDto, Long> {

    CompanyDto findByCompanyName(String companyName);
    Boolean existsByCompanyName(String companyName);
}
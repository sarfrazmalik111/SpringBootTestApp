package com.test.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.test.common.MyUtility;
import com.test.dao.CompanyRepository;
import com.test.modal.CompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private MyUtility myUtility;

    public CompanyDto saveCompany(CompanyDto company) {
        company.setCreatedOn(LocalDateTime.now());
        return companyRepository.save(company);
    }

    public Boolean deleteById(Long id) {
        Boolean flag = Boolean.valueOf(false);
        try {
            companyRepository.deleteById(id);
            flag = Boolean.valueOf(true);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return flag;
    }

    public CompanyDto findCompanyById(Long id) {
        CompanyDto companyDto = null;
        Optional<CompanyDto> data = companyRepository.findById(id);
        if (data.isPresent()) {
            companyDto = data.get();
            companyDto.setCreatedOnStr(myUtility.formatLocalDateTimeForUI(companyDto.getCreatedOn()));
        }
        return companyDto;
    }

    public List<CompanyDto> findAllCompanies() {
        List<CompanyDto> companyRecords = new ArrayList<>();
        for(CompanyDto company: companyRepository.findAll()){
            company.setCreatedOnStr(myUtility.formatLocalDateTimeForUI(company.getCreatedOn()));
            companyRecords.add(company);
        }
        return companyRecords;
    }
}
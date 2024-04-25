package com.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.modal.EmployeeDto;

public interface EmployeeTestRepository extends JpaRepository<EmployeeDto, Long> {

}

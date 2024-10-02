package com.test.dao;

import com.test.modal.EmployeeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeDto, Long> {

}

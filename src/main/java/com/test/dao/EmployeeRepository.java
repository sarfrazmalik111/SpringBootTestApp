package com.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.test.modal.EmployeeDetailsDto;

public interface EmployeeRepository extends JpaRepository<EmployeeDetailsDto, Long> {

}

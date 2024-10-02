package com.test.service;

import com.test.modal.EmployeeDto;
import java.util.List;

public interface EmployeeService {

	EmployeeDto saveEmployee(EmployeeDto user);
	EmployeeDto findEmployeeById(Long id);
	List<EmployeeDto> findAllEmployees();
	Boolean deleteById(Long id);

}

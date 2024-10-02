package com.test.service;

import com.test.common.MyUtility;
import com.test.dao.EmployeeRepository;
import com.test.modal.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private MyUtility myUtility;

	public EmployeeDto saveEmployee(EmployeeDto employee) {
		if(employee.getId() == null) employee.setCreatedOn(LocalDateTime.now());
		return employeeRepository.save(employee);
	}

	public EmployeeDto findEmployeeById(Long id) {
		EmployeeDto employee = null;
		Optional<EmployeeDto> list = employeeRepository.findById(id);
		if (list.isPresent()) {
			employee = list.get();
			employee.setCreatedOnStr(myUtility.formatLocalDateTimeForUI(employee.getCreatedOn()));
		}
		return employee;
	}

	public List<EmployeeDto> findAllEmployees() {
		List<EmployeeDto> employees = employeeRepository.findAll();
		for(EmployeeDto employee: employees) {
			employee.setCreatedOnStr(myUtility.formatLocalDateTimeForUI(employee.getCreatedOn()));
		}
		return employees;
	}

	public Boolean deleteById(Long id) {
		Boolean flag = Boolean.valueOf(false);
		try {
			employeeRepository.deleteById(id);
			flag = Boolean.valueOf(true);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return flag;
	}

}

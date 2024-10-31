package com.test.web;

import com.test.common.AppConstants;
import com.test.common.RestResponseUtility;
import com.test.modal.EmployeeDto;
import com.test.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping({ "/api/employees" })
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class EmployeeRestController {

	@Autowired
	EmployeeService employeeService;
	@Autowired
	private RestResponseUtility responseUtility;
	private Logger logger = LoggerFactory.getLogger(EmployeeRestController.class);

	@GetMapping("")
	public ResponseEntity<?> getAllEmployee() {
		logger.info("----------------getAllUser----------------");
		return responseUtility.successResponse(AppConstants.SUCCESS, this.employeeService.findAllEmployees());
	}

	@PostMapping("/save")
	public ResponseEntity saveEmployee(@Valid @RequestBody EmployeeDto employeeData) {
		ResponseEntity respEntity = null;
		logger.info("----------------saveEmployee----------------");
		try {
			EmployeeDto employeeDto = this.employeeService.saveEmployee(employeeData);
			if(employeeDto != null) {
				logger.info("Employee Saved: "+ employeeDto.getId() + " : "+ employeeDto.getEmail());
			}
			respEntity = responseUtility.successResponse(AppConstants.SAVE_EMP_SUCCESS);
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to save Employee details.");
		}
		return respEntity;
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
		ResponseEntity respEntity = null;
		logger.info("----------------getEmployeeById----------------");
		try {
			EmployeeDto employeeDto = this.employeeService.findEmployeeById(id);
			if (employeeDto == null) {
				return responseUtility.ENTITY_NOT_FOUND;
			}
			respEntity = responseUtility.successResponse(AppConstants.SUCCESS, employeeDto);
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to get employee details.");
		}
		return respEntity;
	}

	@GetMapping({ "/delete/{id}" })
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		ResponseEntity respEntity = null;
		logger.info("----------------deleteUser----------------");
		try {
			EmployeeDto employeeDto = this.employeeService.findEmployeeById(id);
			if (employeeDto == null) {
				return responseUtility.ENTITY_NOT_FOUND;
			}
			if(!this.employeeService.deleteById(id)) {
				return responseUtility.SOMETHING_WENT_WRONG;
			}
			respEntity = responseUtility.successResponse(AppConstants.DELETE_SUCCESS);
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to delete employee details.");
		}
		return respEntity;
	}

}

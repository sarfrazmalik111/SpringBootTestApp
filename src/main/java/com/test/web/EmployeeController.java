package com.test.web;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dao.EmployeeRepository;
import com.test.dao.EmployeeTestRepository;
import com.test.modal.EmployeeDto;
import com.test.modal.EmployeeDetailsDto;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeTestRepository testRepo;
	
	@GetMapping({ "/welcome" })
	public String welcome() {
		return "Welcome to RechargeNow Application";
	}
	@PostMapping("/login")
	public String login2(@RequestBody EmployeeDetailsDto user) {
		System.out.println("------login-----");
		System.out.println(user.getEmail());
		System.out.println(user.getPassword());
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", 1);
		jsonResponse.put("message", "Success");
		return jsonResponse.toString();
	}
	
	@GetMapping("")
	public List<EmployeeDetailsDto> getAllEmployees() {
		System.out.println("------getAllEmployees-----");
		return employeeRepository.findAll();
	}

	@GetMapping("/find/{id}")
	public Optional<EmployeeDetailsDto> getEmployeeById(@PathVariable("id") Long employeeId) {
		return employeeRepository.findById(employeeId);
	}

	@PostMapping("/add")
	public EmployeeDetailsDto addEmployee(@Valid @RequestBody EmployeeDetailsDto employee) {
		System.out.println("------addEmployee-----");
		return employeeRepository.save(employee);
	}

	@PutMapping("/update")
	public EmployeeDetailsDto updateEmployee(@Valid @RequestBody EmployeeDetailsDto employee) {
		System.out.println("------updateEmployee-----");
		return employeeRepository.save(employee);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id") Long employeeId) {
		System.out.println("------deleteEmployee-----");
		employeeRepository.deleteById(employeeId);
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", 0);
		jsonResponse.put("message", "Success");
		return jsonResponse.toString();
	}
	
//	======================================================================================
	@GetMapping("/get-all")
	public List<EmployeeDto> findEmployeeList() {
		System.out.println("------findEmployeeList-----");
		return testRepo.findAll();
	}
	
	@PostMapping("/save-employee")
	public EmployeeDto saveEmployee(@Valid @RequestBody EmployeeDto employee) {
		System.out.println("------saveEmployee-----");
		return testRepo.save(employee);
	}

	@DeleteMapping("/delete-employee/{id}")
	public String deleteEmployee2(@PathVariable("id") Long id) {
		System.out.println("------deleteEmployee-----");
		testRepo.deleteById(id);
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.put("status", 0);
		jsonResponse.put("message", "Success");
		return jsonResponse.toString();
	}
}

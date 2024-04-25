package com.test.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dao.UserJdbcTemplate;
import com.test.modal.AppUserDto;

@RestController
@RequestMapping({ "/rest/jdbc" })
public class JdbcRestController {

	@Autowired
	private UserJdbcTemplate jdbcTemplate;

	@GetMapping({ "/insert-user" })
	public String index() {
		this.jdbcTemplate.add();
		return "Data Inserted Successfully";
	}

	@GetMapping({ "/get-users" })
	public List<AppUserDto> getUsers() {
		return this.jdbcTemplate.getUsers();
	}

	@GetMapping({ "/get-user-details/{id}" })
	public AppUserDto getUserById(@PathVariable int id) {
		return this.jdbcTemplate.getUserById(id);
	}
}

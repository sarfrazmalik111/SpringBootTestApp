package com.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.test.dao.AppJdbcTemplate;
import org.springframework.web.client.RestTemplate;

@Controller
public class HomeController {

	@Autowired
	private AppJdbcTemplate jdbcTemplate;
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("")
	public String homePage(Model model) {
		jdbcTemplate.dissable_ONLY_FULL_GROUP_BY();
		System.out.println("----------homePage------------");
		return "index";
	}
	
}

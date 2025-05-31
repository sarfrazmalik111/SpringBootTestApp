package com.test.web;

import com.test.common.AppConstants;
import com.test.dao.TestJdbcTemplate;
import com.test.modal.AppUserDto;
import com.test.modal.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

	@Autowired
	private TestJdbcTemplate jdbcTemplate;

	private String indexPage = "index";
	private String pageNotFoundPage = "pages/common/pageNotFound";

	@GetMapping({"", "/home"})
	public String homePage(Model model) {
		System.out.println("----------homePage------------");
		model.addAttribute("myName", "Sarfraz Malik");
		return indexPage;
	}

	@GetMapping("/page-not-found")
	public String pageNotFound(Model model) {
		System.out.println("----------pageNotFound------------");
		model.addAttribute("myName", "Sarfraz Malik");
		return pageNotFoundPage;
	}

	@GetMapping("/git-success")
	@ResponseBody
	public String gitSuccess(@RequestParam Map<String, String> map, ModelMap modelMap) {
		System.out.println("----------Git-Success------------");
		System.out.println(map);
		System.out.println(map.get("code"));
		System.out.println(modelMap);
		return AppConstants.SUCCESS;
	}

//================================================================================
//	http://localhost:8080/get-map?name=Sarfraz&address=Naagal&code=123456
	@GetMapping("/get-map")
	@ResponseBody
	public Map<String, String> getMap(@RequestParam Map<String, String> map) {
		System.out.println("----------Get-Map------------");
		System.out.println(map);
		return map;
	}

	@PostMapping("/get-map")
	@ResponseBody
	public Map<String, String> postMap(@RequestBody Map<String, String> map) {
		System.out.println("----------Post-Map------------");
		System.out.println(map);
		return map;
	}

	@GetMapping("/test-data")
	@ResponseBody
	public String getTestDataIn() {
		System.out.println("----------Get-Data-List------------");
		List<AppUserDto> list = jdbcTemplate.getAllAppUserList();
		System.out.println(list.size());
		list.stream().forEach(System.out::println);

		return AppConstants.SUCCESS;
	}


}

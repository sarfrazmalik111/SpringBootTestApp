package com.test.web;

import com.test.common.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class HomeController {

	private String indexPage = "index";
	private String pageNotFoundPage = "pages/common/pageNotFound";

	@GetMapping("/home")
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

}

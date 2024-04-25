package com.test.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.common.AppConstants;
import com.test.modal.AppUserDto;
import com.test.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({ "/users" })
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private HttpSession session;
	private String usersPage = "pages/users/users";
	private String addEditUserPage = "pages/users/user-addEdit";
	private String userDetailsPage = "pages/users/user-details";

	@RequestMapping("")
	public String getAllUser(Model model) {
		System.out.println("----getAllUser-----");
		model.addAttribute("users", this.userService.findAllUsers());
		session.setAttribute(AppConstants.MENU_ITEM, AppConstants.MENU_USERS);
		return usersPage;
	}

	@GetMapping({ "/add", "/save" })
	public String add(Model model, AppUserDto appUser) {
		model.addAttribute("appUser", appUser);
		return addEditUserPage;
	}

	@GetMapping({ "/update/{id}" })
	public String update(@PathVariable Long id, Model model) {
		model.addAttribute("appUser", this.userService.findUserById(id));
		return addEditUserPage;
	}

	@PostMapping({ "/save" })
	public String save(@Valid @ModelAttribute("appUser") AppUserDto appUser, BindingResult bindingResult, RedirectAttributes redAtt) {
		if (bindingResult.hasErrors())
			return addEditUserPage;
		this.userService.saveUser(appUser);
		redAtt.addFlashAttribute(AppConstants.ALERT_SUCCESS, "User details saved successfully!");
		return "redirect:/users";
	}

	@GetMapping({ "/find/{id}" })
	public String findUser(@PathVariable Long id, Model model) {
		model.addAttribute("appUser", this.userService.findUserById(id));
		return userDetailsPage;
	}

	@RequestMapping({ "/delete/{id}" })
	public String deleteUser(@PathVariable Long id, RedirectAttributes redAtt) {
		this.userService.deleteById(id);
		redAtt.addFlashAttribute(AppConstants.ALERT_ERROR, "User deleted successfully!");
		return "redirect:/users";
	}
	
	//============================================================================
	//=================================== PAGGING ================================
	//============================================================================
	@ResponseBody
	@RequestMapping(value = { "/getUsers" })
	public String getAppUsersByPage(HttpServletRequest request) {
		return userService.findAppUsersByPage(request);
	}

}

package com.test.web;

import com.test.modal.AppUserDto;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.validation.Valid;

@Controller
@RequestMapping({ "/users" })
public class UserController {

	@Autowired
	private UserService userService;
	private String usersPage = "pages/users/users";
	private String addEditUserPage = "pages/users/user-addEdit";
	private String userDetailsPage = "pages/users/user-details";
	private String alertError = "alertError";
	private String alertSuccess = "alertSuccess";

	@RequestMapping("")
	public String getAllUser(Model model) {
		System.out.println("----getAllUser-----");
		model.addAttribute("users", this.userService.findAllUsers());
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
		redAtt.addFlashAttribute(alertSuccess, "User details saved successfully!");
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
		redAtt.addFlashAttribute(alertError, "User deleted successfully!");
		return "redirect:/users";
	}

}

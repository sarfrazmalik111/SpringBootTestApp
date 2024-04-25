package com.test.web;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacebookController {

	private FacebookConnectionFactory factory = new FacebookConnectionFactory("844834739296116", "515bd70d37540ca6a7da36f8a2edf70e");
	private String appURL = "http://localhost:8080/forwardLogin";

	// Redirection uri.
	@GetMapping(value = "/facebook-login")
	public String redirect() {
		OAuth2Operations operations = factory.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.setRedirectUri(appURL);
		params.setScope("email, public_profile");

		String authUrl = operations.buildAuthenticateUrl(params);
		System.out.println("Generated url is= " + authUrl);
		return "redirect:" + authUrl;
	}

	// Welcome page.
	@GetMapping(value = "/forwardLogin")
	public String prodducer(@RequestParam("code") String authorizationCode, Model model) {
		OAuth2Operations operations = factory.getOAuthOperations();
		AccessGrant accessToken = operations.exchangeForAccess(authorizationCode, appURL, null);
		Connection<Facebook> connection= factory.createConnection(accessToken);

		Facebook facebook= connection.getApi();
		if(facebook.isAuthorized()) {
			String[] fields = { "id", "name", "email"};
			User userProfile= facebook.fetchObject("me", User.class, fields);
			
			model.addAttribute("user", userProfile);
			return "test/facebookSuccess";
		}else {
			model.addAttribute("msg", "Wrong email or password");
			return "redirect:/";
		}
	}
}
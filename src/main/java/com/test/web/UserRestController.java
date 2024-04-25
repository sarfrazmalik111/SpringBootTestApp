package com.test.web;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.common.AppConstants;
import com.test.modal.AppUserDto;
import com.test.modalDT.FilePathModel;
import com.test.service.AmazonS3Service;
import com.test.service.UserService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping({ "/api/users" })
public class UserRestController {

	@Autowired
	UserService userService;
	@Autowired
	private AmazonS3Service amazonS3Service;

	private ResponseEntity<Object> ENTITY_NOT_FOUND = new ResponseEntity<Object>("Entity not found", HttpStatus.NOT_FOUND);

	@GetMapping({ "" })
	public ResponseEntity<Object> getAllUser() {
		return new ResponseEntity(this.userService.findAllUsers(), HttpStatus.OK);
	}

	@GetMapping({ "/login" })
	public ResponseEntity<Object> login() {
		return new ResponseEntity("Please login!", HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(value = { "/save" }, consumes = { "application/json" })
	public ResponseEntity<Object> createUser(@RequestBody AppUserDto user) {
		System.out.println("Saving User: " + user.getUserName());
		this.userService.saveUser(user);
		return new ResponseEntity("User saved successfuly", HttpStatus.CREATED);
	}

	@GetMapping({ "/find/{id}" })
	public ResponseEntity<Object> getUserById(@PathVariable("id") Long id) {
		AppUserDto user = this.userService.findUserById(id);
		if (user == null)
			return this.ENTITY_NOT_FOUND;
		return new ResponseEntity(user, HttpStatus.OK);
	}

	@GetMapping({ "/delete/{id}" })
	public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
		AppUserDto user = this.userService.findUserById(id);
		if (user == null)
			return this.ENTITY_NOT_FOUND;
		this.userService.deleteById(id);
		return new ResponseEntity("User deleted successfully", HttpStatus.OK);
	}

	@RequestMapping({ "/paging" })
	public Page<AppUserDto> getAllUserByPaging(Pageable page, Model model) {
		System.out.println("=========Rest-Users-Pagging==========");
		return this.userService.findAllUsersByPage(page);
	}

	@GetMapping("/find-user")
	public String findByUserId(@RequestParam("userId") Long userId, HttpServletRequest request) {
		JSONObject jsonResponse = new JSONObject();
		try {
			if(userService.verifyUserAccess(request, jsonResponse)) {
				AppUserDto savedUser = userService.findUserById(userId);
				jsonResponse.put("status", 1);
				jsonResponse.put("message", AppConstants.SUCCESS);
				jsonResponse.put("userDetails", new JSONObject(savedUser));
			}
		} catch (Exception ex) {
			jsonResponse.put("status", 2);
			jsonResponse.put("message", AppConstants.SOMETHING_WRONG_MSG);
			ex.printStackTrace();
		}
		return jsonResponse.toString();
	}
	
	@PostMapping("/upload-file-on-awss3-bucket")
	public String uploadFileOnAmazonS3Bucket(@RequestParam("file") MultipartFile file) {
		System.out.println("------------uploadFileOnAmazonS3Bucket-------------");
		JSONObject jsonResponse = new JSONObject();
		try {
			if(file.isEmpty()) {
				jsonResponse.put("status", 0);
				jsonResponse.put("message", "Please upload any file");
			}else {
				FilePathModel fileData = amazonS3Service.uploadImage(file, AppConstants.TEST_DIR);
				jsonResponse.put("status", 1);
				jsonResponse.put("message", AppConstants.SUCCESS);
				jsonResponse.put("imagePath", fileData.getImagePath());
			}
		} catch (Exception ex) {
			jsonResponse.put("status", 0);
			jsonResponse.put("message", AppConstants.SOMETHING_WRONG_MSG);
			ex.printStackTrace();
		}
		return jsonResponse.toString();
	}
	
}

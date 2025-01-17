package com.test.web;

import com.test.common.AppConstants;
import com.test.common.RestResponseUtility;
import com.test.modal.AppUserDto;
import com.test.modal.MyRecord;
import com.test.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;

@RestController
@RequestMapping({ "/api/users" })
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {

	@Autowired
	private UserService userService;
	@Autowired
	private RestResponseUtility responseUtility;
	private Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@GetMapping("")
	public ResponseEntity<?> getAllUser() {
		ResponseEntity respEntity = null;
		logger.info("----------------getAllUser----------------");
		try {
			respEntity = responseUtility.successResponse(AppConstants.SUCCESS, this.userService.findAllUsers());
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to get user details.");
		}
		return respEntity;
	}

	@PostMapping("/save")
	public ResponseEntity saveUserDetails(@Valid @RequestBody AppUserDto appUserDto) {
		ResponseEntity respEntity = null;
		logger.info("----------------saveUserDetails----------------");
		try {
			AppUserDto userDto = userService.saveUser(appUserDto);
			if(userDto != null) {
				logger.info("User Saved: "+ userDto.getId() + " : "+ userDto.getEmailId());
			}
			respEntity = responseUtility.successResponse(AppConstants.SAVE_USER_SUCCESS);
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to save user details.");
		}
        return respEntity;
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		ResponseEntity respEntity = null;
		logger.info("----------------getUserById----------------");
		try {
			AppUserDto appUserDto = this.userService.findUserById(id);
			if (appUserDto == null) {
				return responseUtility.ENTITY_NOT_FOUND;
			}
			respEntity = responseUtility.successResponse(AppConstants.SUCCESS, appUserDto);
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to get user details.");
		}
		return respEntity;
	}

	@GetMapping("/find-by-email/{emailId}")
	public ResponseEntity findUserByEmailId(@PathVariable String emailId) {
		ResponseEntity respEntity = null;
		logger.info("----------------saveUserDetails----------------");
		try {
			AppUserDto appUserDto = this.userService.findUserByEmailId(emailId);
			if (appUserDto == null) {
				return responseUtility.ENTITY_NOT_FOUND;
			}
			respEntity = responseUtility.successResponse(AppConstants.SUCCESS, appUserDto);
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to get user details.");
		}
		return respEntity;
	}

	@GetMapping({ "/delete/{id}" })
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		ResponseEntity respEntity = null;
		logger.info("----------------deleteUser----------------");
		try {
			AppUserDto appUserDto = this.userService.findUserById(id);
			if (appUserDto == null) {
				return responseUtility.ENTITY_NOT_FOUND;
			}
			if(!this.userService.deleteById(id)) {
				return responseUtility.SOMETHING_WENT_WRONG;
			}
			respEntity = responseUtility.successResponse(AppConstants.DELETE_SUCCESS);
		} catch (Exception ex){
			logger.error(ex.getMessage());
			respEntity = responseUtility.serverErrorOcurred(ex, "Error to delete user details.");
		}
		return respEntity;
	}

	@GetMapping("/test-xml")
	public MyRecord testXmlMethod(HttpServletRequest request) {
		System.out.println("----------testXmlMethod------------");
		var student = new MyRecord(123, "Sarfraz", "Roorkee");
		System.out.println(student);

		ProblemDetail errDetails = ProblemDetail.forStatus(HttpStatus.NOT_ACCEPTABLE);
		errDetails.setTitle("Input invalid Exception");
		errDetails.setDetail("Input can't be processed");
		errDetails.setType(URI.create(request.getRequestURL().toString()));
		errDetails.setProperty("myError", "custom error object");
//		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errDetails);
		return student;
	}
	
}

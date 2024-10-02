package com.test.common;

import org.springframework.stereotype.Service;

@Service
public class AppConstants {

	public static final String APP_NAME = "SpringBootTestApp";
	public static final String APP_PORT_NUMBER = "8080";
	public static final String PROFILE_UAT = "UAT";

	public static final String SUCCESS = "Success";
	public static final String ALERT_ERROR = "alertError";
	public static final String ALERT_SUCCESS = "alertSuccess";

	public static final String SAVE_EMP_SUCCESS = "Employee saved successfully";
	public static final String SAVE_USER_SUCCESS = "User saved successfully";
	public static final String DELETE_SUCCESS = "Record deleted successfully";

	public static final String File_Size_Error = "File size must be less than 2 MB!";
	public static final String Cant_Empty_EntityId = "EntityId can't be empty";
	public static final String Cant_Empty_Email = "Email-Address can't be empty";

	public static final String ENTITY_NOT_FOUND = "Entity not found with this details";
	public static final String INVALID_INPUT = "Invalid input data";
	public static final String INVALID_PHONE_NUMBER = "Invalid PhoneNumber";
	public static final String INVALID_EMAIL = "Invalid Email address";
	public static final String EMAIL_NOT_EXISTS = "Email address does not exist";
	public static final String WRONG_EMAIL_OR_PWD = "Wrong email address or password";
	public static final String SOMETHING_WRONG_MSG = "Something went wrong, please try again";

	public static final String Existing_Email = "This Email-Address is already registered!";
	public static final String Existing_MobileNo = "This mobile number is already registered!";

	public enum UserRoleEnum{
		SUPERADMIN(1), EMPLOYEE(2), USER(3), CUSTOMER(4);
		private int id;
		private UserRoleEnum(int id){
			this.id = id;
		}
		public static UserRoleEnum getRoleByName(String enumName) {
			UserRoleEnum roleEnum = null;
			for(UserRoleEnum role: UserRoleEnum.values()) {
				if(role.name().equalsIgnoreCase(enumName))	roleEnum = role;
			}
			return roleEnum;
		}
	}

}

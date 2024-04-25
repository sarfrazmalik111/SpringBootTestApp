package com.test.common;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AppConstants {
	
	public static final String APP_PORT_NUMBER = "8080";
	public static final String MENU_ITEM = "menuItem";
	public static final String MENU_USERS = "Users";
	public static final String MENU_Mobile_Store = "MobileStore";

	public static final String MY_APP_SALT = "ABCDMNOPQFGEJKLTUVHIRSWXYZabcopqrstdemnfghijkluvxyz1234567890";
	public static List<String> userAgreementTokens = new ArrayList<>();
	public static final String CURRENCY = "USD";
//	public String CURRENCY_RUPPE = "INR";
	public static final String PAYMENT_METHOD_CREDIT_CARD = "credit_card";
	public static final String PAYMENT_METHOD_PAYPAL = "PAYPAL";
	public static final String PAYMENT_STATUS_CREATED = "created";	//Txn created successfully
	public static final String PAYMENT_STATUS_APPROVED = "approved";	//Txn approved by buyer
	public static final String PAYMENT_STATUS_FAILED = "failed";		//Txn fail
	
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String ERROR_MESSAGE = "errorMessage";
	public static final String UPLOAD_LOCATION_IDE = "src/main/resources/static/uploadedFiles/";
	public static final String UPLOAD_LOCATION_TOMCAT = "WEB-INF/classes/static/uploadedFiles/";
	public static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	public static final String MOBILE_REGEX = "91\\d{10}";

	public static final String SUCCESS = "Success";
	
	public static final String EMPLOYEES_DIR = "employees";
	public static final String USERS_DIR = "users";
	public static final String TEST_DIR = "test";
	public static final String QRCODES_DIR = "qrCodes";
	public static final String EC2_TEST_FOLDER = "/home/ec2-user/test/";
	public static final String S3_PARENT_FOLDER = "/AATest";
	public static String S3_ENDPOINT_URL = null;
	public static String S3_BUCKET_NAME = null;
	
	public static final String ALERT_ERROR = "alertError";
	public static final String ALERT_SUCCESS = "alertSuccess";
	public static final String INVALID_FORM_DATA = "Bitte Daten richtigen eingeben";
	public static final String SOMETHING_WRONG_MSG = "Irgendwas hat nicht funktioniert, probiere es nochmal";
	
	public static final String AGREEMENT_TITLE = "Why add payment method?";
	public static final String AGREEMENT_TEXT = "In order to be able to rent a power bank, we need a deposited payment method from you. "
		+ "We book the resulting fees of € 1.00 per hour and a maximum of € 5.00 per day (24 hours) after the return of the "
		+ "Powerbank from your account. Please make sure that you bring the power bank back to a station within 7 days, "
		+ "otherwise the Powerbank becomes your property and we book € 35.00 from your account.";
	
	public enum UserTypeEnum{
		User(1), Employee(2), SuperAdmin(3);
		private int id;
		private UserTypeEnum(int id){
			this.id = id;
		}
	}
	
	public enum ActiveInactiveEnum{
		Aktiv(1), Inaktiv(2);
		private int id;
		private ActiveInactiveEnum(int id){
			this.id = id;
		}
	}
	
	public static enum PaymentMethodEnum{
		CreditCard(1), Paypal(2);
		private int id;
		private PaymentMethodEnum(int id){
			this.id = id;
		}
	}
	
	public static enum DayNamesEnum{
		MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6), SUNDAY(7);
		private int id;
		private DayNamesEnum(int id){
			this.id = id;
		}
	}
	
}

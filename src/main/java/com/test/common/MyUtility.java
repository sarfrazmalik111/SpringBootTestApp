package com.test.common;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class MyUtility {

	@Autowired
	private Environment env;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private AppConstants constants;
	
	private static String local_Date_Format = "dd/MM/yyyy";
	private static String local_Date_Format2 = "dd-MM-yyyy";
	private static String local_DateTime_Format = "dd/MM/yyyy HH:mm:ss";
	private static String dateFormatForFileName = "ddMMyyyy_HHmmssSSS";
	private static String DB_DateTime_Format = "yyyy-MM-dd HH:mm:ss";
	private static String humanReadable_Format = "dd MMMM yyyy";
	private static SecretKeySpec secretKeySpec;
	private static byte[] digestKey;
	
	public String getBaseURL() {
		String baseURL = env.getProperty("canopi.server.base_url");
		try {
			if(request.getRequestURL().toString().contains("localhost")) {
				baseURL = "http://localhost:8080";
			}
		} catch (IllegalStateException ex) {
			System.out.println("ERROR: "+ ex.getMessage().substring(0, 30));
		}
		return baseURL;
	}
	
	public String getServletContextPath() {
		return request.getServletContext().getRealPath("/");
	}

//	===================================DateTime-Formating-Start=====================================
	public LocalDateTime parseLocalDateTimeForDB(String dateTime) {
		return LocalDateTime.parse(dateTime.replace("Z", ""));
	}
	
	public static String getTodayDate_ForFileName() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormatForFileName));
	}

	public String formatLocalDateTimeForUISlashDate(LocalDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ofPattern(local_Date_Format));
	}
	public String formatLocalDateForUI(LocalDate date) {
		String dateStr = date.format(DateTimeFormatter.ofPattern(local_Date_Format));
		return dateStr.replace("/", ".");
	}
	public String formatLocalDateForUILocalDateOnly(String date) {
		String dateStr = LocalDate.parse(date).format(DateTimeFormatter.ofPattern(local_Date_Format));
		return dateStr.replace("/", ".");
	}
	
	public String formatLocalDateTimeForUI(LocalDateTime dateTime) {
		String dateTimeStr = dateTime.format(DateTimeFormatter.ofPattern(local_DateTime_Format));
		dateTimeStr = dateTimeStr.substring(0, dateTimeStr.lastIndexOf(":"));
		return dateTimeStr.replace("/", ".");
	}
	public String formatLocalDateTimeForUI(String dateTime) {
		String dateTimeStr = LocalDate.parse(dateTime).format(DateTimeFormatter.ofPattern(local_DateTime_Format));
		dateTimeStr = dateTimeStr.substring(0, dateTimeStr.lastIndexOf(":"));
		return dateTimeStr.replace("/", ".");
	}
	public String formatLocalDateTimeForUILocalDateOnly(String dateTime) {
		String dateTimeStr = LocalDate.parse(dateTime).format(DateTimeFormatter.ofPattern(local_Date_Format));
		return dateTimeStr.replace("/", ".");
	}
	public String formatLocalDateTimeForUILocalDateOnly(LocalDateTime dateTime) {
		String dateTimeStr = dateTime.format(DateTimeFormatter.ofPattern(local_Date_Format));
		return dateTimeStr.replace("/", ".");
	}
	
	public String formatLocalDateTimeForHuman(String dateTime) {
		return LocalDateTime.parse(dateTime).format(DateTimeFormatter.ofPattern(humanReadable_Format));
	}
	
	public String getTodayDate_ForPaypal() {
		String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DB_DateTime_Format));
		return dateTime.replaceFirst("\\s", "T")+"Z";
	}
	
	public Long getSecondsFromTodayDateTime() {
		return System.currentTimeMillis()/1000;
	}
	public LocalDateTime getLocalDateTimeFromTimeInSeconds(String timeInSeonds) {
		Long timeInMillis = Long.parseLong(timeInSeonds+"000");
//		Date date = new Date(timeInMillis);
		Instant instant = Instant.ofEpochMilli(timeInMillis);
	    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	public long getDaysFromGivenDateTimeByToday(LocalDateTime dateTime) {
//		long hoursDiff = ChronoUnit.HOURS.between(dateTime, LocalDateTime.now());
		return ChronoUnit.DAYS.between(dateTime, LocalDateTime.now());
	}
	
	/**
	 * German-Date-Time
	 */
	private LocalTime getGermanTimeFromGivenTime(LocalTime localTime) {
		return localTime.plusHours(2);
//		LocalTime currentTime = localTime.plus(Duration.ofHours(1));
	}
	private LocalDateTime getGermanDatetimeFromGivenDatetime(LocalDateTime dateTime) {
		return dateTime.plusHours(2);
	}
	private String formatGermanyCurrency(float amount) {
		return formatGermanyCurrency(String.valueOf(amount));
	}
	private String formatGermanyCurrency(String number) {
		number = avoidNullableValue(number);
		number = number.isEmpty()?"0":number;
		Double doubleValue = Double.parseDouble(number);
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
//		System.out.println(currencyFormatter.format(floatValue));
		return currencyFormatter.format(doubleValue);
	}
	
//	===================================DateTime-Formating-End=====================================

	public String convertJavaObjectIntoJsonString(Object object) {
		return (new JSONObject(object)).toString();
	}
	public String convertJavaObjectIntoJsonStringDEEPLY(Object object) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		}catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	public <T> T convertJsonStringIntoJavaObject(String jsonString, Class<T> classType) {
		try {
			return (T) (new ObjectMapper()).readValue(jsonString, classType);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String getWindowsPath(String path) {
		char seprator = File.separatorChar;
		return path.replace("/", (new StringBuilder(String.valueOf(seprator))).toString());
	}

	public boolean validatePhoneNumber(String phoneNumber) {
//		return phoneNumber.matches("\\+\\d{12,15}");
		return phoneNumber.matches("\\d{10,12}");
	}
	
	public boolean validateEmail(String email) {
		String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
		return email.matches(EMAIL_REGEX);
	}
	
	// It will generate 2 digit random Number from 0 to 99
	public String get2DigitRandomNumber() {
	    Random rnd = new Random();
	    int number = rnd.nextInt(99);
	    return String.format("%02d", number);
	}
	public String get6DigitRandomNumber() {
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	
	public Double parseDoubleUpto2DecimalPlaces(Double doubleValue) {
//		float floatValue = 22.3453555f;
		if(doubleValue != null) {
			return Double.parseDouble(String.format("%.2f", doubleValue));
		}else {
			return doubleValue;
		}
	}
	
	public String parseDoubleUpto2DecimalPlaces(String decimalValue) {
		if(decimalValue==null) decimalValue = "0";
		if(decimalValue.indexOf("E")>0) {
			decimalValue = decimalValue.substring(0, decimalValue.indexOf("E"));
		}
		else if(decimalValue.indexOf("e")>0) {
			decimalValue = decimalValue.substring(0, decimalValue.indexOf("e"));
		}
		return String.format("%.2f", Double.parseDouble(decimalValue));
	}
	
	/**
	 * It Return Empty string if passed parameter is NULL
	 * @param str
	 */
	public boolean isEmptyString(String str) {
		return (str == null || str.trim().isEmpty());
	}
	public String avoidNullableValue(String str) {
		return str!=null?str:"";
	}
	public Integer avoidNullableValue(Integer number) {
		return number!=null?number:0;
	}
	public Long avoidNullableValue(Long number) {
		return number!=null?number:0;
	}
	public Float avoidNullableValue(Float number) {
		return number!=null?number:0;
	}
	public Double avoidNullableValue(Double number) {
		return number!=null?number:0;
	}
	public Boolean isNumberWithLength(String number, int length) {
		Boolean status = false;
		if(number != null && number.trim().length() == length && number.trim().matches("\\d{"+length+"}")){
			status = true;
		}
		return status;
	}

	public String getUniqueKeyFromERROR(String uk_error) {
		String unique_key = null;
		Pattern MY_PATTERN = Pattern.compile("(.+)(UK_\\w+)(.+)");
		Matcher m = MY_PATTERN.matcher(uk_error);
		if (m.find()) {
			unique_key = m.group(2);
		}
		return unique_key;
	}
	
	private void sortMyList() {
//		Collections.sort(list, (loc1,loc2)->{ return loc1.getTotalStations() < loc2.getTotalStations()?1:-1; });
	}
	
	public String getOrderId() {
		String base = "0123456789ABCDEFGHIJKLmnopqrstuvwxyz";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 15; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public String encodeForSHA256(String text) {
		return DigestUtils.sha256Hex(text);
	}
	public String encodeForURL(String text) throws UnsupportedEncodingException {
		return URLEncoder.encode(text, StandardCharsets.UTF_8.toString());
	}
	public String decodeForURL(String encodedText) throws UnsupportedEncodingException {
		return URLDecoder.decode(encodedText, StandardCharsets.UTF_8.toString());

	}
	public String encodeForBase64URL(String text) {
		String payload = text + AppConstants.MY_APP_SALT;
		return Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
	}
	public String decodeForBase64URL(String encodedText) {
		try {
			String decodedText = new String(Base64.getUrlDecoder().decode(encodedText));
			return decodedText.replaceFirst(AppConstants.MY_APP_SALT, "");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	public static final void main(String[] args) {
		int aa = 5;
		aa += 10;
		System.out.println(aa);


	}

}

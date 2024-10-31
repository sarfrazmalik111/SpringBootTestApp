package com.test.common;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.test.modal.EmployeeDto;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class MyUtility {

	@Autowired
	private Environment env;
	@Autowired
	private HttpServletRequest request;
	public static final String US_Date_Format = "yyyy-MM-dd";
	public static final String local_Date_Format = "dd-MM-yyyy";
	public static final String local_DateTime_Format = "dd/MM/yyyy HH:mm:ss";
	private Logger logger = LoggerFactory.getLogger(MyUtility.class);
	private static ObjectMapper objectMapper = new ObjectMapper();

//	===================================DateTime-Formating-Start=====================================
	public Long getTimeInSeconds() {
		return System.currentTimeMillis()/1000;
	}

	public String getTodayDateToUSDate() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern(US_Date_Format));
	}

	public String formatLocalDateTimeForUI(LocalDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ofPattern(local_Date_Format));
	}
	public String formatLocalDateTimeForUI2(LocalDateTime dateTime) {
		return dateTime.format(DateTimeFormatter.ofPattern(local_DateTime_Format));
	}

	public LocalDateTime parseDateTimeIntoDateTime(String dateTime) {
		if(isEmptyString(dateTime)) return null;
		if(dateTime.contains("+")){
			dateTime = dateTime.substring(0, dateTime.indexOf("+"));
		}
		return LocalDateTime.parse(dateTime);
	}

	public String parseTodayDateTimeByDatetimeFormat(String datetimeFormat) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(datetimeFormat));
	}

	public LocalDateTime parseLocalDateTime(String dateTime) {
		if(isEmptyString(dateTime)) return null;
		return LocalDateTime.parse(dateTime.replace("Z", ""));
	}

	public LocalDate parseLocalDate(String date) {
		if(isEmptyString(date)) return null;
		return LocalDate.parse(date);
	}

	public String parseIndianDateIntoUSDate(String date) {
		if(isEmptyString(date)) return null;
		String newDate = null;
		if(isEmptyString(date)) return newDate;
		if(date.contains("-")) {
			String dt[] = date.split("-");
			newDate = dt[2] + "-" + dt[1] + "-" + dt[0];
		} else if(date.contains("/")) {
			String dt[] = date.split("/");
			newDate = dt[2] + "-" + dt[1] + "-" + dt[0];
		}
		return newDate;
	}

	private static Random random = null;
	public static Random getRandom() {
		try {
			if(random == null) {
				random = SecureRandom.getInstanceStrong();
			}
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return random;
	}
	public String get5DigitRandomNumber() {
		if(getRandom() == null) return null;
		return String.format("%05d", random.nextInt(99999));
	}
	public String get6DigitRandomNumber() {
		if(getRandom() == null) return null;
		return String.format("%06d", random.nextInt(999999));
	}

	public Double parseUpto2DecimalPlaces(Double doubleValue) {
		if(doubleValue != null) {
			return Double.parseDouble(String.format("%.2f", doubleValue));
		}else {
			return doubleValue;
		}
	}
	public String parseUpto2DecimalPlacesStr(Double doubleValue) {
		return String.format("%.2f", doubleValue);
	}

	public String convertJavaObjectIntoJsonString(Object object) {
		return (new JSONObject(object)).toString();
	}

	public static String convertJavaObjectIntoJsonStringDEEPLY(Object object) {
		try {
			objectMapper.registerModule(new JavaTimeModule());
			return objectMapper.writeValueAsString(object);
		}catch (JsonProcessingException ex){
			System.err.println(ex.getMessage());
			return null;
		}
	}
	public static JsonNode convertJosnStringIntoJsonNode(String jsonString) {
		JsonNode jsonNode = null;
		try {
			if(jsonString != null) {
				jsonNode = objectMapper.readTree(jsonString);
			}
		} catch (JsonProcessingException ex){
			System.err.println(ex.getMessage());
		}
		return jsonNode;
	}

	public static <T> T convertJsonStringIntoJavaObject(String jsonString, Class<T> classType) {
		try {
			return objectMapper.readValue(jsonString, classType);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	public static <T> T convertObjectIntoAnotherObject(Object object, Class<T> classType) {
		try {
			return objectMapper.convertValue(object, classType);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public byte[] convertJavaObjectIntoBytes(Object object) {
		try {
			return objectMapper.writeValueAsBytes(object);
		} catch (JsonProcessingException ex) {
			logger.info("ERROR: MyUtility.convertJavaObjectIntoBytes()------->>>"+ ex.getMessage());
		}
		return null;
	}

	/**
	 * It Return Empty string or 0 value if passed parameter is NULL
	 * @param str
	 */
	public boolean isEmptyString(String str) {
		return (str == null || str.trim().isEmpty());
	}
	public String avoidNullableValue(String str) {
		return str!=null?str:"";
	}

	public String getString(JSONObject jsonObject, String key) {
		String paramValue = null;
		try {
			if(jsonObject.has(key) && !jsonObject.isNull(key) && jsonObject.getString(key) != null)
				paramValue = jsonObject.getString(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return paramValue != null ? paramValue : "";
	}

	public JSONObject getJSONObject(JSONObject jsonObject, String key) {
		try {
			if (jsonObject.has(key) && !jsonObject.isNull(key) && jsonObject.getJSONObject(key) != null)
				return jsonObject.getJSONObject(key);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

	public static boolean isVowel(String strChar) {
		return strChar.matches("[aeiouy]");
	}

	public static void main(String[] args) {
		MyUtility myUtility = new MyUtility();
		JSONObject jsonObject = new JSONObject().put("name", "sarfraz malik");
		JsonNode jsonNode = convertJosnStringIntoJsonNode(jsonObject.toString());
		System.out.println(jsonNode);

		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setId(1l);
		employeeDto.setName("sarfraz");
		JsonNode jsonNode1 = convertObjectIntoAnotherObject(employeeDto, JsonNode.class);
		System.out.println(jsonNode1);

		List<EmployeeDto> list = new ArrayList<>();
		list.add(employeeDto);
		JsonNode jsonNode2 = convertObjectIntoAnotherObject(list, JsonNode.class);
		System.out.println(jsonNode2);

	}

}

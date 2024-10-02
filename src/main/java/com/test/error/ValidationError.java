package com.test.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationError {

	private Integer errorCode = 400;
	private Integer statusCode = 400;
	private String status = "Error";
	private String message;
	private List<Violation> inputErrors = new ArrayList<>();

	public void addError(String fieldName, String message) {
		inputErrors.add(new Violation(fieldName, message));
	}
	public String getFirstErrorMessage() {
		String firstErrorMsg = null;
		if(!inputErrors.isEmpty()) {
			firstErrorMsg = inputErrors.get(0).getMessage();
		}
		return firstErrorMsg;
	}
}

@Data
@AllArgsConstructor
class Violation {
	private String fieldName;
	private String message;
}
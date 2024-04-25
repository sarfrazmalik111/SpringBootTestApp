package com.test.modalDT;

import java.util.List;
import java.util.ArrayList;

public class ValidationErrorResponse {

    private List<Violation> inputErrors = new ArrayList<>();

	public List<Violation> getInputErrors() {
		return inputErrors;
	}

	public void setInputErrors(List<Violation> inputErrors) {
		this.inputErrors = inputErrors;
	}

}

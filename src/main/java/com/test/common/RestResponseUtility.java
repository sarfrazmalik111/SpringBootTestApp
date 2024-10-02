package com.test.common;

import com.test.error.CustomResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class RestResponseUtility {

	private Logger logger = LoggerFactory.getLogger(RestResponseUtility.class);

	public ResponseEntity ENTITY_NOT_FOUND = CustomResponse.getHttpErrorResponse(HttpStatus.NOT_FOUND, AppConstants.ENTITY_NOT_FOUND);
	public ResponseEntity INVALID_DATA_ENTERED = CustomResponse.getHttpErrorResponse(HttpStatus.NOT_ACCEPTABLE, AppConstants.INVALID_INPUT);
	public ResponseEntity SOMETHING_WENT_WRONG = CustomResponse.getHttpErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, AppConstants.SOMETHING_WRONG_MSG);
	public ResponseEntity FORBIDDEN = CustomResponse.getHttpErrorResponse(HttpStatus.FORBIDDEN, "Forbidden access");

	public ResponseEntity successResponse(String successMsg){
		return ResponseEntity.ok( CustomResponse.getHttpSuccessResponse(successMsg) );
	}
	public ResponseEntity successResponse(Object data){
		return ResponseEntity.ok( data );
	}
	public ResponseEntity successResponse(String successMsg, Object data){
		return ResponseEntity.ok( CustomResponse.getHttpSuccessResponse(successMsg, data) );
	}
	public ResponseEntity successResponse(JSONObject jsonParams, String successMsg){
		JSONObject jsonResponse = new JSONObject(CustomResponse.getHttpSuccessResponse(successMsg));
		for(String paramName: jsonParams.keySet()){
			jsonResponse.put(paramName, jsonParams.get(paramName));
		}
		return ResponseEntity.ok( jsonResponse.toMap() );
	}
	public ResponseEntity getResponseWithStatusCode(Object result, HttpStatus httpStatus){
		return new ResponseEntity<>(result, httpStatus);
	}

	public ResponseEntity serverErrorOcurred(String errorMsg){
		return CustomResponse.getHttpErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorMsg);
	}
	public ResponseEntity serverErrorOcurred(Exception ex, String errorMsg){
		return CustomResponse.getServerErrorResponse(ex, errorMsg);
	}

	public ResponseEntity entityNotFoundError(String entityName){
		return CustomResponse.getHttpErrorResponse(HttpStatus.NOT_FOUND, entityName+" not found with this details");
	}
	public ResponseEntity invalidDataFoundError(String errorMsg){
		return CustomResponse.getHttpErrorResponse(HttpStatus.NOT_ACCEPTABLE, errorMsg);
	}
	public ResponseEntity inputDataAlreadyExistsError(String errorMsg){
		return CustomResponse.getHttpErrorResponse(HttpStatus.CONFLICT, errorMsg);
	}

}

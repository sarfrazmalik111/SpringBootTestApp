package com.test.error;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.common.MyUtility;
import lombok.Data;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class CustomResponse {

    private boolean status;
    private int statusCode;
    private String message;
    private JsonNode data;

    public static CustomResponse getHttpSuccessResponse(String successMsg){
        CustomResponse response = new CustomResponse();
        response.setStatus(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage(successMsg);
        return response;
    }

    public static CustomResponse getHttpSuccessResponse(String successMsg, Object jsonData){
        CustomResponse response = getHttpSuccessResponse(successMsg);
        response.setData(MyUtility.convertObjectIntoAnotherObject(jsonData, JsonNode.class));
        return response;
    }

    public static ResponseEntity getHttpErrorResponse(HttpStatus httpStatus, String errorMsg){
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setStatusCode(httpStatus.value());
        response.setMessage(errorMsg);
        return new ResponseEntity<>(response, httpStatus);
    }

    public static ResponseEntity getServerErrorResponse(Exception ex, String errorMsg){
        CustomResponse response = new CustomResponse();
        response.setStatus(false);
        response.setStatusCode(500);
        response.setMessage(errorMsg);
        JSONObject jsonError = new JSONObject();
        if(ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause().getMessage() != null) {
            jsonError.put("error", ex.getCause().getCause().getMessage());
        } else {
            jsonError.put("error", ex.getMessage());
        }
        response.setData(MyUtility.convertJosnStringIntoJsonNode(jsonError.toString()));
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

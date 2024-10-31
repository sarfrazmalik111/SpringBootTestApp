package com.test.error;

import com.test.common.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.net.SocketTimeoutException;
import java.nio.file.AccessDeniedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationError onConstraintValidationException(ConstraintViolationException ex){
        logger.error("------------ConstraintViolationException-------------");
        ValidationError errorResponse = new ValidationError();
        for(ConstraintViolation error: ex.getConstraintViolations()){
            errorResponse.addError(error.getPropertyPath().toString(), error.getMessage());
        }
        errorResponse.setMessage(errorResponse.getFirstErrorMessage());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationError onMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        logger.error("------------MethodArgumentNotValidException--------------");
        ValidationError errorResponse = new ValidationError();
        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        errorResponse.setMessage(errorResponse.getFirstErrorMessage());
        return errorResponse;
    }

    @ExceptionHandler({AccessDeniedException.class, HttpClientErrorException.Forbidden.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity accessDeniedException(AccessDeniedException ex) {
        logger.error("-------------AccessDeniedException---------");
        return CustomResponse.getServerErrorResponse(ex, "Access denied");   //"{\"status\":\"access denied\"}";
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity maxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        logger.error("-------------MaxUploadSizeExceededException---------");
        return CustomResponse.getServerErrorResponse(ex, AppConstants.File_Size_Error);
    }

    @ExceptionHandler({SocketTimeoutException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public ResponseEntity requestTimeoutException(SocketTimeoutException ex) {
        logger.error("-------------SocketTimeoutException---------");
        return CustomResponse.getServerErrorResponse(ex, "Request Timeout");
    }

}

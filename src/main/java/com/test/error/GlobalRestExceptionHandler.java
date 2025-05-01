package com.test.error;

import com.test.common.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.net.SocketTimeoutException;
import java.util.Date;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);

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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> globleRestExcpetionHandler(Exception ex, WebRequest request) {
        System.out.println("----------globleRestExcpetionHandler-----------");
        if(ex.getMessage().contains("favicon.ico")) {
            return null;
        }
        System.out.println(request.getDescription(true));
        System.out.println(request.getHeaderNames());
        System.out.println(ex.getClass());
        System.out.println(ex.getMessage());
        ex.printStackTrace();
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

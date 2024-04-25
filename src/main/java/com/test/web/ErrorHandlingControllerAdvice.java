package com.test.web;

import com.test.modalDT.ValidationErrorResponse;
import com.test.modalDT.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse onConstraintValidationException(ConstraintViolationException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for(ConstraintViolation violation: ex.getConstraintViolations()){
            errorResponse.getInputErrors().add(
                new Violation(violation.getPropertyPath().toString(), violation.getMessage())
            );
        }
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            errorResponse.getInputErrors().add(
                new Violation(fieldError.getField(), fieldError.getDefaultMessage())
            );
        }
        return errorResponse;
    }

}

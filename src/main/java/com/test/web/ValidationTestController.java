package com.test.web;

import com.test.modal.AppUserDto;
import com.test.modalDT.ValidationErrorResponse;
import com.test.modalDT.Violation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/canopi-pay")
public class ValidationTestController {

//  Here AppUser validated by BindingResult, It works only with @Valid
    @PostMapping("/validate-user-test")
    public ResponseEntity<Object> contactInfo(@Valid @RequestBody AppUserDto user, BindingResult bindingResult) {
        System.out.println("---------contactInfo----------");
        if(bindingResult.hasErrors()){
            ValidationErrorResponse errorResponse = new ValidationErrorResponse();
            for (FieldError error: bindingResult.getFieldErrors()){
                errorResponse.getInputErrors().add(
                    new Violation(error.getField(), error.getDefaultMessage())
                );
            }
            return ResponseEntity.ok(errorResponse);
        }
        System.out.println(user);
        
//        Convert Java-Object into JSON-String
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String userInfo = objectMapper.writeValueAsString(user);
            System.out.println(userInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(user);
    }

//  Here AppUser validated by ErrorHandlingControllerAdvice, It works only with @Valid 
    @PostMapping("/validate-customer-test")
    public ResponseEntity<AppUserDto> registerCustomer(@Valid @RequestBody AppUserDto user) {
        System.out.println("---------registerCustomer----------");
        return ResponseEntity.ok(user);
    }
}

package com.test.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public String resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		System.out.println("----------resourceNotFoundException-----------");
		System.out.println(ex.getMessage());
		System.out.println(request.getDescription(false));
		return "redirect:/page-not-found";
	}

	@ExceptionHandler(MultipartException.class)
	public String multipartException(MultipartException e, RedirectAttributes redirectAttributes) {
		System.out.println("----------multipartException-----------");
		redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
		return "redirect:/uploadStatus";

	}

	@ExceptionHandler(Exception.class)
	public String globleExcpetionHandler(Exception ex, HttpServletRequest request) {
		System.out.println("----------globleExcpetionHandler-----------");
		System.out.println(ex.getMessage());
		ex.printStackTrace();
		return "redirect:/"+request.getRequestURL();
	}

}
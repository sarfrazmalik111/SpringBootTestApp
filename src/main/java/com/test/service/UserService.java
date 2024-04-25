package com.test.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.modal.AppUserDto;

public interface UserService {

	AppUserDto saveUser(AppUserDto user);
	Boolean deleteById(Long id);
	boolean verifyUserAccess(HttpServletRequest request, JSONObject jsonResponse);
	AppUserDto findUserById(Long id);
	List<AppUserDto> findAllUsers();
	Page<AppUserDto> findAllUsersByPage(Pageable pageable);
	String findAppUsersByPage(HttpServletRequest request);
}

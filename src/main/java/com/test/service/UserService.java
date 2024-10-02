package com.test.service;

import com.test.modal.AppUserDto;
import java.util.List;

public interface UserService {

	AppUserDto saveUser(AppUserDto user);
	AppUserDto findUserById(Long id);
	AppUserDto findUserByEmailId(String emailId);
	List<AppUserDto> findAllUsers();
	boolean deleteById(Long id);

}

package com.test.service;

import com.test.common.MyUtility;
import com.test.dao.UserRepository;
import com.test.modal.AppUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MyUtility myUtility;

	public AppUserDto saveUser(AppUserDto user) {
		if(user.getId() == null) user.setCreatedOn(LocalDateTime.now());
		return userRepository.save(user);
	}

	public AppUserDto findUserById(Long id) {
		AppUserDto user = null;
		Optional<AppUserDto> data = userRepository.findById(id);
		if (data.isPresent()) {
			user = data.get();
			user.setCreatedOnStr(myUtility.formatLocalDateTimeForUI(user.getCreatedOn()));
		}
		return user;
	}

	public AppUserDto findUserByEmailId(String emailId) {
		return userRepository.findByEmailId(emailId);
	}

	public List<AppUserDto> findAllUsers() {
		List<AppUserDto> appUsers = userRepository.findAll();
		for(AppUserDto user: appUsers) {
			user.setCreatedOnStr(myUtility.formatLocalDateTimeForUI(user.getCreatedOn()));
		}
		return appUsers;
	}

	public boolean deleteById(Long id) {
		boolean status = false;
		try {
			userRepository.deleteById(id);
			status = true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return status;
	}

}

package com.test.dao;

import com.test.modal.AppUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AppUserDto, Long> {

	@Query("SELECT u.emailId FROM AppUserDto u WHERE u.id = :id")
	String getEmailIdByUserId(@Param("id") Long id);

	@Modifying
	@Query("UPDATE AppUserDto u SET u.emailId = :emailId WHERE u.id = :id")
	int updateEmailId(@Param("id") Long id, @Param("emailId") String emailId);

	AppUserDto findByEmailId(String emailId);
	AppUserDto findByMobileNo(String mobileNo);
	boolean existsByEmailId(String emailId);
	boolean existsByMobileNo(String mobileNo);

}

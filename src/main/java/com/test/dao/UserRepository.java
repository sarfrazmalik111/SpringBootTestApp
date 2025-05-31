package com.test.dao;

import com.test.modal.AppUserDto;
import com.test.modal.TestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<AppUserDto, Long> {

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM AppUserDto u WHERE u.id = :id")
	Boolean isUserExistsById(@Param("id") Long id);

	@Query("SELECT u.emailId FROM AppUserDto u WHERE u.id = :id")
	String getEmailIdByUserId(@Param("id") Long id);

	@Modifying
	@Query("UPDATE AppUserDto u SET u.emailId = :emailId WHERE u.id = :id")
	int updateEmailId(@Param("id") Long id, @Param("emailId") String emailId);

	AppUserDto findByEmailId(String emailId);
	AppUserDto findByMobileNo(String mobileNo);
	List<AppUserDto> findAllByOrderByCreatedOnDesc();
	List<AppUserDto> findAllByActiveOrderByCreatedOnDesc(boolean active);
	boolean existsByEmailId(String emailId);
	boolean existsByMobileNo(String mobileNo);

//	===========================================================================
	@Query(value = "SELECT * FROM TESTB WHERE id IN(?1)", nativeQuery = true)
	List<Object[]> getTestBDataByIdIn(List<Long> ids);

	@Query(value = "SELECT b.* FROM TESTB b INNER JOIN TESTA a ON a.id=b.testa_id ORDER BY created_on ASC", nativeQuery = true)
	List<Object[]> getAllTestBData();

}

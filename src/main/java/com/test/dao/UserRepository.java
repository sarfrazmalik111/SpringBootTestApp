package com.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.test.modal.AppUserDto;

public interface UserRepository extends JpaRepository<AppUserDto, Long> {

	@Query("SELECT u.email FROM AppUserDto u WHERE u.id = :id")
	String getEmailByUserId(@Param("id") Long id);
	
//	@Query("SELECT new AppUser(u.id,u.userName,u.email,u.createdOn) FROM AppUser WHERE u.deleted=:deleted")
//	List<AppUser> findAllUsers(@Param("deleted") boolean deleted);
//	@Query("SELECT new AppUser(u.id,u.walletAmount,c.paymentMethod,c.cardId,c.agreementId) FROM AppUser AS u LEFT JOIN CreditCard2 AS c ON u.id=c.userId WHERE u.id=:id")
//	List<AppUser> findAppUserDetailsForPaymentById(@Param("id") Long id);
//	
//	@Query("SELECT new Rentals(r.id,r.powerbankNumber,s.stationNumber) from Rentals AS r INNER JOIN Station AS s ON s.id=r.stationId WHERE r.id=:id AND r.rentalStatus=4")
//	Rentals findRentalDetailsForEndRenting(@Param("id") Long id);
//	@Query("SELECT new PromoCode(id,status) FROM PromoCode WHERE expireOn=CURDATE() AND status=0")
//	List<PromoCode> findPromoCodesToBeExpireToday();
	
	@Modifying
	@Query("UPDATE AppUserDto u SET u.email = :email WHERE u.id = :id")
	int updateEmail(@Param("id") Long id, @Param("email") String email);
	@Modifying
	@Query("UPDATE AppUserDto u SET u.deleted = :deleted WHERE u.id = :id")
	int deleteAppUser(@Param("id") Long id, @Param("deleted") boolean deleted);

//	@Modifying
//	@Query("UPDATE AppUserDto u SET u.walletAmount = :walletAmount WHERE u.id = :id")
//	int updateWalletAmount(@Param("id") Long id, @Param("walletAmount") float walletAmount);
//	@Modifying
//	@Query("UPDATE AppUserDto u SET u.walletAmount = u.walletAmount+:walletAmount WHERE u.id = :id")
//	int addWalletAmount(@Param("id") Long id, @Param("walletAmount") float walletAmount);
//	@Modifying
//	@Query("UPDATE Station s SET s.status = :status WHERE s.id = :id")
//	int updateStationStatusById(@Param("id") Long paramLong, @Param("status") ActiveInactiveEnum status);
//	@Modifying
//    public int deleteByUserIdAndCreatedOnAfter(Long userId, LocalDate createdOn);

//	AppUser findByPhoneNumberFull(String phoneNumberFull);
	AppUserDto findByEmail(String email);
//	AppUser findByEmailAndPassword(String email, String password);
//	List<CreditCard2> findByUserId(Long userId);
//	List<Station> findByLocationIdAndDeleted(Long locationId, boolean deleted);
//	PromoCode findByNameAndStatusInAndDeleted(String name, List<PromoStatusEnum> list, boolean deleted);
//	PromoCode findByNameAndStatusAndDeleted(String name, ActiveInactiveEnum status, boolean deleted);

	
	boolean existsByEmail(String email);
	boolean existsByAccessToken(String accessToken);
//	boolean existsByUserIdAndPromoCodeId(Long userId, Long promoCodeId);
//	boolean existsByUserIdAndRentalStatus(Long userId, ActiveInactiveEnum rentalStatus);
			
}

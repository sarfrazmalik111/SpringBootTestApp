package com.test.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.common.AppConstants;
import com.test.common.MyUtility;
import com.test.modalDT.DataTableRequest;
import com.test.modalDT.DataTableResults;
import com.test.modalDT.UserDT;
import com.test.dao.AppJdbcTemplate;
import com.test.dao.UserRepository;
import com.test.modal.AppUserDto;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppJdbcTemplate jdbcTemplate;
	@Autowired
	private MyUtility myUtils;
	
	private final String[] columnNames = { "u.id", "u.email", "u.status", "u.createdOn" };

	public AppUserDto saveUser(AppUserDto user) {
//			user.setPassword(myUtils.encrypt(user.getPassword()));
		user.setAccessToken(createAccessToken());
		if(user.getCreatedOn() == null) user.setCreatedOn(LocalDateTime.now());
		return userRepository.save(user);
	}

	public Boolean deleteById(Long id) {
		Boolean flag = Boolean.valueOf(false);
		try {
			userRepository.deleteById(id);
			flag = Boolean.valueOf(true);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return flag;
	}
	
	public boolean verifyUserAccess(HttpServletRequest request, JSONObject jsonResponse) {
		boolean isVerified = false;
		if(request.getHeader(AppConstants.ACCESS_TOKEN) != null) {
//			isVerified = userRepository.existsByAccessToken(request.getHeader(AppConstants.ACCESS_TOKEN));
		}
		if(!isVerified) {
			jsonResponse.put("status", 0);
			jsonResponse.put("message", "Illegal Access!");
		}
		return isVerified;
	}

	public AppUserDto findUserById(Long id) {
		AppUserDto user = null;
		Optional<AppUserDto> list = userRepository.findById(id);
		if (list.isPresent()) {
			user = list.get();
			user.setCreatedOnStr(myUtils.formatLocalDateTimeForUILocalDateOnly(user.getCreatedOn()));
		}
		return user;
	}

	public List<AppUserDto> findAllUsers() {
		return userRepository.findAll();
	}

	public Page<AppUserDto> findAllUsersByPage(Pageable page) {
		return userRepository.findAll(page);
	}
	
	public UserDT findAppUserDetailsForEditPage(Long id) {
		String sql = "SELECT u.id,u.phoneNumberFull,u.email,u.walletAmount,"
			+ "(SELECT COUNT(c.id) FROM CreditCard2 AS c WHERE c.userId=u.id) AS cards,"
			+ "(SELECT COUNT(r.id) FROM Rentals AS r WHERE r.userId=u.id AND r.type=2) AS rentals,"
			+ "(SELECT COUNT(r.id) FROM Rentals AS r WHERE r.userId=u.id AND r.type=3) AS purchases,"
			+ "(SELECT SUM(r.totalAmount) FROM Rentals AS r WHERE r.userId=u.id AND r.paymentStatus=0) AS sales,"
			+ "(SELECT COUNT(i.id) FROM Invitation AS i WHERE i.fromUserId=u.id) AS invitations,"
			+ "(SELECT COUNT(w.id) FROM WalletTransaction AS w WHERE w.userId=u.id AND w.paymentMethod=0) AS promoCodes "
			+ " FROM AppUser AS u WHERE u.id="+id;
		UserDT appUser = jdbcTemplate.getRecordsByGivenSelectSQL(sql, UserDT.class).get(0);
		return appUser;
	}
	
	/**
	 * Getting appUsers for pagination
	 */
	public String findAppUsersByPage(HttpServletRequest request) {
		DataTableResults dataTableResult = new DataTableResults();
		DataTableRequest dataTableRequest = new DataTableRequest(request, columnNames, AppUserDto.class.getSimpleName());
		JSONObject jsonRESULT = null;
		List<JSONObject> dataList = new ArrayList<>();
		int totalRecords = 0;
		try {
			String sql = dataTableRequest.getSelectSQL() + dataTableRequest.getOrderBySQL();
			List<UserDT> resultSet = jdbcTemplate.getRecordsByGivenSelectSQL(sql, UserDT.class);
			for(UserDT user: resultSet) {
				String url = "<a title='Delete User' href='/users/delete/"+user.getId()+"' onclick=\"return confirm('Sind Sie sicher, dass Sie löschen möchten?')\">"
						+ "<i class='fa fa-trash-o' aria-hidden='true'></i></a>";
				user.setAction(url);
				user.setEmail(myUtils.avoidNullableValue(user.getEmail()));
				user.setCreatedOn(myUtils.formatLocalDateTimeForUILocalDateOnly(user.getCreatedOn()));
				dataList.add(new JSONObject(user));
			}
			if(!resultSet.isEmpty()) {
				totalRecords = resultSet.get(0).getTotalRecords();
			}
			int filteredRecords = totalRecords;
			if(!dataTableRequest.getGlobalSearchTerm().isEmpty()) {
				filteredRecords = jdbcTemplate.getRecordCountByGivenSQL(dataTableRequest.getGlobalSearchSQL());
			}
			dataTableResult.setDraw(dataTableRequest.getDraw());
			dataTableResult.setRecordsTotal(totalRecords);
			dataTableResult.setRecordsFiltered(filteredRecords);
			dataTableResult.setData(dataList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally {
			jsonRESULT = new JSONObject(dataTableResult);
		}
		return jsonRESULT.toString();
	}
	
	private String createAccessToken() {
		String uniqueUserCode = myUtils.encodeForBase64URL( myUtils.get6DigitRandomNumber() );
		while(true) {
			if(userRepository.existsByAccessToken(uniqueUserCode)) {
				uniqueUserCode = createAccessToken();
			} else {
				break;
			}
		}
		return uniqueUserCode;
	}
}

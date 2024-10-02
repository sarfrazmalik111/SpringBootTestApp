package com.test.dao;

import com.test.modal.AppUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcTemplate {

	@Autowired
	private JdbcTemplate jdbc;

	public void add(AppUserDto appUserDto) {
		this.jdbc.execute("insert into AppUser(userName,email,mobileNo,address) " +
				"values('"+appUserDto.getUserName()+"','"+appUserDto.getEmailId()+"','"+appUserDto.getMobileNo()+"','"+appUserDto.getAddress()+"')");
	}

	public List<AppUserDto> getUsers() {
		String sql = "select * from AppUser";
		return this.jdbc.query(sql, new BeanPropertyRowMapper<AppUserDto>(AppUserDto.class));
	}

	public AppUserDto getUserById(int id) {
		System.out.println("ID: " + id);
		String sql = "select * from AppUser where id=?";
		return this.jdbc.queryForObject(sql, new Object[] { Integer.valueOf(id) },
				new BeanPropertyRowMapper<AppUserDto>(AppUserDto.class));
	}

	public Integer getTotalUserCount() {
		String sql = "SELECT COUNT(*) FROM AppUser";
		return this.jdbc.queryForObject(sql, Integer.class);
	}

}

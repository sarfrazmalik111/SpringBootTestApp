package com.test.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.test.modal.AppUserDto;

@Repository
public class UserJdbcTemplate {

	@Autowired
	private JdbcTemplate jdbc;

	public void add() {
		this.jdbc.execute("insert into user_record(id,username,email,address) values(5,'Sarfraz5','sarfraz3@gmail.com','Mohali')");
	}

	public List<AppUserDto> getUsers() {
		String sql = "select * from user_record";
		return this.jdbc.query(sql, new BeanPropertyRowMapper<AppUserDto>(AppUserDto.class));
	}
	
	public List<AppUserDto> getAlllUsers(String sql) {
		return this.jdbc.query(sql, new BeanPropertyRowMapper<AppUserDto>(AppUserDto.class));
	}

	public AppUserDto getUserById(int id) {
		System.out.println("ID: " + id);
		String sql = "select * from user_record where id=?";
		return (AppUserDto) this.jdbc.queryForObject(sql, new Object[] { Integer.valueOf(id) },
				new BeanPropertyRowMapper<AppUserDto>(AppUserDto.class));
	}

	public String findUsernameById(int id) {
		String sql = "SELECT username FROM user_record WHERE ID = ?";
		return (String) this.jdbc.queryForObject(sql, new Object[] { Integer.valueOf(id) }, String.class);
	}

	public Integer getTotalUserCount() {
		String sql = "SELECT COUNT(*) FROM user_record";
		return (Integer) this.jdbc.queryForObject(sql, Integer.class);
	}
	
	public int getTotalUserRecordCount(String sql) {
		return this.jdbc.queryForObject(sql, Integer.class);
	}
	
	public static void main(String[] args) {
		
	}
}

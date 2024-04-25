package com.test.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppJdbcTemplate {

	@Autowired
	private JdbcTemplate jdbc;

	public void dissable_ONLY_FULL_GROUP_BY() {
		jdbc.execute("SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));");
	}
	
	public int executeUpdateSQL(String sql) {
		return jdbc.update(sql);
	}
	
	public <T> List<T> getRecordsByGivenSelectSQL(String sql, Class<T> classType) {
		return jdbc.query(sql, new BeanPropertyRowMapper<T>(classType));
	}
	
	public Integer getRecordCountByGivenSQL(String sql) {
		return jdbc.queryForObject(sql, Integer.class);
	}
	
	public Long getRecordIdByGivenSQL(String sql) {
		return jdbc.queryForObject(sql, Long.class);
	}

}

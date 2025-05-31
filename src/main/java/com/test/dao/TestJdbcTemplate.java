package com.test.dao;

import com.test.modal.AppUserDto;
import com.test.modal.TestData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TestJdbcTemplate {

	@Autowired
	private JdbcTemplate jdbc;

//		jdbc.setFetchSize(10);
//		jdbc.getFetchSize();
//		jdbc.setMaxRows(10);
//		jdbc.getMaxRows();
//		Map<String, Object> queryForMap(String sql, @Nullable Object... args)
//		<T> List<T> queryForList(String sql, Class<T> elementType, @Nullable Object... args)

	public boolean addTestData(TestData data) {
		try {
//			execute: should used to DDL, return void is return succeeds i.e create table
//			update: used for DLM, returns how many rows have been added/changed/deleted
//			query: execute select query, return resultset
			this.jdbc.execute("INSERT INTO TestB(testName, test_address, test_city, test_state, testA_Id, created_on) " +
					"VALUES('" + data.getTestName() + "','" + data.getTestAddress() + "','" + data.getTestCity()
					+ "','" + data.getTestState() + "','" + data.getTestAId() +"', NOW())");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean addTestData2(TestData data) {
		int rowUpdated = 0;
		try {
			PreparedStatementSetter ps = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, data.getTestName());
					ps.setString(2, data.getTestAddress());
					ps.setString(3, data.getTestCity());
					ps.setString(4, data.getTestState());
					ps.setInt(5, data.getTestAId());
					ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				}
			};
			String sql = "INSERT INTO TestB(testName, test_address, test_city, test_state, testA_Id, created_on) VALUES(?,?,?,?,?,?)";
			rowUpdated = this.jdbc.update(sql, ps);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rowUpdated > 0;
	}

	public boolean updateTestData(TestData data) {
		int rowUpdated = 0;
		try {
			PreparedStatementSetter ps = new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					ps.setString(1, data.getTestName());
					ps.setString(2, data.getTestAddress());
					ps.setInt(3, data.getId());
				}
			};
			String sql = "UPDATE TestB SET testName=?,test_address=? WHERE id=?";
			rowUpdated = this.jdbc.update(sql, ps);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rowUpdated > 0;
	}

	public boolean deleteTestData(int ids[]) {
		int rowDeleted = 0;
		try {
			String strIds = Arrays.stream(ids).mapToObj(String::valueOf).collect(Collectors.joining(","));
			String sql = "DELETE FROM TestB WHERE id IN("+strIds+")";
			rowDeleted = this.jdbc.update(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rowDeleted > 0;
	}

	public Integer countTestRocords() {
		String sql = "SELECT COUNT(*) FROM TestB";
		return this.jdbc.queryForObject(sql, Integer.class);
	}

	public TestData getTestDataById(int id) {
		String sql = "SELECT * FROM TestB WHERE id=?";
		return this.jdbc.queryForObject(sql, new TestDataRowMapper(), new Object[] { id });
	}

	public List<TestData> getAllTestDataSQLIn(List<Integer> ids) {
		String strIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
		String sql = "SELECT * FROM TESTB WHERE id IN("+strIds+")";
		return this.jdbc.query(sql, new BeanPropertyRowMapper<>(TestData.class));

//		String sql2 = "SELECT * FROM TESTB WHERE id IN (" +
//				String.join(",", Collections.nCopies(ids.size(), "?")) + ")";
//		List<Map<String, Object>> listMap = jdbc.queryForList(sql2, ids.toArray());
//		return listMap.stream().map(EntryToTestDataMapper::map).collect(Collectors.toList());
	}

	public List<TestData> getAllTestDataSorted() {
//		Fetch first record
		String sql = "SELECT b.* FROM TESTB b INNER JOIN TESTA a ON a.id=b.testa_id ORDER BY b.created_on ASC LIMIT 1";
		return this.jdbc.query(sql, new BeanPropertyRowMapper<>(TestData.class));
	}

	public List<TestData> getAllTestDataList() {
		String sql = "SELECT * FROM TestB";
//		return this.jdbc.queryForList(sql, TestData.class);	//Not Working
//		return this.jdbc.query(sql, new BeanPropertyRowMapper<>(TestData.class));	//OK
		return this.jdbc.query(sql, new TestDataRowMapper());
	}

	public List<AppUserDto> getAllAppUserList() {
		String sql = "SELECT * FROM AppUser";
//		return this.jdbc.queryForList(sql, AppUserDto.class);	//Not Working
		return this.jdbc.query(sql, new BeanPropertyRowMapper<>(AppUserDto.class));
	}

}

class EntryToTestDataMapper {
	public static TestData map(Map<String, Object> map) {
		TestData data = new TestData();
		if(map.containsKey("id")) data.setId(Integer.valueOf(map.get("id").toString()));
		if(map.containsKey("testName")) data.setTestName(map.get("testName").toString());
		if(map.containsKey("test_address")) data.setTestAddress(map.get("test_address").toString());
		if(map.containsKey("test_city")) data.setTestCity(map.get("test_city").toString());
		if(map.containsKey("test_state")) data.setTestState(map.get("test_state").toString());
		if(map.containsKey("testA_Id")) data.setTestAId(Integer.valueOf(map.get("testA_Id").toString()));
		if(map.containsKey("created_on")) data.setCreatedOn(LocalDateTime.parse(map.get("created_on").toString()));
		return data;
	}
}

class TestDataRowMapper implements RowMapper<TestData> {
	@Override
	public TestData mapRow(ResultSet rs, int rowNum) throws SQLException {
		TestData data = new TestData();
		data.setId(rs.getInt("id"));
		data.setTestName(rs.getString("testName"));
		data.setTestAddress(rs.getString("test_address"));
		data.setTestCity(rs.getString("test_city"));
		data.setTestState(rs.getString("test_state"));
		data.setTestAId(rs.getInt("testA_Id"));
		data.setCreatedOn(rs.getTimestamp("created_on").toLocalDateTime());
		return data;
	}
}
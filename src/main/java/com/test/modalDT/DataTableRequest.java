package com.test.modalDT;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.test.modal.AppUserDto;

public class DataTableRequest {
	
	private String uniqueId;
	private Integer draw = 1;	//Serial Number to perform functions on datatable 
	private Integer pageStart = 0;		//Records start from
	private Integer pageLength = 10;	//Page length
	private Integer sortColumnIndex;	//Which column need to sort
	private String sortColumnName;		//Which column need to sort
	private String sortColumnDir;		//Sorting direction like: ASC/DESC
	private String selectSQL = "";		//SQL syntax in case of search
	private String globalSearchTerm = "";	//Searched text
	private String globalSearchSQL = "";	//Searched text
	private String orderBySQL = "";			//SQL syntax for sorting
	private String totalRecordsLabel = "totalRecords";

	public DataTableRequest(HttpServletRequest request, String[] columnNames, String entityName) {
		prepareDataTableRequest(request, columnNames, entityName);
	}

	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getPageStart() {
		return pageStart;
	}
	public void setPageStart(Integer pageStart) {
		this.pageStart = pageStart;
	}
	public Integer getPageLength() {
		return pageLength;
	}
	public void setPageLength(Integer pageLength) {
		this.pageLength = pageLength;
	}
	public Integer getSortColumnIndex() {
		return sortColumnIndex;
	}
	public void setSortColumnIndex(Integer sortColumnIndex) {
		this.sortColumnIndex = sortColumnIndex;
	}
	public String getSortColumnName() {
		return sortColumnName;
	}
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}
	public String getSortColumnDir() {
		return sortColumnDir;
	}
	public void setSortColumnDir(String sortColumnDir) {
		this.sortColumnDir = sortColumnDir;
	}
	public String getSelectSQL() {
		return selectSQL;
	}
	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}
	public String getGlobalSearchTerm() {
		return globalSearchTerm;
	}
	public void setGlobalSearchTerm(String globalSearchTerm) {
		this.globalSearchTerm = globalSearchTerm;
	}
	public String getGlobalSearchSQL() {
		return globalSearchSQL;
	}
	public void setGlobalSearchSQL(String globalSearchSQL) {
		this.globalSearchSQL = globalSearchSQL;
	}
	public String getOrderBySQL() {
		return orderBySQL;
	}
	public void setOrderBySQL(String orderBySQL) {
		this.orderBySQL = orderBySQL;
	}
	public String getTotalRecordsLabel() {
		return totalRecordsLabel;
	}

	/**
	 * Extract data from request for Datatable process
	 * @param request
	 * @param columnNames
	 */
	private void prepareDataTableRequest(HttpServletRequest request, String[] columnNames, String entityName) {

		uniqueId = request.getParameter("_");
		draw = Integer.parseInt(request.getParameter("draw"));
		pageStart = Integer.parseInt(request.getParameter("start"));
		pageLength = Integer.parseInt(request.getParameter("length"));
		
		sortColumnDir = request.getParameter("order[0][dir]");
		sortColumnIndex = Integer.parseInt(request.getParameter("order[0][column]"));
		sortColumnName = columnNames[sortColumnIndex];
		globalSearchTerm = request.getParameter("search[value]");
		
		if(entityName.equals(AppUserDto.class.getSimpleName())) {
			createAppUserSQL(columnNames);
		}else {
			StringBuilder selectSQLBuilder = new StringBuilder("SELECT ");
			for(int x=0; x<columnNames.length; x++) {
				selectSQLBuilder.append(columnNames[x]+",");
			}
			String countSQL = "SELECT COUNT(1) FROM "+entityName;
			selectSQLBuilder.append("("+countSQL+") AS "+totalRecordsLabel+ " FROM "+entityName);
			String whereSQL = " WHERE deleted=false";
			whereSQL = whereSQL + getLIKE_SQL(columnNames);
			
			globalSearchSQL = countSQL + whereSQL;
			selectSQL = selectSQLBuilder.toString() + whereSQL;
		}
		orderBySQL = " ORDER BY " +sortColumnName+ " " +sortColumnDir+ " LIMIT " +pageStart+ ", " +pageLength;
	}
	
	private void createAppUserSQL(String[] columnNames) {
		String whereSQL = " WHERE u.deleted=false";
		String countSQL = "SELECT COUNT(1) FROM AppUser AS u" +whereSQL;
		String sql = "SELECT u.id,u.phoneNumberFull,u.email,u.walletAmount,u.status,u.createdOn,"
			+ "(SELECT COUNT(c.id) FROM CreditCard2 AS c WHERE c.userId=u.id) AS cards,"
			+ "(SELECT SUM(r.totalAmount) FROM Rentals AS r WHERE r.userId=u.id AND r.paymentStatus=0) AS sales,"
			+ "("+countSQL+") AS totalRecords FROM AppUser AS u";
		String likeSQL = getLIKE_SQL(removeExtraColumns(columnNames));
		globalSearchSQL = countSQL + likeSQL;
		selectSQL = sql + whereSQL + likeSQL;
	}
	
	private void createStationSQL(String[] columnNames) {
		String whereSQL = " WHERE s.deleted=false";
		String countSQL = "SELECT COUNT(1) FROM Station AS s INNER JOIN Location AS l ON l.id=s.locationId" +whereSQL;
		String sql = "SELECT s.id,s.stationNumber,s.status,s.type,s.powerbankSlots,s.imagePath,l.id as locationId,l.name as locationName,"
			+ "(SELECT COUNT(1) FROM Powerbank AS p WHERE p.status=0 AND p.stationId=s.id) AS batteries,"
			+ "("+countSQL+") AS totalRecords FROM Station AS s INNER JOIN  Location AS l ON l.id=s.locationId" +whereSQL;
		String likeSQL = getLIKE_SQL(removeExtraColumns(columnNames));
		globalSearchSQL = countSQL + likeSQL;
		selectSQL = sql + likeSQL;
	}
	
	private String getLIKE_SQL(String[] columnNames) {
		StringBuilder whereSQLBuilder = new StringBuilder("");
		if (!globalSearchTerm.isEmpty()) {
			for(int x=0; x<columnNames.length; x++) {
				if(x>0) {
					whereSQLBuilder.append(" OR "+columnNames[x]+" LIKE '%" +globalSearchTerm+ "%'");
				}else {
					whereSQLBuilder.append(" AND ("+columnNames[x]+" LIKE '%" +globalSearchTerm+ "%'");
				}
			}
			whereSQLBuilder.append(")");
		}
		return whereSQLBuilder.toString();
	}
	private String[] removeExtraColumns(String[] columnNames) {
		List<String> list = new ArrayList<>();
		for(String ss: columnNames) {
			if(ss.contains("status") || ss.equals("sales")) {
				continue;
			}
			else if(ss.equals("employeeName")) {
				list.add("firstname");
				list.add("lastname");
			}else {
				list.add(ss);
			}
		}
		String[] columnNames22 = new String[list.size()];
		return list.toArray(columnNames22);
	}
	
}

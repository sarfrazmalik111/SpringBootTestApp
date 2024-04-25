package com.test.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class DataTableProcessor {

	public int INITIAL = 0;
	public int RECORD_SIZE = 10;
	public int TOTAL_RECORDS = -1;
	public String COLUMN_NAME = "";
	public String DIRECTION = "";
	public String GLOBAL_SEARCH_TERM = "";
	public String GLOBAL_SEARCH_SQL = "";
	public String ORDER_BY_SQL = "";
	
	private void resetDataTable() {
		INITIAL = 0;
		RECORD_SIZE = 10;
		TOTAL_RECORDS = -1;
		COLUMN_NAME = "";
		DIRECTION = "";
		GLOBAL_SEARCH_TERM = "";
		GLOBAL_SEARCH_SQL = "";
		ORDER_BY_SQL = "";
	}
	
	public void setRequestDataIntoDataTable(HttpServletRequest request, String[] columnNames) {
		resetDataTable();
		int start = 0;
		int columnIndex = 0;
		String dir = "asc";
		String pageNo = request.getParameter("iDisplayStart");
		String pageSize = request.getParameter("iDisplayLength");
		String colIndex = request.getParameter("iSortCol_0");
		String sortDirection = request.getParameter("sSortDir_0");
		String globalSearch = request.getParameter("sSearch");

		if (pageNo != null) {
			start = Integer.parseInt(pageNo);
			if (start < 0) start = 0;
		}
		if (colIndex != null) {
			columnIndex = Integer.parseInt(colIndex);
			if (columnIndex < 0 || columnIndex >= columnNames.length) columnIndex = 0;
		}
		if (sortDirection != null) {
			if (!sortDirection.equals("asc")) dir = "desc";
		}
		String columnName = columnNames[columnIndex];

		INITIAL = start;
		RECORD_SIZE = Integer.parseInt(pageSize);;
		COLUMN_NAME = columnName;
		DIRECTION = dir;
		GLOBAL_SEARCH_TERM = globalSearch;
		
		if (!GLOBAL_SEARCH_TERM.isEmpty()) {
			int x=0;
			StringBuilder searchSQL = new StringBuilder("WHERE");
			for(String column: columnNames) {
				if(x>0) {
					searchSQL.append(" OR "+column+" LIKE '%" +GLOBAL_SEARCH_TERM+ "%'");
				}else {
					searchSQL.append(" "+column+" LIKE '%" +GLOBAL_SEARCH_TERM+ "%'");
				}
				x++;
			}
			GLOBAL_SEARCH_SQL = searchSQL.toString();
		}
		ORDER_BY_SQL = " order by " +COLUMN_NAME+ " " +DIRECTION+ " limit " +INITIAL+ ", " +RECORD_SIZE;
	}

}

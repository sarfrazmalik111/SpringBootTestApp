package com.test.modalDT;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class DataTableResults {
	
	private Integer draw = 1;			//Serial Number to perform functions on datatable
	private Integer recordsTotal = 0;	//Total records exists into database
	private Integer recordsFiltered = 0;	//How many records filtered/find after search
	List<JSONObject> data = new ArrayList<>();	// The list of data objects.
	
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public Integer getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public List<JSONObject> getData() {
		return data;
	}
	public void setData(List<JSONObject> data) {
		this.data = data;
	}
	
}


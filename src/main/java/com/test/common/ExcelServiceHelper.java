package com.test.common;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.FileInputStream;
import java.util.Iterator;

@Service
@Transactional
public class ExcelServiceHelper {

	private final String trackSheetFilePath = "/Users/apple/Desktop/AAA/AAA_C91_Track_Sheet.xlsx";
	private Logger logger = LoggerFactory.getLogger(ExcelServiceHelper.class);

	private void getRecordsFromTrackerSheet(String trackSheetFilePath) {
		try {
			FileInputStream file = new FileInputStream(trackSheetFilePath);
			Workbook workbook = new XSSFWorkbook(file);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() < 2) continue;
				if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) break;
				for (Cell cell : row) {
					System.out.println(getCellValue(cell));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String getCellValue(Cell cell) {
		String cellValue = null;
		try {
			if(cell.getCellType() == CellType.STRING) {
				cellValue = cell.getStringCellValue();
			} else if(cell.getCellType() == CellType.NUMERIC) {
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
			return cellValue;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return cellValue;
	}

}

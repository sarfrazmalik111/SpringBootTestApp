package com.test.web;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.test.common.MyUtility;
import com.test.modalDT.PersonAddressDT;
import com.test.modalDT.PersonDT;
import com.test.modalDT.UserDT;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/import-export")
public class ImportExportController {

	@GetMapping("/export-json-Report")
    public void exportJsonReport(HttpServletRequest request, HttpServletResponse response) {
		try {
            List<PersonDT> personList = new ArrayList<>();
            
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

            PersonDT person1 = new PersonDT();
            person1.setFirstName("Sarfraz");
            person1.setLastName("Malik");
            person1.setAge(30);
            person1.setAddress(new PersonAddressDT("Naagal", "Roorkee", "247667"));
            person1.setCars(new String[]{"BMW", "Honda City", "Farrari"});

            PersonDT person2 = new PersonDT();
            person2.setFirstName("Salman");
            person2.setLastName("Malik");
            person2.setAge(30);
            person2.setAddress(new PersonAddressDT("Rampur", "Roorkee", "247667"));
            person2.setCars(new String[]{"BMW", "Honda City", "Farrari"});
            personList.add(person1);
            personList.add(person2);

//            OutputStream outputStream = new FileOutputStream(System.getProperty("user.home") + "/Desktop/persons.json");
//            mapper.writeValue(outputStream, person1);
//            writer.writeValue(outputStream, personList);
            

            String outFileName = "PersonList-" + MyUtility.getTodayDate_ForFileName();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + outFileName + ".json");
            ByteArrayInputStream stream = new ByteArrayInputStream(writer.writeValueAsBytes(personList));
            IOUtils.copy(stream, response.getOutputStream());
            System.out.println("PersonList Exported...");
        }catch (Exception ex){
            ex.printStackTrace();
        }
	}

	@ResponseBody
	@PostMapping("/import-json-Report")
    public Object importJsonReport(@RequestParam("attachFile") MultipartFile attachFile, HttpServletRequest request) {
		List<PersonDT> persons = null;
		try {
//          File personFile = new File(System.getProperty("user.home") + "/Desktop/persons.json");
//			InputStream inputStream = new FileInputStream(personFile);
            ObjectMapper mapper = new ObjectMapper();
            
            InputStream inputStream = attachFile.getInputStream();
            TypeReference<List<PersonDT>> typeReference = new TypeReference<List<PersonDT>>() {};
//            Person person = mapper.readValue(inputStream, Person.class);
            persons = mapper.readValue(inputStream, typeReference);
            for (PersonDT person: persons){
                System.out.println(person.getFirstName()+" "+person.getLastName()+" : "+ person.getAddress().getCity()+" : "+ person.getCars()[0]);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
		return persons;
	}

    @GetMapping("/export-excel-Report")
    public void exportExcelReport(HttpServletRequest request, HttpServletResponse response) {
        List<UserDT> userList = new ArrayList<>();
        userList.add(new UserDT(1l, "Sarfraz", "sarfraz@gmail.com", "Naagal"));
        userList.add(new UserDT(2l, "Malik", "malik@gmail.com", "Naagal"));

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("UserList");
        sheet.setColumnWidth(0, 15*256);
        sheet.setColumnWidth(1, 20*256);
        sheet.setColumnWidth(2, 50*256);
        sheet.setColumnWidth(3, 20*256);

        final HSSFFont font = sheet.getWorkbook ().createFont ();
        font.setFontName ("Arial");
        font.setBold(true);

        final CellStyle style = sheet.getWorkbook ().createCellStyle ();
        style.setFont(font);
        style.setWrapText(true);

        HSSFRow rowhead = sheet.createRow((short) 0);
        HSSFCell cell1 = rowhead.createCell(0);
        cell1.setCellValue("ID");
        cell1.setCellStyle(style);
        HSSFCell cell2 = rowhead.createCell(1);
        cell2.setCellValue("Name");
        cell2.setCellStyle(style);
        HSSFCell cell3 = rowhead.createCell(2);
        cell3.setCellValue("Email");
        cell3.setCellStyle(style);
        HSSFCell cell4 = rowhead.createCell(3);
        cell4.setCellValue("Address");
        cell4.setCellStyle(style);
        short index = 1;
        for (UserDT user: userList) {
            HSSFRow row = sheet.createRow(index);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getUserName());
            row.createCell(2).setCellValue(user.getEmail());
            row.createCell(3).setCellValue(user.getAddress());
            index++;
        }
        try{
            DateFormat dateFormatter = new SimpleDateFormat("MM-dd-yyyy");
            String currentDate = dateFormatter.format(new Date());
            String reportFileName = "UserList-" + currentDate;
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename="+reportFileName+".xls");
            ByteArrayInputStream stream = new ByteArrayInputStream(outputStream.toByteArray());
            IOUtils.copy(stream, response.getOutputStream());
            System.out.println("Excel Report created...");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }

    private String parseColumnValue(HSSFCell cell, int dataType){
        String data = "";
        CellType cellType = cell.getCellType();
        if(cellType == CellType.NUMERIC) data = String.valueOf(cell.getNumericCellValue());
        else if(cellType == CellType.BOOLEAN) data = String.valueOf(cell.getBooleanCellValue());
        else data = cell.getStringCellValue();
        if(data == null || data.isEmpty()){  //String: 0, Integer: 1, Double: 2, Boolean: 3
            if(dataType == 1) data = "0";
            else if(dataType == 2) data = "0.0";
            else if(dataType == 3) data = "false";
            else data = "";
        }
        return data;
    }

}

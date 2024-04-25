package com.test.common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadUtils {

    @Autowired
    private MyUtility myUtility;
    private static int MAX_IMAGE_SIZE = 500;
    private static final String OUT_FILE_HOME_DIR = "/mobile_store/";
    private Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

    private String getCATALINA_BASE_DIR() {
        String baseURL = myUtility.getBaseURL();
//        if(baseURL.contains("localhost"))   return "/Users/apple/Desktop/AAA";
//        else    return System.getProperty("catalina.base")+"/webapps";
        return System.getProperty("catalina.base")+"/webapps";
    }

    public static String getFileNameFromFilePath(String filePath) {
        if(filePath != null && filePath.contains("/"))
            return filePath.substring(filePath.lastIndexOf("/")+1);
        else
            return filePath;
    }

    public String getFileExtension(String fileName) {
        if(fileName != null && fileName.contains("."))
            return fileName.substring(fileName.lastIndexOf("."));
        else
            return fileName;
    }

    public Boolean validateFileSizeMax2MB(MultipartFile multipartFile){
        if (multipartFile != null || !multipartFile.isEmpty()) {
            return (double)multipartFile.getSize()/(1024*1024) < 2;
        }
        return true;
    }

    public Boolean validateImageType(File myFile){
        if (myFile == null) return false;
        Boolean isValid = false;
        if(myFile.getName().substring(myFile.getName().lastIndexOf(".")+1).toLowerCase().matches("png|jpg|jpeg|gif")) {
            isValid = true;
        }
        return isValid;
    }

    public Boolean validateImageType(MultipartFile multipartFile){
        if (multipartFile == null || multipartFile.isEmpty()) return false;
        Boolean isValid = false;
        String contentType = multipartFile.getContentType();
        if(contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg")
                || contentType.equals("image/gif")) {
            isValid = true;
        }
        return isValid;
    }

    public Boolean validateDocFileType(MultipartFile multipartFile){
        if (multipartFile == null || multipartFile.isEmpty()) return false;
        Boolean isValid = false;
        String contentType = multipartFile.getContentType();
        if(contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg")
                || contentType.equals("image/gif") || contentType.equals("application/pdf")) {
            isValid = true;
        }
        return isValid;
    }

    public Boolean validateDocFileTypeOptional(MultipartFile multipartFile){
        if (multipartFile == null || multipartFile.isEmpty()) return true;
        Boolean isValid = false;
        String contentType = multipartFile.getContentType();
        if(contentType.equals("image/png") || contentType.equals("image/jpg") || contentType.equals("image/jpeg")
                || contentType.equals("image/gif") || contentType.equals("application/pdf")) {
            isValid = true;
        }
        return isValid;
    }

    public String uploadFile(MultipartFile multipartFile) {
        String outFilePath = null;
        if(multipartFile.isEmpty()) return outFilePath;
        String fileName = multipartFile.getOriginalFilename();
        String outFileName = myUtility.getSecondsFromTodayDateTime() + getFileExtension(fileName);
        System.out.println("--------Uploading-document--------: " + fileName);
        try {
            File outputFile = new File(getCATALINA_BASE_DIR() + OUT_FILE_HOME_DIR + outFileName);
            if(!outputFile.getParentFile().exists()){
                if(!outputFile.getParentFile().getParentFile().exists()){
                    outputFile.getParentFile().getParentFile().mkdir();
                }
                outputFile.getParentFile().mkdir();
            }
            System.out.println("--------outputFile--------: " + outputFile);
            outputFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            outFilePath = OUT_FILE_HOME_DIR + outFileName;

            // Get the file and save it somewhere
//            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
//            Files.write(path, multipartFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outFilePath;
    }

    public String getBase64StringFromFile(File docFile) throws IOException {
        return Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(docFile.getAbsolutePath())));
    }
    public String getBase64StringFromFile(MultipartFile docFile) throws IOException {
        return Base64.getEncoder().encodeToString(docFile.getBytes());
    }

    public static void main(String[] args) throws IOException {
        FileUploadUtils fileUploadUtils = new FileUploadUtils();
        String base64String = fileUploadUtils.getBase64StringFromFile(new File("/Users/apple/Desktop/IMAGES/user1.png"));
        System.out.println(base64String);
        System.out.println(base64String.length());

    }
}

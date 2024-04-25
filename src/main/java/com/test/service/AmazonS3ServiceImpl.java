package com.test.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.test.common.AppConstants;
import com.test.common.MyUtility;
import com.test.modalDT.FilePathModel;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

	@Autowired
	private MyUtility myUtils;
	@Autowired
    private AmazonS3 amazonS3;
	private final String ec2TestDir = "/home/ec2-user/test/";

	@Async
	@Override
	public FilePathModel uploadImage(MultipartFile multipartFile, String dirName) throws IOException {
		FilePathModel fileData = new FilePathModel();
		String origFileName = multipartFile.getOriginalFilename();
		String serverS3Path = getUploadFileLocation(dirName);
//		String outFileName = myUtils.getTodayDate_ForFileName() + origFileName.substring(origFileName.lastIndexOf("."));
		String outFileName = origFileName;
		System.out.println("Uploaded FileName: " + origFileName);
	    try {
	    	File outputFile = new File(ec2TestDir+outFileName);
	    	if(!outputFile.getParentFile().exists()) {
	    		outputFile = new File(outFileName);
	    	}
	    	System.out.println("---FILE----"+outputFile.getPath());
	    	outputFile.createNewFile();
		    FileOutputStream fos = new FileOutputStream(outputFile);
		    fos.write(multipartFile.getBytes());
		    fos.close();
		    
		    amazonS3.putObject(new PutObjectRequest(AppConstants.S3_BUCKET_NAME + serverS3Path, outFileName, outputFile)
		            .withCannedAcl(CannedAccessControlList.PublicRead));
		    fileData.setImagePath(serverS3Path +File.separator+ outFileName);
		    outputFile.delete();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
		return fileData;
	}
	
	private String getUploadFileLocation(String dirName) {
		String outFilePath = AppConstants.S3_PARENT_FOLDER;
		if (dirName.equals(AppConstants.EMPLOYEES_DIR) || dirName.equals(AppConstants.USERS_DIR)) {
			outFilePath = outFilePath + dirName;
		} else {
//			outFilePath = outFilePath + AppConstants.TEST_DIR;
		}
		return outFilePath;
	}
	
	private String deleteFileFromS3Bucket() {
	    String fileName = "user1.png";
	    amazonS3.deleteObject(new DeleteObjectRequest(AppConstants.S3_BUCKET_NAME, fileName));
//	    amazonS3.deleteObject(new DeleteObjectRequest(AppConstants.S3_BUCKET_NAME + "/AAFitnessAppFiles", fileName));
	    return "Successfully deleted";
	}
}

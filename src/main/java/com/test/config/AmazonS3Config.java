package com.test.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.test.common.AppConstants;

@Configuration
public class AmazonS3Config {

	@Value("${amazonS3.accessKey}")
	private String accessKey;
	@Value("${amazonS3.secretKey}")
	private String secretKey;
	@Value("${amazonS3.endpointUrl}")
	private String endpointUrl;
	@Value("${amazonS3.bucketName}")
	private String bucketName;
	@Value("${amazonS3.regionName}")
    private String regionName;
	
	@Autowired
    private AmazonS3 amazonS3;

	@Bean
    public AmazonS3 getAmazonS3Cient() {
		AppConstants.S3_ENDPOINT_URL = endpointUrl;
		AppConstants.S3_BUCKET_NAME = bucketName;
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        // Get AmazonS3 client and return the s3Client object.
        return AmazonS3ClientBuilder.standard().withRegion(Regions.fromName(regionName))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
    }
	
	public void uploadFileOnAWS(String amazonS3Path, File outputFile) {
		amazonS3.putObject(new PutObjectRequest(amazonS3Path, outputFile.getName(), outputFile)
	            .withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public void readS3File(String filePath) throws IOException {
		System.out.println("------------readS3File------------");
		System.out.println(filePath);
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);

		S3Object object = amazonS3.getObject (new GetObjectRequest(bucketName, fileName));
		InputStream objectData = object.getObjectContent();
		File targetFile = new File("/Users/apple/Desktop/zTargetFile.png");
		FileUtils.copyInputStreamToFile(objectData, targetFile);
	}

}
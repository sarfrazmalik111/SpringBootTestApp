package com.test.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.test.modalDT.FilePathModel;

public interface AmazonS3Service {

	FilePathModel uploadImage(MultipartFile mpartFile, String dirName) throws IOException;
}

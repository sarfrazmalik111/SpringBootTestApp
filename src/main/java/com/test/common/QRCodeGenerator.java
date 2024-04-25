package com.test.common;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.test.config.AmazonS3Config;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QRCodeGenerator {
	
	@Autowired
    private AmazonS3Config amazonService;
	
	public String createQRCodeImage(String qrCodeText) throws WriterException, IOException {
    	int size = 300;
    	String outFileName = qrCodeText +".png";
    	String amazonS3Path = AppConstants.S3_PARENT_FOLDER + AppConstants.QRCODES_DIR;
    	File outputFile = new File(AppConstants.EC2_TEST_FOLDER + outFileName);
		if(!outputFile.getParentFile().exists()) {
    		outputFile = new File(outFileName);
    	}
//		outputFile.createNewFile();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size);
        
        Path path = FileSystems.getDefault().getPath(outputFile.getAbsolutePath());
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        amazonService.uploadFileOnAWS(AppConstants.S3_BUCKET_NAME + amazonS3Path, outputFile);
        outputFile.delete();
        return amazonS3Path +File.separator+ outFileName;
    }

	private String decodeQRCode(String filePath) throws WriterException, IOException {
		BufferedImage bufferedImage = ImageIO.read(new File(filePath));
		BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(bufferedImageLuminanceSource));
		String qrCodeText = null;
		try {
			Result result = (new MultiFormatReader()).decode(bitmap);
			qrCodeText = result.getText();
		} catch (NotFoundException ex) {
			ex.printStackTrace();
		}
		return qrCodeText;
	}

}

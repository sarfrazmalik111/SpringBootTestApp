package com.test.common;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class ImageResizer {

	private static int THUMBNAIL_SIZE = 100;
	private static int MESSAGE_SIZE = 500;
	private static int FULL_IMAGE_SIZE = 800;
	private static int LARGEST_SIZE = 1000;
	private static String THUMBNAIL_DIR_NAME = "thumbnails";
	
	public void resizeImageMax800PX(File inputFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputFile);
        BufferedImage resizedImage = resizeImage(originalImage, FULL_IMAGE_SIZE);
        if(resizedImage != null) {
	        String fileType = inputFile.getName().substring(inputFile.getName().lastIndexOf(".")+1);
	        ImageIO.write(resizedImage, fileType, inputFile);
        }
	}

	public void resizeImageMax500PX(File inputFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputFile);
        BufferedImage resizedImage = resizeImage(originalImage, MESSAGE_SIZE);
        if(resizedImage != null) {
	        String fileType = inputFile.getName().substring(inputFile.getName().lastIndexOf(".")+1);
	        ImageIO.write(resizedImage, fileType, inputFile);
        }
	}

	public String createThumbnailImage(File inputFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(inputFile);
        BufferedImage resizedImage = resizeImage(originalImage, THUMBNAIL_SIZE);

        String thumbFilePath = inputFile.getParent() + File.separator + THUMBNAIL_DIR_NAME + File.separator + inputFile.getName();
        String fileType = inputFile.getName().substring(inputFile.getName().lastIndexOf(".")+1);
        ImageIO.write(resizedImage, fileType, new File(thumbFilePath));
		return thumbFilePath;
	}

	private static BufferedImage resizeImage(BufferedImage buffImage, int SAMPLE_SIZE) {
		int NEW_WIDTH = 0;
		int NEW_HEIGHT = 0;
		BufferedImage resizedImage = null;
		if(SAMPLE_SIZE == THUMBNAIL_SIZE) {
			NEW_HEIGHT = SAMPLE_SIZE;
			NEW_WIDTH = (NEW_HEIGHT*buffImage.getWidth())/buffImage.getHeight();
			int imageType = buffImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : buffImage.getType();
	        Image newImage = buffImage.getScaledInstance(NEW_WIDTH, NEW_HEIGHT, Image.SCALE_SMOOTH);
	        resizedImage = new BufferedImage(NEW_WIDTH, NEW_HEIGHT, imageType);
	        Graphics2D g2d = resizedImage.createGraphics();
	        g2d.drawImage(newImage, 0, 0, null);
	        g2d.dispose();
		}else {
			int LARGEST_SIZE_NEW = SAMPLE_SIZE == MESSAGE_SIZE ? MESSAGE_SIZE : LARGEST_SIZE;
			if(buffImage.getWidth()>LARGEST_SIZE_NEW || buffImage.getHeight()>LARGEST_SIZE_NEW) {
				if(buffImage.getWidth() > buffImage.getHeight()) {
					NEW_WIDTH = SAMPLE_SIZE;
					NEW_HEIGHT = (NEW_WIDTH*buffImage.getHeight())/buffImage.getWidth();
				}else {
					NEW_HEIGHT = SAMPLE_SIZE;
					NEW_WIDTH = (NEW_HEIGHT*buffImage.getWidth())/buffImage.getHeight();
				}
				int imageType = buffImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : buffImage.getType();
		        Image newImage = buffImage.getScaledInstance(NEW_WIDTH, NEW_HEIGHT, Image.SCALE_SMOOTH);
		        resizedImage = new BufferedImage(NEW_WIDTH, NEW_HEIGHT, imageType);
		        Graphics2D g2d = resizedImage.createGraphics();
		        g2d.drawImage(newImage, 0, 0, null);
		        g2d.dispose();
			}
		}
        return resizedImage;
    }

}

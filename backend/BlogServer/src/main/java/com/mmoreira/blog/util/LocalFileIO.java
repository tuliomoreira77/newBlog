package com.mmoreira.blog.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("LocalFileIO")
public class LocalFileIO implements FileIO{
	@Value("${file.system.base.path}")
	String basePath;

	private String getFilePath() {
		File dir = new File(basePath);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		return basePath;
	}

	@Override
	public byte[] getFile(String path) throws IOException {
		InputStream inputStream = new FileInputStream(new File(path));
		return inputStream.readAllBytes();
	}

	@Override
	public void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}

	@Override
	public String saveFile(byte[] image) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(image);
			BufferedImage buffedImage = ImageIO.read(bais);
			String filePath = getFilePath() + UUID.randomUUID() + ".png";
			ImageIO.write(buffedImage, "png", new File(filePath));
			
			return filePath;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

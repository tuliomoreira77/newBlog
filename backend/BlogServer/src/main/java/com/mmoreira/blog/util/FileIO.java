package com.mmoreira.blog.util;

public interface FileIO {
	
	String getFilePath(String userName);
	byte[] getFile(String path);
	void deleteFile(String path);
	void saveFile(String path, byte[] image);
	byte[] compressImage(byte[] originalImage);
}

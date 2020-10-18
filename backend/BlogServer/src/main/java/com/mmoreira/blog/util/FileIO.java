package com.mmoreira.blog.util;

import java.io.IOException;

public interface FileIO {
	byte[] getFile(String path) throws IOException;
	void deleteFile(String path);
	String saveFile(byte[] image);
}

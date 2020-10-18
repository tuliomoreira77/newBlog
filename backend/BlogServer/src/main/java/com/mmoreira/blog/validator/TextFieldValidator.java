package com.mmoreira.blog.validator;

public class TextFieldValidator {
	
	public static boolean isNull(String text) {
		if(text != null && !text.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}

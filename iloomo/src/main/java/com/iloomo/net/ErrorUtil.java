package com.iloomo.net;

public class ErrorUtil {
	public static String errorJson(String errorCode, String message) {
		return "{\"code\":\"" + errorCode + "\" ,\"data\":{\"code_message\":\""+message+"\"}}";
	}
}

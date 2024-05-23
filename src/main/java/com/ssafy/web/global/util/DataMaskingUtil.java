package com.ssafy.web.global.util;

public class DataMaskingUtil {
	public static String maskEmail(String email) {
		// 앞 두 자리만 보이게!!
		if (email == null || !email.contains("@")) {
			return email;
		}

		String[] parts = email.split("@");
		String username = parts[0];
		String domain = parts[1];

		if (username.length() <= 2) {
			return "**@" + domain;
		}

		String maskedUsername = username.substring(0, 2) + "*".repeat(username.length() - 2);
		return maskedUsername + "@" + domain;
	}

	public static String maskPhoneNumber(String phoneNumber) {
		// 뒷번호만 보이게!!
		if (phoneNumber == null || phoneNumber.length() < 7) {
			return phoneNumber;
		}

		String prefix = phoneNumber.substring(0, 3);
		String suffix = phoneNumber.substring(phoneNumber.length() - 4);
		return prefix + "****" + suffix;
	}

	public static String maskString(String str, int start, int end) {
		if (str == null || str.length() < end || start < 0 || end <= start) {
			return str;
		}

		StringBuilder masked = new StringBuilder(str);
		for (int i = start; i < end; i++) {
			masked.setCharAt(i, '*');
		}

		return masked.toString();
	}

	public static String maskName(String name){
		if(name == null || name.length() < 2){
			return name;
		}
		return maskString(name, 1, name.length() - 1);
	}
}

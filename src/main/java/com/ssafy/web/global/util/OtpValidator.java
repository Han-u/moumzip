package com.ssafy.web.global.util;

import org.springframework.stereotype.Component;

@Component
public class OtpValidator {
	private static final String SIX_DIGIT_REGEX = "^[0-9]{6}$";

	/*
		확인하는 기준
		1. 6자리 숫자인지
		2. 123456, 654321 등 6자리가 연속된 숫자인지
		3. 000000, 111111 등 6자리가 모두 같은지
		4. 패턴이 반복되는지
		5. 또 있나?? --> 계속 똑같은걸 안쓰도록
	 */

	public boolean isValidPassword(String password) {
		// 6자리 숫자인지 확인
		if (!password.matches(SIX_DIGIT_REGEX)) {
			return false;
		}
		return !isSequential(password) && !isRepeated(password) && !isPatterned(password);
	}

	// 모든 숫자가 같은지 확인
	private boolean isRepeated(String password) {
		char firstChar = password.charAt(0);
		for (int i = 1; i < password.length(); i++) {
			if (password.charAt(i) != firstChar) {
				return false;
			}
		}
		return true;
	}

	// 연속된 숫자인지 확인
	private boolean isSequential(String password) {
		boolean isAscending = true;
		boolean isDescending = true;
		for (int i = 0; i < password.length() - 1; i++) {
			if (password.charAt(i) + 1 != password.charAt(i + 1)) {
				isAscending = false;
			}
			if (password.charAt(i) - 1 != password.charAt(i + 1)) {
				isDescending = false;
			}
		}
		return isAscending || isDescending;
	}

	// 반복되는 패턴인지 확인
	private boolean isPatterned(String password) {
		String pattern1 = password.substring(0, 2);
		String pattern2 = password.substring(0, 3);
		return password.equals(pattern1.repeat(3)) || password.equals(pattern2.repeat(2));
	}
}

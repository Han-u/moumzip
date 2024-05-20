package com.ssafy.web.global.error.exception;

import com.ssafy.web.global.error.ErrorCode;

public class OAuthException extends BusinessException{
	private ErrorCode errorCode;

	public OAuthException(ErrorCode errorCode) {
		super(errorCode);
		this.errorCode = errorCode;
	}
}

package com.ssafy.web.global.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.web.global.error.exception.BusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(BusinessException e) {
		ErrorResponse response = ErrorResponse.of(e.getErrorCode());
		return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
	}
}

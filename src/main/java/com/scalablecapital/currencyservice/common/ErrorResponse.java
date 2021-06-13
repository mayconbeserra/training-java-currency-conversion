package com.scalablecapital.currencyservice.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private String errorCode;
	private String errorMsg;
	private int status;
	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;

	protected ErrorResponse() {
	}

	public static ErrorResponse of(String errorMsg, HttpStatus httpStatus, LocalDateTime timestamp) {
		ErrorResponse error = new ErrorResponse(); 
		error.errorCode = httpStatus.name();
		error.errorMsg = errorMsg;
		error.status = httpStatus.value();
		error.timestamp = timestamp;

		return error;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public int getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

}

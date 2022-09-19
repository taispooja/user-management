package com.usermanagement.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorDetails {
	
	private String message;
	private String details;
	private HttpStatus httpStatus;
    private LocalDateTime timestamp;
    
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public ErrorDetails(HttpStatus httpStatus, String details,String message) {
		this.message = message;
		this.details = details;
		this.httpStatus = httpStatus;
		this.timestamp = LocalDateTime.now();

	}
	

}

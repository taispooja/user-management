package com.usermanagement.exception;

import java.io.IOException;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
		BindingResult result = ex.getBindingResult();
	    List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
	    return processFieldErrors(fieldErrors);
	}

	private ResponseEntity<ErrorDetails> processFieldErrors(
			List<org.springframework.validation.FieldError> fieldErrors) {
		ErrorDetails error = null;
		for (org.springframework.validation.FieldError fieldError : fieldErrors) {
			error = new ErrorDetails(HttpStatus.BAD_REQUEST, "Validation Error", fieldError.getDefaultMessage());
		}
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> handleConstraintViolationException(ConstraintViolationException ex,
    		WebRequest webRequest) throws IOException {
		
		ErrorDetails error = new ErrorDetails(HttpStatus.BAD_REQUEST, "Validation Error", ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request){
		ErrorDetails errordetails = new ErrorDetails(HttpStatus.NOT_FOUND, "Resource Not Found Exception", exception.getMessage());
	
		return new ResponseEntity<>(errordetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceAlreadyExistException.class)
	public ResponseEntity<ErrorDetails> handleResourceAlreadyExistException(ResourceAlreadyExistException exception, WebRequest request){
		ErrorDetails errordetails = new ErrorDetails(HttpStatus.CONFLICT, "Resource Already Exist" , exception.getMessage());
	
		return new ResponseEntity<>(errordetails, HttpStatus.CONFLICT);
	}
	
}

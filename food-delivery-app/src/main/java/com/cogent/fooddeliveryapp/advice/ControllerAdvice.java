package com.cogent.fooddeliveryapp.advice;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cogent.fooddeliveryapp.exception.NameAlreadyExistsException;
import com.cogent.fooddeliveryapp.exception.NoDataFoundException;
import com.cogent.fooddeliveryapp.exception.apierror.ApiError;


@org.springframework.web.bind.annotation.ControllerAdvice
// will handle all exceptions which are thrown by the controller/restcontroller
// using throws.
public class ControllerAdvice extends ResponseEntityExceptionHandler {
	

	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<?> noDataFoundException(NoDataFoundException e) {
		Map<String, String> map = new HashMap<>();
		
//		System.out.println("hello from no data");
		
		map.put("message", "no data found");
//		System.err.println(e);
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage(), e);
//		System.out.println(apiError);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(NameAlreadyExistsException.class) // this is responsible for handling NameAlreadyExistsException.
	public ResponseEntity<?> nameAlreadyExistsException(NameAlreadyExistsException e){
		/*
		Map<String, String> map = new HashMap<>();
		map.put("message", "name already exists");
		*/
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "name already Exists", e);
//		return ResponseEntity.badRequest().body(map);
		return buildResponseEntity(apiError);
	}
	

	@Override // when the validation fail it will be take that situation
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) { //@Valid annotation.(post method)
//		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
//		System.out.println("hello from handlemethod are not valid");
	
		ApiError apiError = new ApiError(status);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getFieldErrors());
		apiError.addValidationObjectErrors(ex.getBindingResult().getGlobalErrors());
		return buildResponseEntity(apiError);
		
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
		
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
//		System.out.println("hello from type mismatch");
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		apiError.setDebugMessage(e.getRequiredType().getName());
		
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleMethodConstrainViolationException(ConstraintViolationException e) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		return buildResponseEntity(apiError);
	}
	
//	@ExceptionHandler(Exception.class)
//	protected ResponseEntity<?> handleMethodException(Exception e) {
//		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
//		apiError.setMessage(e.getMessage());
//		return buildResponseEntity(apiError);
//	}
	
}

package com.wallet.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.wallet.dto.common.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EntityAlreadyExistException.class)
	@ResponseStatus(code= HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponse> handleEntityAlreadyExistsException(EntityAlreadyExistException ex,WebRequest webRequest)
	{
		log.info("handleEntityAlreadyExistsException fired");
        return new ResponseEntity<>(ApiResponse.builder().code(HttpStatus.NOT_ACCEPTABLE.value()).message(ex.getMessage()).build(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(code= HttpStatus.NOT_FOUND)
	public ResponseEntity<ApiResponse>handleResourecNotFoundExecption(ResourceNotFoundException ex, WebRequest webRequest){
		log.info("handleResourecNotFoundExecption fired");
		return new ResponseEntity<>(ApiResponse.builder().code(HttpStatus.NOT_ACCEPTABLE.value()).message(ex.getMessage()).build(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IcsdException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ApiResponse> handleIcsdException(IcsdException ex){
		return new ResponseEntity<ApiResponse>(ApiResponse.builder().code(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).build(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleException(ConstraintViolationException ex){
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
}

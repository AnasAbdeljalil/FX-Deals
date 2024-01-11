package com.xx.fx.deals;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	////////////////////////////////////////////////////////////////////////////////
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e, HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		String url = request.getRequestURI();
		ErrorResponseDTO errorResponse = buildErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR.toString(), url);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	////////////////////////////////////////////////////////////////////////////////
	@ExceptionHandler(FxRequestException.class)
	public ResponseEntity<Object> handleFxRequestExceptionn(Exception e, HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		String url = request.getRequestURI();
		ErrorResponseDTO errorResponse = buildErrorResponse(e, HttpStatus.BAD_REQUEST.toString(), url);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	////////////////////////////////////////////////////////////////////////////////
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Object> handleSqlExceptionn(Exception e, HttpServletRequest request) {
		logger.error(e.getMessage(), e);
		String url = request.getRequestURI();
		ErrorResponseDTO errorResponse = buildErrorResponse(e, HttpStatus.UNPROCESSABLE_ENTITY.toString(), url);
		return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	////////////////////////////////////////////////////////////////////////////////
	private ErrorResponseDTO buildErrorResponse(Exception e, String errorCode, String path) {
		ErrorResponseDTO errorResponse = new ErrorResponseDTO();
		errorResponse.setCode(errorCode);
		errorResponse.setMessage(e.getMessage());
		errorResponse.setUrl(path);
		errorResponse.setId("FXDeal " + errorCode);
		return errorResponse;
	}

}

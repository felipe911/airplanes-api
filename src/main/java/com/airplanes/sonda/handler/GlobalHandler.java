package com.airplanes.sonda.handler;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.airplanes.sonda.exception.BusinessException;


@ControllerAdvice
@RestController
public class GlobalHandler {
	@ExceptionHandler({ Exception.class, BusinessException.class, ResourceNotFoundException.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

		final String ERRO_INTERNO = "Erro interno de servidor";
		ApiError apiError = null;

		if (ex instanceof BusinessException) {
			apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ((BusinessException) ex).getMessage(),
					ERRO_INTERNO);
		} else if (ex instanceof ResourceNotFoundException) {
			apiError = new ApiError(HttpStatus.NOT_FOUND, ((ResourceNotFoundException) ex).getMessage(),
					"Não encontrado.");
		} else if (ex instanceof UnexpectedRollbackException) {
			apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Commit não efetuado");

		} else {
			apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ERRO_INTERNO);
		}

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}
}

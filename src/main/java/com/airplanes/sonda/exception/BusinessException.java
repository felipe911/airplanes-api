package com.airplanes.sonda.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String errorMessage;

	public BusinessException(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getMessage() {
		return errorMessage;
	}

}